package core.accounts;

import java.util.HashMap;
import java.util.List;

import core.database.DatabaseCommunicator;

public class FacultyMember extends User{  
	//private static final int FACULTY_MEMBER_ROLE = 1; 

	private double minWtu;
	private double maxWtu;
	
	public FacultyMember(String login, int emplId, String firstName, String lastName, String email, String officeLocation, double minWtu, double maxWtu) {
		super(login, emplId, firstName, lastName, email, officeLocation, FACULTY_MEMBER); 
		this.minWtu = minWtu;
		this.maxWtu = maxWtu;
	}
	
	/**
	 * Sets the teacher's minimum WTU target
	 * @param wtu minimum wtu requirement
	 */
	public void setMinWtu(double wtu) {
		this.minWtu= wtu;
	}
	
	/**
	 * Gets the teacher's minimum WTU target
	 * @return the minimum WTU target
	 */
	public double getMinWtu() {
		return minWtu;
	}
	/**
	 * Sets the teacher's max WTU target
	 * @param wtu max wtu requirement
	 */
	public void setMaxWtu(double wtu) {
		this.maxWtu= wtu;
	}
	
	/**
	 * Gets the teacher's max WTU target
	 * @return the max WTU target
	 */
	public double getMaxWtu() {
		return maxWtu;
	}
	
	/**
	 * Method to update user information in database
	 */
	@Override
	public void updateUser()
	{
		DatabaseCommunicator.editDatabase(this, "login='" + super.getLogin() + "', empl_id=" + super.getEmplId() + ", last_name='" + super.getLastName() + "', "
				+ "first_name='" + super.getFirstName() + "', email='" + super.getEmail() + "', office_location='" + super.getOfficeLocation() + "', role=" + getRole()
				+ ", min_wtu=" + minWtu + ", max_wtu=" + maxWtu);
	}
	@Override
	public String getKeys(){
		return super.getKeys() + ", min_wtu, max_wtu"; 
	}
	
	@Override
	public String getValues() {
		return super.getValues() + ", " + minWtu + ", " + maxWtu;
	}
	
}
