package org.kernels.schedulr.accounts;

import java.util.*;

import org.mindrot.jbcrypt.BCrypt;

import Database.DatabaseCommunicator;

public class UserAuthenticator {
	
	List<HashMap<String, Object>> result;
	
	public Integer checkPassword(String username, String pass)
	{
		String hashed = null;
		int role = -1;
		result = DatabaseCommunicator.queryDatabase("SELECT pass_hash FROM users WHERE login='" + username + "';");
		try {	
			hashed = (String) result.get(0).get("pass_hash");
			if (BCrypt.checkpw(pass, hashed)) {
				result = DatabaseCommunicator.queryDatabase("SELECT role FROM users WHERE login='" + username + "';");
				role = (Integer) result.get(0).get("role");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}

}
