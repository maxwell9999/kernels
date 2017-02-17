package core.accounts;

import core.database.DatabaseCommunicator;
import core.database.DatabaseObject;

public abstract class User implements DatabaseObject {
	private static int DEPARTMENT_SCHEDULER = 1; 
	private static int FACULTY_MEMBER = 2; 
	
	private String login; 
	private int emplId; 
	private String firstName, lastName, email, officeLocation; 
	private int role;
	
	public User() {
		
	}
	
	public User(String login, int emplId, String firstName, String lastName, String email, String officeLocation, int role) {
		this.login = login;
		this.emplId = emplId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.officeLocation = officeLocation;
		this.role = role; 
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getEmplId() {
		return emplId;
	}

	public void setEmplId(int emplId) {
		this.emplId = emplId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	} 
	
	public String getKeys(){
		return "login, empl_id, last_name, first_name, email, office_location, role"; 
	}
	
	public String getValues() {
		return "'" + login + "', " + emplId + ", '" + lastName + "', '" + firstName + "', '" + 
				email + "', " + officeLocation + ", " + role;
	}
	
	public String getTable() {
		return "users"; 
	}
	
	public void updateUser()
	{
		DatabaseCommunicator.updateDatabase(this);
	}
	
	public void changePassword(String pass) {
		DatabaseCommunicator.updateDatabase(this, "pass_hash='" + pass + "'");
		DatabaseCommunicator.updateDatabase(this, "reset_password=0");
	}
	
	public void resetPassword() {
		
	}
	
	public String getKeyIdentifier()
	{
		return "login='" + login + "'";
	}
	
	

}
