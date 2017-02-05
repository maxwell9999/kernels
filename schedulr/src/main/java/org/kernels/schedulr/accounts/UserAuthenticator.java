package org.kernels.schedulr.accounts;

import java.sql.DriverManager;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UserAuthenticator {
	
	Statement stmt;
	ResultSet rs;
	
	
	public boolean checkPassword(String username, String pass)
	{
		String hashed = null;
		
		
		
		//get hashed password from database using username
		
		
		
		
		return BCrypt.checkpw(pass, hashed);
	}

}
