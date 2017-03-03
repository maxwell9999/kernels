package core.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
				System.out.println(query);
				e.printStackTrace();
			}
		}
		return result;
	}
	

	public static void insertDatabase(DatabaseObject object, String fieldString, String valueString)
	{
		String insert = "INSERT INTO " + object.getTable() + "(" + fieldString + ") VALUES (" + valueString + ");";
		databaseAction(insert);
	}
	
	public static void deleteDatabase(String tableName, String value)
	{
		String delete = "DELETE FROM " + tableName + " WHERE " + value;
		databaseAction(delete);
	}
	
	/*public static void updateDatabase(DatabaseObject object, String newValue)
	{
		String update = "UPDATE " + object.getTable() + " SET " + newValue  + " WHERE " + object.getKeyIdentifier() + ";";
		databaseAction(update);
	}*/
	
	public static void editDatabase(DatabaseObject object, String newValue)
	{
		String update = "UPDATE " + object.getTable() + " SET " + newValue  + " WHERE " + object.getKeyIdentifier() + ";";
		databaseAction(update);
	}
	
	public static void replaceDatabase(DatabaseObject object)
	{
		String update = ("REPLACE INTO " + object.getTable() + " (" + object.getKeys() + ") "
				+ "VALUES (" + object.getValues() + ");");
		databaseAction(update);
	}
	
	public static boolean resourceExists(DatabaseObject object) {
		List<HashMap<String, Object>> list = queryDatabase("SELECT count(*) FROM " + object.getTable() + " WHERE " + object.getKeyIdentifier() + ";");
		if (list.size() == 0)
			return false;
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
				String replace = "REPLACE INTO " + object.getTable() + " (" + object.getKeys() + ") "
						+ "VALUES (" + object.getValues() + ");";
				stmt.executeUpdate(replace);
			}
			stmt.close();
			connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
