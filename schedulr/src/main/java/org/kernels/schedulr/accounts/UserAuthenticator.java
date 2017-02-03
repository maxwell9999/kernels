package org.kernels.schedulr.accounts;

import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;
import Database.DatabaseCommunicator;

public class UserAuthenticator {
	
	ResultSet rs;
	
	public String checkPassword(String username, String pass)
	{
		String hashed = null;
		rs = DatabaseCommunicator.queryDatabase("SELECT password FROM users WHERE username=" + username + ";");
		try {
			hashed = rs.getString(0);
			if (BCrypt.checkpw(pass, hashed)) {
				rs = DatabaseCommunicator.queryDatabase("SELECT role FROM users WHERE username=" + username + ";");
				return rs.getString(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
