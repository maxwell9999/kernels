package core.accounts;

public class DepartmentScheduler extends User {  
	private static final int DEPT_SCHED_ROLE = 1; 

	public DepartmentScheduler() {
		this.setRole(DEPT_SCHED_ROLE); 
	}
	
	public DepartmentScheduler(String login, int emplId, String firstName, String lastName, String email, String officeLocation) {
		super(login, emplId, firstName, lastName, email, officeLocation, DEPT_SCHED_ROLE); 
	}
	
}