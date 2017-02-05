package Database;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DatabaseCommunicator 
{
	public static ResultSet queryDatabase(String query)
	{
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		
		connection = connect();
		if(connection != null)
		{
			try {
				stmt = (Statement) connection.createStatement();
				rs = stmt.executeQuery(query);
				stmt.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	public static void insertDatabase(String tableName, String fieldString, String valueString)
	{
		String insert = "INSERT INTO " + tableName + "(" + fieldString + ") VALUES (" + valueString + ");";
		databaseAction(insert);
	}
	
	public static void deleteDatabase(String tableName, String value)
	{
		String delete = "DELETE FROM " + tableName + " WHERE " + value;
		databaseAction(delete);
	}
	
	public static void updateDatabase(String tableName, String newValue, String identifier)
	{
		String update = "UPDATE " + tableName + " SET " + newValue  + " WHERE " + identifier + ";";
		databaseAction(update);
	}
	
	
	private static void databaseAction(String action)
	{
		Connection connection = null;
		Statement stmt = null;
		
		connection = connect();
		if(connection != null)
		{
			try {
			stmt = (Statement) connection.createStatement();
			stmt.executeUpdate(action);
			stmt.close();
			connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Connection connect()
	{
		Connection connection = null;
		
		String url = "jdbc:mysql://cpe309.cqxvubssbsqb.us-west-2.rds.amazonaws.com:3306/";
		String userName = "kernels";
		String password = "TimKearns";
		String dbName = "schedulr";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(url + dbName, userName, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
}
