package core.accounts;

public class FacultyMember extends User{  
	//private static final int FACULTY_MEMBER_ROLE = 1; 

	
	public FacultyMember(String login, int emplId, String firstName, String lastName, String email, String officeLocation) {
		super(login, emplId, firstName, lastName, email, officeLocation, FACULTY_MEMBER); 
	}
	
}
