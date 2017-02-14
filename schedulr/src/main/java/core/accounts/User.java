package core.accounts;

public abstract class User {
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
	
	
	
	

}
