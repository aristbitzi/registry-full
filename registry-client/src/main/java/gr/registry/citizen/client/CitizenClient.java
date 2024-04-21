package gr.registry.citizen.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import gr.registry.citizen.*;
import gr.registry.citizen.domain.Citizen;


public class CitizenClient {
	private String ip;
    private int port;
    private String basePath;
    private static int id = 1;
    
    public CitizenClient() {
    	this.ip = PropertyReader.getIp();
    	this.port = Integer.parseInt(PropertyReader.getPort());
    	basePath = "http://" + ip + ":" + port + "/registry-rest/api/citizens"; 
    }
    
    public CitizenClient(String ip, int port) {
    	this.ip = ip;
    	this.port = port;
    	basePath = "http://" + ip + ":" + port + "/registry-rest/api/citizens"; 
    }
    
    private WebTarget getTarget(String methodName) {
    	ClientConfig cc = new ClientConfig();
    	Client c = ClientBuilder.newClient(cc);
    	HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
    	c.register(feature);
    	WebTarget target = c.target(basePath + methodName);

		return target;
    }
    
    public void getCitizens(MediaType type) {
    	System.out.println("Invoking GET /citizens with no query parameters");
    	WebTarget r = getTarget("");
    	Invocation.Builder builder = r.request(type);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getCitizens");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void getCitizensWithParams(String idNumber, String firstName, String lastName, MediaType type) {
    	System.out.println("Invoking GET /citizens with query parameters");
    	WebTarget r = getTarget("");
    	if (idNumber != null) r = r.queryParam("idNumber",idNumber);
    	if (firstName != null) r = r.queryParam("firstName",idNumber);
    	if (lastName != null) r = r.queryParam("lastName",idNumber);
    	
    	Invocation.Builder builder = r.request(type);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getCitizens with parameters");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void getCitizen(String idNumber, MediaType type) {
    	System.out.println("Invoking GET /citizens/" + idNumber);
    	WebTarget r = getTarget("/" + idNumber);
    	Invocation.Builder builder = r.request(type);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getCitizen");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	if (type != MediaType.TEXT_HTML_TYPE) {
 	    		Citizen citizen = response.readEntity(Citizen.class);
 	    		System.out.println("Found citizen: " + citizen);
 	    	}
 	    	else System.out.println(response.readEntity(String.class));
 	    }
    }
    
    private Citizen createCitizen(String idNumber) {
    	Citizen citizen = new Citizen();
    	citizen.setIdNumber(idNumber);
    	citizen.setFirstName("First Name" + id);
    	citizen.setLastName("Last Name" + id);
    	citizen.setGender("Gender" +id);
    	citizen.setDateOfBirth("Date of Birth" +id);
    	citizen.setTaxNumber("Tax Number" +id);
    	citizen.setAddress("Address" +id);
    	id++;
    	
    	return citizen;
    }
    
    public void addCitizen(String idNumber, MediaType type) {
    	System.out.println("Invoking PUT /citizens");
    	WebTarget r = getTarget("");
    	Citizen citizen = createCitizen(idNumber);
    	Invocation.Builder builder = r.request();
 	    Response response = builder.post(Entity.entity(citizen,type),Response.class);
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling addCitizen");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void updateCitizen(String idNumber, MediaType type) {
    	System.out.println("Invoking PUT /citizens/" + idNumber);
    	WebTarget r = getTarget("/" + idNumber);
    	Citizen citizen = createCitizen(idNumber);
    	citizen.setFirstName("Antonis");
    	Invocation.Builder builder = r.request();
 	    Response response = builder.put(Entity.entity(citizen,type),Response.class);
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling updateCitizen");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void deleteCitizen(String idNumber) {
    	System.out.println("Invoking DELETE /citizens/" + idNumber);
    	WebTarget r = getTarget("/" + idNumber);
    	Invocation.Builder builder = r.request();
 	    Response response = builder.delete();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling deleteCitizen");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public static void main(String[]args) {
    	MediaType html = MediaType.TEXT_HTML_TYPE;
    	MediaType jaxb = MediaType.APPLICATION_JSON_TYPE;
    	CitizenClient bc = new CitizenClient();
    	//Adding two citizens
    	bc.addCitizen("ID1",jaxb);
    	bc.addCitizen("ID2",jaxb);
    	bc.addCitizen("ID1",jaxb);
    	
    	//Getting all citizens in different media types
    	bc.getCitizens(html);
    	bc.getCitizens(jaxb);
    	
    	//Getting filtered citizens in different media types
    	bc.getCitizensWithParams(null,"FirstName1",null,html);
    	bc.getCitizensWithParams(null,"LastName2",null,jaxb);
    	
    	//Getting one citizen in different media types
    	bc.getCitizen("ID1",html);
    	bc.getCitizen("ID1",jaxb);
    	
    	//Updating one book & checking the update
    	bc.updateCitizen("ID1",jaxb);
    	bc.getCitizen("ID1",jaxb);
    	
    	//Deleting first book & checking the deletion
    	bc.deleteCitizen("ID1");
    	bc.deleteCitizen("ID1");
    	bc.getCitizens(jaxb);
    }
}

