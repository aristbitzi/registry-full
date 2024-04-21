package gr.registry.citizen.domain;

import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Citizen {
	
	private String idNumber;
	private String firstName;
	private String lastName;
	private String gender;
	private String dateOfBirth;
	private String taxNumber;
	private String address;
	
	public Citizen() {}
	
	public Citizen(String idNumber, String firstName, String lastName, String gender, String dateOfBirth, String taxNumber, String address) {
		this.idNumber = idNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.taxNumber = taxNumber;
		this.address = address;
	}
	
	public String getIdNumber() {
		return idNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public String getTaxNumber() {
		return taxNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String toString() {
		return "ID: " + idNumber + ", Name: " + firstName + " " + lastName + ", Gender: " + gender + ", Date of Birth: " + dateOfBirth + ", Tax Number: " + taxNumber + ", Address: " + address;
    }

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setIdNumber(String idNumber) {
		this.idNumber= idNumber;	
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;	
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;	
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;	
	}
	
	public void setGender(String gender) {
		this.gender = gender;	
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citizen citizen = (Citizen) o;
        return Objects.equals(idNumber, citizen.idNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber);
    }
			
}
