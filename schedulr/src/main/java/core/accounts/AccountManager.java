package core.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import core.database.DatabaseCommunicator;
import core.resources.Course;

public class AccountManager
{
	/**
     * Query method to add new user to database
     * @param username field to be used for user login 
     * @param emplId field representing user employee ID
     * @param first first name of user
     * @param last last name of user
     * @param email school affiliated email for user; typically contains username
     * @param office office location of user (building-room)
     * @param role denotes user privileges (1 = Department Scheduler, 0 = Faculty Member)
     */
	public static void addUser(String username, int emplID, String first, String last, String email, String office, int role)
	{
		String hashed = BCrypt.hashpw(emplID + "", BCrypt.gensalt());
		String fieldString = "login, empl_id, last_name, first_name, email, office_location, pass_hash, role";
		String valueString = "'" + username + "', " + emplID + ", '" + last + "', '" + first +  "', '" + email +  "', '" + office + "', '" + hashed + "', " + role;
		
		DatabaseCommunicator.insertDatabase("users", fieldString, valueString);
	}
	
	/**
     * Query method to remove user from database
     * @param username field to be used for user login 
     */
	public static void removeUser(String username)
	{
		DatabaseCommunicator.deleteDatabase("users", "login='" + username + "'");
	}
	
	/**
     * Query method to edit existing user information
     * @param username field to be used for user login 
     * @param setValues column=value pairs separated by commas
     */
	public static void editUser(String username, String setValues)
	{
		DatabaseCommunicator.updateDatabase("users", setValues, "login='" + username + "'");
	}
	
	public static User getUser(String login) {
		FacultyMember user = new FacultyMember();  
		List<HashMap<String, Object>> userAttributes = DatabaseCommunicator.queryDatabase("SELECT * FROM users WHERE login='" + login + "';");
		HashMap<String, Object> userMap = userAttributes.get(0); 
		user.setLogin(userMap.get("login").toString());
		user.setEmplId(Integer.parseInt(userMap.get("empl_id").toString()));
		user.setFirstName((userMap.get("first_name").toString()));
		user.setLastName((userMap.get("last_name").toString()));
		user.setEmail((userMap.get("email").toString()));
		user.setOfficeLocation((userMap.get("office_location").toString()));
		return user; 
	}
	
	/**
     * Query method to reset existing user password. 
     * @param username field to be used for user login 
     * @param newPass new password
     */
	public static void resetPassword(String username, String newPass)
	{
		String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt());
		editUser(username, "pass_hash='" + hashed + "'");
		DatabaseCommunicator.updateDatabase("users", "reset_password=0", "login='" + username + "'");
	}
	
	/**
     * Query method to promote or demote an existing user
     * @param username field to be used for user login 
     * @param role denotes user privileges 
     */
	public static void changeRole(String username, int role)
	{
		DatabaseCommunicator.updateDatabase("users", "role=" + role, "login='" + username + "'");
	}
	
	/**
	 * Returns a sorted user list, sorted by last name
	 * @return sorted List of users
	 */
	public static List<User> getUserList()
	{
		String login, firstName, lastName, email, officeLocation; 
		int emplId;  
		int role;
		
		List<User> userList = new ArrayList<User>();
		List<HashMap<String, Object>> userMap = DatabaseCommunicator.queryDatabase("SELECT * FROM users;");
		for(HashMap<String, Object> map: userMap)
		{
			login = map.get("login").toString();
			firstName = map.get("first_name").toString();
			lastName = map.get("last_name").toString();
			email = map.get("email").toString();
			officeLocation = map.get("office_location").toString();
			emplId = (Integer) map.get("empl_id");
			role = (Integer) map.get("role");
			if (role == 1)
				userList.add(new DepartmentScheduler(login, emplId, firstName, lastName, email, officeLocation));
			else
				userList.add(new FacultyMember(login, emplId, firstName, lastName, email, officeLocation));
		}
		Collections.sort(userList, new UserComparator());
		return userList;
	}
	/**
	 * User Comparator is the comparator used to compare the user's last names to put in alphabetical order.
	 * 
	 * @author Simko
	 *
	 */
    private static class UserComparator implements Comparator<User>
    {
    	/**
    	 * Compares the last names, returning a negative number if the first name comes before the second.
    	 */
    	public int compare(User user1, User user2) 
    	{
    		if (user1.getLastName().equals(user2.getLastName()))
    		{
    			return user1.getFirstName().compareTo(user2.getFirstName());
    		}
    		return user1.getLastName().compareTo(user2.getLastName());
    	}
	}
}