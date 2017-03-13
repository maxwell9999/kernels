package core.database;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import core.resources.Schedule;

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
	
	/**
     * Method to add a new table to the database to hold sections for the specified schedule
     * Table names are in the format status_year_term in all caps
     * @param status must either be DRAFT or PREREG or POSTREG
     * @param year the year the schedule is for
     * @param term the term the schedule is for; must be F, W, SP, or SU
     */
	public static void createNewSchedule(String status, int year, String term) {
		
		createNewScheduleTable(status, year, term);

    	// add schedule to table of schedules
    	Schedule newSchedule = new Schedule(term, year);
    	newSchedule.addToDatabase();
    }
	
	
	public static boolean saveSchedule(String status, int year, String term) throws SQLException {
		String tableName = status.toUpperCase() + "_" + year + "_" + term.toUpperCase(); 
		if (scheduleExists(status, year, term)) {
			// wipe the draft table
			String action = "DELETE FROM " + tableName + ";"; 
			databaseAction(action); 

			// clone the TEMP_SCHEDULE table
			action = "INSERT " + tableName + " SELECT * FROM TEMP_SCHEDULE;";
			databaseAction(action); 
			return true; 
		}
		else {
			System.err.println("SCHEDULE DOES NOT EXIST.");
			return false; 
		}

	}
	
	public static void createNewScheduleTable(String status, int year, String term) {
    	String tableName = status.toUpperCase() + "_" + year + "_" + term.toUpperCase(); 
    	String statement = "CREATE TABLE " + tableName + " LIKE TEMP_SCHEDULE;"; 
    	databaseAction(statement);		
	}
	
	public static void deleteScheduleTable(String status, int year, String term) {
    	String tableName = status.toUpperCase() + "_" + year + "_" + term.toUpperCase(); 
    	String statement = "DROP TABLE " + tableName + ";"; 
    	databaseAction(statement);		
	}
	
	public static boolean scheduleExists(String status, int year, String term) throws SQLException {
		String tableName = status.toUpperCase() + "_" + year + "_" + term.toUpperCase(); 
		Connection connection = connect(); 
		DatabaseMetaData dmd = connection.getMetaData(); 
		ResultSet rs = dmd.getTables(null, null, tableName, null); 
		return rs.next(); 
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
