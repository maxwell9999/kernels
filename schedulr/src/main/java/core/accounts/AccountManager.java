package core.accounts;

import core.database.*;

import org.mindrot.jbcrypt.*;

import java.util.*;

public class AccountManager
{
	public void addUser(String username, int emplID, String first, String last, String email, String office, int role)
	{
		String hashed = BCrypt.hashpw(emplID + "", BCrypt.gensalt());
		String fieldString = "login, empl_id, last_name, first_name, email, office_location, pass_hash, role";
		String valueString = "'" + username + "', " + emplID + ", '" + last + "', '" + first +  "', '" + email +  "', '" + office + "', '" + hashed + "', " + role;
		
		DatabaseCommunicator.insertDatabase("users", fieldString, valueString);
	}
	
	public void removeUser(String username)
	{
		DatabaseCommunicator.deleteDatabase("users", "login='" + username + "'");
	}
	
	public void editUser(String username, String column, String newValue)
	{
		DatabaseCommunicator.updateDatabase("users", column + "='" + newValue + "'", "login='" + username + "'");
	}
	
	public void resetPassword(String username, String newPass)
	{
		String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt());
		editUser(username, "pass_hash", hashed);
		DatabaseCommunicator.updateDatabase("users", "reset_password=0", "login='" + username + "'");
	}
	
	public void changeRole(String username, int role)
	{
		DatabaseCommunicator.updateDatabase("users", "role=" + role, "login='" + username + "'");
	}
	public List<HashMap<String, Object>> getUserList()
	{
		List<HashMap<String, Object>> userMap = DatabaseCommunicator.queryDatabase("SELECT login,last_name,first_name FROM users;");
		Collections.sort(userMap, new UserComparator());
		return userMap;
	}
	
	class UserComparator implements Comparator<Map<String, Object>>
	{
		public int compare(Map<String, Object> user1, Map<String, Object> user2) 
		{
			return user1.get("last_name").toString().compareTo(user2.get("last_name").toString());
		}
	}
}