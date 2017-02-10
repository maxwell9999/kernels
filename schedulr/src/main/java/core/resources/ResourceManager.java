package core.resources;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.database.DatabaseCommunicator;

/**
 * building
 * number
 * capacity
 * notes
 * equipment
 * primary = (building, number)
 * @author Simko
 *
 */

public class ResourceManager
{
	/**
     * Query method to add new room to database
     */
	public void addRoom(int building, int number, int capacity, String notes, String equipment)
	{
		String fieldString = "building, number, capacity, notes, equipment";
		String valueString = building + ", " + number + ", " + capacity + ", '" + notes + "', '" + equipment + "'" ;
		
		DatabaseCommunicator.insertDatabase("rooms", fieldString, valueString);
	}
	
	/**
     * Query method to remove room from database
     */
	public void removeRoom(String resourceName, int building, int room)
	{
		DatabaseCommunicator.deleteDatabase("rooms", "building=" + building + " AND room=" + room + ";");
	}
	
	/**
     * Query method to edit existing Resource information
     */
	public void editResource(String table, String resourcename, String column, String newValue)
	{
		DatabaseCommunicator.updateDatabase(table, column + "='" + newValue + "'", "login='" + resourcename + "'");
	}

	/**
	 * Returns a sorted Resource list, sorted by last name
	 * @return sorted List of Resources
	 */
	public List<HashMap<String, Object>> getClassList()
	{
		List<HashMap<String, Object>> classMap = DatabaseCommunicator.queryDatabase("SELECT login,last_name,first_name FROM Resources;");
		Collections.sort(classMap, new RoomComparator());
		return classMap;
	}
	/**
	 * Resource Comparator is the comparator used to compare the Resource's last names to put in alphabetical order.
	 * 
	 * @author Simko
	 *
	 */
    class RoomComparator implements Comparator<Map<String, Object>>
    {
    	/**
    	 * Compares the last names, returning a negative number if the first name comes before the second.
    	 */
    	public int compare(Map<String, Object> Resource1, Map<String, Object> Resource2) 
    	{
    		return Resource1.get("last_name").toString().compareTo(Resource2.get("last_name").toString());
    	}
	}
}