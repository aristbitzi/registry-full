package gr.registry.citizen.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import gr.registry.citizen.configuration.PropertyReader;
import gr.registry.citizen.domain.Citizen;

public class DBHandler {
	private static Connection getConnection() throws InternalServerErrorException {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con = DriverManager.getConnection(  
			"jdbc:mysql://" + PropertyReader.getDbHost() + ":" + PropertyReader.getDbPort() + "/citizens",
				PropertyReader.getLogin(),PropertyReader.getPwd());
			return con;
		}
		catch(Exception e) {
			throw new InternalServerErrorException("Cannot connect to underlying database");
		}
	}
	
		
	private static Citizen getCitizenFromRS(ResultSet rs) throws SQLException{
		Citizen citizen = new Citizen();
		citizen.setIdNumber(rs.getString("idnumber"));
		citizen.setFirstName(rs.getString("firstname"));
		citizen.setLastName(rs.getString("lastname"));
		citizen.setDateOfBirth(rs.getString("dateofbirth"));
		citizen.setGender(rs.getString("gender"));
		citizen.setTaxNumber(rs.getString("taxnumber"));
		citizen.setAddress(rs.getString("address"));
		
		return citizen;
	}
	
	
	public static List<Citizen> getAllCitizens() throws InternalServerErrorException{
		List<Citizen> citizens = null;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from citizen");
			System.out.println("Successfuly queried DB!!!");
			if (rs.next()) {
				citizens = new ArrayList<Citizen>();
				citizens.add(getCitizenFromRS(rs));
				while (rs.next()) {
					citizens.add(getCitizenFromRS(rs));
				}
			}
			
			con.close();
		}
		catch(Exception e) {
			throw new InternalServerErrorException("An internal error prevented from getting the information of the registry citizen's");
		}
		return citizens;
	}
	
	public static Citizen getCitizen(String idNumber) throws InternalServerErrorException{
		Citizen citizen = null;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "select * from citizen where idnumber='" + idNumber + "'";
			System.out.println("query is: " + query);
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("query was successful!!!");
			if (rs.next()) {
				citizen = getCitizenFromRS(rs);
			}
			System.out.println("processed query results!!!");
			
			con.close();
		}
		catch(Exception e) {
			throw new InternalServerErrorException("An internal error prevented from getting the information of the registry citizen's");
		}
		return citizen;
	}
	
	public static List<Citizen> getCitizens(String idNumber, String firstName, String lastName) throws InternalServerErrorException{
		List<Citizen> citizens = null;
		boolean hasFirstName = false, hasLastName = false, hasIdNumber = false, hasDateOfBirth = false, hasGender = false, hasAddress = false, hasTaxNumber = false;
		/*hasFirstName = (firstName != null && !firstName.trim().equals(""));
		hasLastName = (lastName != null && !lastName.trim().equals(""));
		hasIdNumber = (idNumber != null && !idNumber.isEmpty());
		hasDateOfBirth = (dateOfBirth != null && !dateOfBirth.trim().equals(""));
		hasGender = (gender != null && !gender.trim().equals(""));
		hasAddress = (address != null && !address.trim().equals(""));
		hasTaxNumber = (taxNumber != null && !taxNumber.trim().equals(""));
		Connection con = getConnection();*/
		
		return citizens;
	}
			
	
	public static boolean existsCitizen(String idNumber) throws InternalServerErrorException {
		boolean hasCitizen = false;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "select * from citizen where idnumber='" + idNumber + "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				hasCitizen = true;
			}
			
			con.close();
		}
		catch(Exception e) {
			throw new InternalServerErrorException("An internal error prevented from getting the information of the registry's citizens");
		}
		return hasCitizen;
	}
	
	public static boolean updateCitizen(Citizen citizen) throws InternalServerErrorException{
		Connection con = getConnection();
		boolean updated = false;
		try {
			Statement stmt = con.createStatement();
			String query = "update citizen info " +
			"first name='" + citizen.getFirstName() + "', " +
			"last name='" + citizen.getLastName() + "', "+
			"gender='" + citizen.getGender() + "', " +
			"date of birth=" + getFieldValue(citizen.getDateOfBirth()) + ", " +
			"address=" + getFieldValue(citizen.getAddress()) + ", " + 
			"tax number=" + getFieldValue(citizen.getTaxNumber()) + ", " + 
			"id number='" + citizen.getIdNumber() + "'";
			stmt.execute(query);
			
			if (stmt.getUpdateCount() == 1) {
				updated = true;
			}
			
			con.close();
		}
		catch(Exception e) {
			throw new InternalServerErrorException("An internal error prevented from getting the information of the registry's citizens");
		}
		
		return updated;
	}
	
	private static String getFieldValue(String value) {
		if (value == null || value.trim().equals("")) return new String("NULL");
		else return "'" + value + "'";
	}
	
	public static boolean createCitizen(Citizen citizen) throws InternalServerErrorException{
		Connection con = getConnection();
		boolean created = false;
		try {
			Statement stmt = con.createStatement();
			String query = "insert into citizen(idnumber,firstname,lastname,address,dateofbirth,gender,taxnumber) "
					+ "values('" + citizen.getIdNumber() + "','" + citizen.getFirstName() + "','" + 
					citizen.getLastName() + "','" +
					citizen.getAddress() + "'," + getFieldValue(citizen.getDateOfBirth()) + "," +
					getFieldValue(citizen.getGender()) + "," + getFieldValue(citizen.getAddress()) + "," +
					getFieldValue(citizen.getTaxNumber()) + ")";
			stmt.execute(query);
			if (stmt.getUpdateCount() == 1) {
				created = true;
			}
			con.close();
		}
		catch(Exception e) {
			throw new InternalServerErrorException("An internal error prevented from getting the information of the registry's citizens");
		}
		
		return created;
	}
	
	public static boolean deleteCitizen(String idNumber) throws InternalServerErrorException{
		boolean deleted = false;
		Connection con = getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "delete from citizen where idnumber='" + idNumber + "'";
			stmt.execute(query);
			if (stmt.getUpdateCount() == 1) {
				deleted = true;
			}
			con.close();
		}
		catch(Exception e) {
			throw new InternalServerErrorException("An internal error prevented from getting the information of the registry's citizens");
		}
		
		return deleted;
	}
}
