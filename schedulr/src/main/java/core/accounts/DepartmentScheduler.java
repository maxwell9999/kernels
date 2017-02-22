package core.accounts;

public class DepartmentScheduler extends User {  

	public DepartmentScheduler(String login, int emplId, String firstName, String lastName, String email, String officeLocation) {
		super(login, emplId, firstName, lastName, email, officeLocation, DEPARTMENT_SCHEDULER); 
	}
	
}