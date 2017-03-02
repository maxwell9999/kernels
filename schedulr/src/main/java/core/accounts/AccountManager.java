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
	public static void addUser(String username, int emplID, String first, String last, String email, String office, int role, double wtu)
	{
		String hashed = BCrypt.hashpw(emplID + "", BCrypt.gensalt());
		User newUser;
		if (role == User.SCHEDULER)
			newUser = new DepartmentScheduler(username, emplID, first, last, email, office);
		else
			newUser = new FacultyMember(username, emplID, first, last, email, office, wtu);
		DatabaseCommunicator.insertDatabase(newUser, newUser.getKeys() + ", pass_hash", newUser.getValues() + ", '" + hashed + "'");
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
	 * Method to get a User object specified by the login
	 * @param login username of user 
	 * @return User with information filled in from database 
	 */
	public static User getUser(String login) {
		User user;  
		String firstName, lastName, email, officeLocation; 
		int emplId;  
		int role;
		double wtu = 0;
		
		List<HashMap<String, Object>> userAttributes = DatabaseCommunicator.queryDatabase("SELECT * FROM users WHERE login='" + login + "';");
		HashMap<String, Object> map = userAttributes.get(0); 
		firstName = map.get("first_name").toString();
		lastName = map.get("last_name").toString();
		email = map.get("email").toString();
		officeLocation = map.get("office_location").toString();
		emplId = (Integer) map.get("empl_id");
		role = (Integer) map.get("role");

		if (role == User.SCHEDULER)
			user = new DepartmentScheduler(login, emplId, firstName, lastName, email, officeLocation);
		else
			wtu = (double) map.get("target_wtu");
			user = new FacultyMember(login, emplId, firstName, lastName, email, officeLocation, wtu);

		return user; 
	}
	
	
	/**
     * Query method to promote or demote an existing user
     * @param username field to be used for user login 
     * @param role denotes user privileges 
     */
	public static void changeRole(User user, int role)
	{
		user.setRole(role);
		user.updateUser();
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
		double wtu = 0;
		
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

			if (role == User.SCHEDULER)
				userList.add(new DepartmentScheduler(login, emplId, firstName, lastName, email, officeLocation));
			else
			{
				wtu = (double) map.get("target_wtu");
				userList.add(new FacultyMember(login, emplId, firstName, lastName, email, officeLocation, wtu));
			}

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