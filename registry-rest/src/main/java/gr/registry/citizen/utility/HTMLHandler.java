package gr.registry.citizen.utility;

import java.util.List;

import gr.registry.citizen.domain.Citizen;

public class HTMLHandler {
	
			
	private static String getStringFieldValue(String fieldValue) {
		if (fieldValue == null || fieldValue.trim().equals("")) return "";
		else return fieldValue;
	}
	
	private static String createCitizenInfo(Citizen citizen) {
		String str = "<tr>";
		str += "<td>" + citizen.getIdNumber() + "</td>";
		str += "<td>" + citizen.getFirstName() + "</td>";
		str += "<td>" + citizen.getLastName() + "</td>";
		str += "<td>" + citizen.getAddress() + "</td>";
		str += "<td>" + getStringFieldValue(citizen.getGender()) + "</td>";
		str += "<td>" + getStringFieldValue(citizen.getDateOfBirth()) + "</td>";
		str += "<td>" + getStringFieldValue(citizen.getTaxNumber()) + "</td>";
		str += "</tr>\n";
		
		return str;
	}
	
	public static String createHtmlCitizens(List<Citizen> citizens) {
		String answer = "<html>\n";
		
		answer += "<head>\n";
		answer += "<title>The Citizens from the registry</title>\n";
		answer += "</head>\n";
		
		answer += "<body>\n";
		answer += "<h1>CITIZENS</h1>\n";
		answer += "<table border=\"1\" width=\"60%\" align=\"center\">\n";
		answer += "<caption>The requested citizens</caption>\n";
		answer += "<tr><th>IdNumber</th><th>FirstName</th><th>LastName</th><th>Address</th>";
		answer += "<th>Gender</th><th>DateOfBirth</th><th>TaxNumber</th>";
		answer += "<th>Pub. date</th>";
		answer += "</tr>\n";
		for (Citizen citizen: citizens) answer += createCitizenInfo(citizen);
		answer += "</table>\n";
		answer += "</body>\n";
		
		answer += "\n</html>";
		
		return answer;
	}
	
	public static String createHtmlCitizen(Citizen citizen) {
		String answer = "<html>\n";
		
		answer += "<head>\n";
		answer += "<title>A Citizen from the registry</title>\n";
		answer += "</head>\n";
		
		answer += "<body>\n";
		answer += "<h1>Citizen " + citizen.getIdNumber() + "</h1>\n";
		answer += "<table border=\"1\" width=\"60%\" align=\"center\">\n";
		answer += "<caption>The requested citizen</caption>\n";
		answer += "<tr><th>IdNumber</th><th>FirstName</th><th>LastName</th><th>Address</th>";
		answer += "<th>Gender</th><th>DateOfBirth</th><th>TaxNumber</th>";
		answer += "<th>Pub. date</th>";
		answer += "</tr>\n";
		answer += createCitizenInfo(citizen);
		answer += "</table>\n";
		answer += "</body>\n";
		
		answer += "\n</html>";
		
		return answer;
	}
}