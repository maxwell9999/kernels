package core.accounts;

import java.util.*;
import org.mindrot.jbcrypt.BCrypt;
import core.database.*;

public class UserAuthenticator {
	
	List<HashMap<String, Object>> result;
	
	/**
	 * Method to check the entered password matches the user's stored password
	 * Typically used at login 
	 * @param username 
	 * @param pass 
	 * @return User in row format
	 */
	//TODO we might want to change this method to return a User instead of List of hashmaps
	public List<HashMap<String, Object>> checkPassword(String username, String password)
	{
		result = null;
		String hashed = null;
		result = DatabaseCommunicator.queryDatabase("SELECT pass_hash FROM users WHERE login='" + username + "';");
		if(result.isEmpty())
		{
			return null;
		}
		hashed = (String) result.get(0).get("pass_hash");
		if (BCrypt.checkpw(password, hashed))
			result = DatabaseCommunicator.queryDatabase("SELECT login,role,reset_password FROM users WHERE login='" + username + "';");
		return result;
	}
	

}
