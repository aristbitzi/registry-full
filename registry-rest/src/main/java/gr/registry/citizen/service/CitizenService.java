package gr.registry.citizen.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import gr.registry.citizen.domain.Citizen;
import gr.registry.citizen.utility.DBHandler;
import gr.registry.citizen.utility.HTMLHandler;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/citizens")

public class CitizenService {
	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("CitizenService");
	
	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getCitizensInHtml(@QueryParam("idNumber") @DefaultValue("") String idNumber, 
			@QueryParam("firstName") @DefaultValue("") String firstName, @QueryParam("lastName") @DefaultValue("") String lastName, @QueryParam("Date Of Birth") @DefaultValue("") String dateOfBirth, @QueryParam("Gender") @DefaultValue("") String gender, @QueryParam("Tax Number") @DefaultValue("") String taxNumber, @QueryParam("Address") @DefaultValue("") String address)throws InternalServerErrorException{
		logger.info("Id Number =" + idNumber + " First Name =" + firstName + " Last Name = " + lastName);
		List<Citizen> citizens = null;
		if ((idNumber != null && !idNumber.trim().equals("")) || (firstName != null && !firstName.isEmpty()) || (lastName != null && !lastName.trim().equals(""))){
			citizens = DBHandler.getCitizens(idNumber,firstName,lastName);
			
		}
		else citizens = DBHandler.getAllCitizens();
		
		if (citizens == null) citizens = new ArrayList<Citizen>();
		String answer = HTMLHandler.createHtmlCitizens(citizens);
		
		return Response.ok(answer, MediaType.TEXT_HTML).build();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public List<Citizen> getCitizens(@QueryParam("idNumber") @DefaultValue("") String idNumber, 
			@QueryParam("firstName") @DefaultValue("") String firstName, @QueryParam("lastName") @DefaultValue("") String lastName, @QueryParam("Date Of Birth") @DefaultValue("") String dateOfBirth, @QueryParam("Gender") @DefaultValue("") String gender, @QueryParam("Tax Number") @DefaultValue("") String taxNumber, @QueryParam("Address") @DefaultValue("") String address)
					throws InternalServerErrorException{
		logger.info("Id Number =" + idNumber + " First Name =" + firstName + " Last Name = " + lastName);
		List<Citizen> citizens = null;
		if ((idNumber != null && !idNumber.trim().equals("")) || (firstName != null && !firstName.isEmpty()) || (lastName != null && !lastName.trim().equals(""))){
			citizens = DBHandler.getCitizens(idNumber,firstName,lastName);
			
		}
		else citizens = DBHandler.getAllCitizens();
		
		if (citizens == null) {
			citizens = new ArrayList<Citizen>();
		}
		return citizens;
	}
	
	@GET
	@Path ("/{idnumber}")
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public Response getCitizenInHtml(@PathParam("Id Number") String idNumber) throws NotFoundException, InternalServerErrorException {
		logger.info("With ID: " + idNumber);
		
		Citizen citizen = DBHandler.getCitizen(idNumber);
		if (citizen != null) {
			String answer = HTMLHandler.createHtmlCitizen(citizen);
			return Response.ok(answer + "", MediaType.TEXT_HTML).build();
		}
		else throw new NotFoundException();
	}
	
	@GET
	@Path ("/{idnumber}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Citizen getCitizen(@PathParam("ID Number") String idNumber) throws NotFoundException, InternalServerErrorException {
		logger.info("With ID: " + idNumber);
		
		Citizen citizen = DBHandler.getCitizen(idNumber);
		if (citizen != null) {
			return citizen;
		}
		else throw new NotFoundException();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response addCitizen(@Context UriInfo uriInfo, Citizen citizen) throws BadRequestException, InternalServerErrorException, URISyntaxException {
		logger.info("With citizen: " + citizen);
		
		if (citizen == null) {
			throw new BadRequestException("Did not provide any citizen info");
		}
			
		String citizenidNumber = citizen.getIdNumber();
		if (citizenidNumber == null || citizenidNumber.trim().equals("")) {
			throw new BadRequestException("Did not provide an id number for the citizen");
		}
			
		//Checking all obligatory fields in citizen apart from taxnumber and address
		String firstName = citizen.getFirstName();
		if (firstName == null || firstName.isEmpty()) 
			throw new BadRequestException("MUST provide the citizen's first name");
		String lastName = citizen.getLastName();
		if (lastName == null || lastName.trim().equals(""))
			throw new BadRequestException("MUST provide the citizen's last name");
		String idNumber = citizen.getIdNumber();
		if (idNumber == null || idNumber.trim().equals(""))
			throw new BadRequestException("MUST provide the citizen's id number");
		String gender = citizen.getGender();
		if (gender == null || gender.trim().equals(""))
			throw new BadRequestException("MUST provide the citizen's gender");
		String dateOfBirth = citizen.getDateOfBirth();
		if (dateOfBirth == null || dateOfBirth.trim().equals(""))
			throw new BadRequestException("MUST provide the citizen's date of birth");
			
		boolean exists = DBHandler.existsCitizen(citizenidNumber);
		if (exists) throw new BadRequestException("Citizen with given id number already exists");
		else DBHandler.createCitizen(citizen);
			
		return Response.created(new URI(uriInfo.getPath() + "/" + citizen.getIdNumber())).build();
	}
	
	@PUT
	@Path ("{idnumber}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response putCitizen(@PathParam("idnumber") String idNumber, Citizen citizen) throws NotFoundException, BadRequestException, InternalServerErrorException {
		logger.info("With idnumber: " + idNumber);
		
		if (citizen == null) {
			throw new BadRequestException("Did not provide any citizen info");
		}
			
		String citizenIdNumber = citizen.getIdNumber();
		if (citizenIdNumber == null || citizenIdNumber.trim().equals("")) {
			throw new BadRequestException("Did not provide an id number");
		}
		else if (!citizenIdNumber.equals(idNumber)) {
			throw new BadRequestException("The citizen's id number does not match the id number path parameter");
		}
			
		//Checking all obligatory fields in citizen 
		String taxNumber = citizen.getTaxNumber();
		if (taxNumber == null || taxNumber.isEmpty()) 
			throw new BadRequestException("MUST provide the citizen's tax number");
		String address = citizen.getAddress();
		if (address == null || address.trim().equals(""))
			throw new BadRequestException("MUST provide the citizen's address");
					
		boolean updated = DBHandler.updateCitizen(citizen);
		if (!updated) throw new NotFoundException();
			
		return Response.noContent().build();
	}
	
	@DELETE
	@Path ("{idnumber}")
	public Response deleteCitizen(@PathParam("idnumber") String idNumber) throws NotFoundException, InternalServerErrorException {
		logger.info("With id number: " + idNumber);
		
		boolean deleted = DBHandler.deleteCitizen(idNumber);
		if (deleted) {
			return Response.noContent().build();
		}
		else throw new NotFoundException();
	}
}