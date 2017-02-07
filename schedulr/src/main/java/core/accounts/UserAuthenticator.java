package core.accounts;

import java.util.*;
import org.mindrot.jbcrypt.BCrypt;
import core.database.*;

public class UserAuthenticator {
	
	List<HashMap<String, Object>> result;
	
	public List<HashMap<String, Object>> checkPassword(String username, String pass)
	{
		result = null;
		String hashed = null;
		result = DatabaseCommunicator.queryDatabase("SELECT pass_hash FROM users WHERE login='" + username + "';");
		hashed = (String) result.get(0).get("pass_hash");
		if (BCrypt.checkpw(pass, hashed))
			result = DatabaseCommunicator.queryDatabase("SELECT login,role,reset_password FROM users WHERE login='" + username + "';");
		return result;
	}
	

}
