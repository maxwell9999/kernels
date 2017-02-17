package core.database;

import java.sql.DriverManager;
import java.util.*;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

public class DatabaseCommunicator 
{
	public static List<HashMap<String, Object>> queryDatabase(String query)
	{
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		
		List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
		
		connection = connect();
		if(connection != null)
		{
			try {
				stmt = (Statement) connection.createStatement();
				rs = stmt.executeQuery(query);
				
				ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
				int col = md.getColumnCount();

				while (rs.next()) {
				    HashMap<String, Object> row = new HashMap<String, Object>(col);
				    for(int i = 1; i <= col; ++i) {
				        row.put(md.getColumnName(i), rs.getObject(i));
				    }
				    result.add(row);
				}
				
				rs.close();
				stmt.close();
				connection.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
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
	
	public static boolean resourceExists(String tableName, String uniqueIdentifier) {
		List<HashMap<String, Object>> list = queryDatabase("SELECT count(*) FROM " + tableName + " WHERE " + uniqueIdentifier + ";");
		return Integer.parseInt(list.get(0).get("count(*)").toString()) == 1; 
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
	/**
	 * Adds a list of DatabaseObjects to the database
	 * @param objectList List of DatabaseObjects to be added to the database
	 */
	public static void addAllToDatabase(List<DatabaseObject> objectList)
	{
		Connection connection = null;
		Statement stmt = null;
		
		connection = connect();
		if(connection != null)
		{
			try {
			stmt = (Statement) connection.createStatement();
			
			for (DatabaseObject object: objectList)
			{
				stmt.executeUpdate("REPLACE INTO " + object.getTable() + " (" + object.getKeys() + ") "
						+ "VALUES (" + object.getValues() + ");");
			}
			stmt.close();
			connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void insertDatabase(DatabaseObject object)
	{
		String insert = "INSERT INTO " + object.getTable() + "(" + object.getKeys() + ") VALUES (" + object.getValues() + ");";
		databaseAction(insert);
	}
}
