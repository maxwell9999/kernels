package core.accounts;

import java.util.HashMap;
import java.util.List;

import core.database.DatabaseCommunicator;

public class FacultyMember extends User{  
	//private static final int FACULTY_MEMBER_ROLE = 1; 

	private double targetWtu;
	
	public FacultyMember(String login, int emplId, String firstName, String lastName, String email, String officeLocation, double wtu) {
		super(login, emplId, firstName, lastName, email, officeLocation, FACULTY_MEMBER); 
		targetWtu = wtu;
	}
	
	public void setWtu(double wtu) {
		this.targetWtu= wtu;
	}
	
	public double getTargetWtu() {
		List<HashMap<String, Object>> wtu = DatabaseCommunicator.queryDatabase("Select taget_wtu from users where login='" + super.getLogin() + "';");
		if (wtu != null)
		{
			return (double) wtu.get(0).get("target_wtu");
		}
		return 0;
	}
	
	/**
	 * Method to update user information in database
	 */
	@Override
	public void updateUser()
	{
		DatabaseCommunicator.editDatabase(this, "login='" + super.getLogin() + "', empl_id=" + super.getEmplId() + ", last_name='" + super.getLastName() + "', "
				+ "first_name='" + super.getFirstName() + "', email='" + super.getEmail() + "', office_location='" + super.getOfficeLocation() + "', role=" + getRole()
				+ "target_wtu=" + targetWtu);
	}
}
