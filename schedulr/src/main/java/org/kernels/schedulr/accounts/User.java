package org.kernels.schedulr.accounts;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public abstract class User {
	
	Statement stmt;
	ResultSet rs;
	
	public void queryDatabaseConnection(String query)
	{
		Connection connection = null;
		Statement stmt = null;
		
		String url = "jdbc:mysql://cpe309.cqxvubssbsqb.us-west-2.rds.amazonaws.com:3306/";
		String userName = "kernels";
		String password = "TimKearns";
		String dbName = "cpe309";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			connection = (Connection) DriverManager.getConnection(url + dbName, userName, password);
			stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(query);
			stmt.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void changePassword(String newPass)
	{
		String hashedPw = BCrypt.hashpw(newPass, BCrypt.gensalt());
		
		//Push hashedPW to database
	}
	
	public void addUser(int EmplID, String name, String phoneNumber, String office)
	{
		
	}
	
	public void editUser(String change)
	{
		
	}
	
	public void removeUser(String EmplID)
	{
		
	}
	public void changeRole(String EmplID, String role)
	{
		
	}
	public void changeUserName(String name)
	{
		
	}

	
}
