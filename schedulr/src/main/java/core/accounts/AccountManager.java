package core.accounts;

import core.database.*;
import org.mindrot.jbcrypt.*;

public class AccountManager
{
	/*
	 *  am.addUser("test", 12345, "Craig", "Yeti", "cyeti@hotmail.com", "", 1);
		am.editUser("test", "office_location", "10-412");
		am.removeUser("test");
	 */
	
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
}