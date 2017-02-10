package core.accounts;

import core.database.*;

import java.io.IOException;

import org.mindrot.jbcrypt.*;

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
     * @param role denotes user privileges 
     */
	public void addUser(String username, int emplID, String first, String last, String email, String office, int role)
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
	public void removeUser(String username)
	{
		DatabaseCommunicator.deleteDatabase("users", "login='" + username + "'");
	}
	
	/**
     * Query method to edit existing user information
     * @param username field to be used for user login 
     * @param column database attribute being edited 
     * @param newValue value to replace current value in database
     */
	public void editUser(String username, String column, String newValue)
	{
		DatabaseCommunicator.updateDatabase("users", column + "='" + newValue + "'", "login='" + username + "'");
	}
	
	/**
     * Query method to reset existing user password. 
     * @param username field to be used for user login 
     * @param newPass new password
     */
	public void resetPassword(String username, String newPass)
	{
		String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt());
		editUser(username, "pass_hash", hashed);
		DatabaseCommunicator.updateDatabase("users", "reset_password=0", "login='" + username + "'");
	}
	
	/**
     * Query method to promote or demote an existing user
     * @param username field to be used for user login 
     * @param role denotes user privileges 
     */
	public void changeRole(String username, int role)
	{
		DatabaseCommunicator.updateDatabase("users", "role=" + role, "login='" + username + "'");
	}
}