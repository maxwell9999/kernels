package core.accounts;

import org.mindrot.jbcrypt.BCrypt;
import core.database.*;


public abstract class User {

	public void changePassword(String newPass)
	{
		String hashedPw = BCrypt.hashpw(newPass, BCrypt.gensalt());
		
		DatabaseCommunicator.updateDatabase("users", hashedPw, "pass_hash");
		
		//Push hashedPW to database
	}

}
