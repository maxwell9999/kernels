package core.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
	public static void addRoom(int building, int number, int capacity, String type, String notes, String equipment)
	{
		String fieldString = "building, number, capacity, type, notes, equipment";
		String valueString = building + ", " + number + ", " + capacity + ", '" + type + "', '" + 
				notes + "', '" + equipment + "'" ;
		
		DatabaseCommunicator.insertDatabase("rooms", fieldString, valueString);
	}
	
	/**
     * Query method to remove room from database
     */
	public static void removeRoom(int building, int number)
	{
		DatabaseCommunicator.deleteDatabase("rooms", "building=" + building + " AND number=" + number + ";");
	}
	
	/**
     * Query method to add new course to database
     */
	public static void addCourse(String department, int number, String name, int wtu, int lect_hours, String notes, int lab_hours)
	{
		String fieldString = "department, number, name, wtu, lect_hours, notes, lab_hours";
		String valueString = "'" + department + "', " + number + ", '" + name + "', " + wtu + ", " + 
				lect_hours + ", " + notes + ", " + lab_hours;
		
		DatabaseCommunicator.insertDatabase("courses", fieldString, valueString);
	}
	
	/**
     * Query method to remove course from database
     */
	public static void removeCourse(String department, int number)
	{
		DatabaseCommunicator.deleteDatabase("courses", "department='" + department + "' AND number=" + number + ";");
	}
	
	public static void importCourses(File courseFile) throws FileNotFoundException
	{
		/*Map<String, Object> course = new HashMap<String, Object>();
		Scanner fileScan = new Scanner(courseFile);
		Scanner lineScan;
		String temp;
		
		while (fileScan.hasNextLine())
		{
			lineScan = new Scanner(fileScan.nextLine());
			course.put("department", lineScan.next());
			lineScan.useDelimiter(",");
			course.put("number", lineScan.next());
			course.put("name", lineScan.next());
			course.put("wtu", 0);
			while (lineScan.hasNext())
			{
				temp = lineScan.next();
				if (temp.contains("lect"))
				{
					course.put("lect", temp.substring(0, temp.lastIndexOf(' ')));
					course.put("wtu", (Integer) course.get("wtu") + lineScan.nextInt());
				}
				else if (temp.contains("lab"))
				{
					course.put("lab", temp.substring(0, temp.lastIndexOf(' ')));
					course.put("wtu", (Integer) course.get("wtu") + lineScan.nextInt());
				}
				else if (temp.contains("activity"))
				{
					course.put("activity", temp.substring(0, temp.lastIndexOf(' ')));
					course.put("wtu", (Integer) course.get("wtu") + lineScan.nextInt());
				}
				else if (temp.contains("supv"))
				{
					course.put("supv", temp.substring(0, temp.lastIndexOf(' ')));
					course.put("wtu", (Integer) course.get("wtu") + lineScan.nextInt());
				}
			}
			lineScan.close();
			
		}
		fileScan.close();*/
		
	}

	/**
	 * Returns a sorted course list, sorted by name and number
	 * @return sorted List of courses
	 */
	public List<HashMap<String, Object>> getCourseList()
	{
		List<HashMap<String, Object>> courseMap = DatabaseCommunicator.queryDatabase("SELECT department,number FROM courses;");
		Collections.sort(courseMap, new CourseComparator());
		return courseMap;
	}
	
	/**
	 * Returns a sorted room list, sorted by building and number
	 * @return sorted List of rooms
	 */
	public List<HashMap<String, Object>> getRoomList()
	{
		List<HashMap<String, Object>> classMap = DatabaseCommunicator.queryDatabase("SELECT building,number FROM rooms;");
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
    	 * Compares the buildings first then room number, putting them in order.
    	 */
    	public int compare(Map<String, Object> Resource1, Map<String, Object> Resource2) 
    	{
    		if (((Integer) Resource1.get("building")) == ((Integer) Resource2.get("building")))
    		{
    			return (Integer) Resource1.get("number") - (Integer) Resource2.get("number");
    		}
    		return (Integer) Resource1.get("building") - (Integer) Resource2.get("building");
    	}
	}
    
	/**
	 * Resource Comparator is the comparator used to compare the Resource's last names to put in alphabetical order.
	 * 
	 * @author Simko
	 *
	 */
    class CourseComparator implements Comparator<Map<String, Object>>
    {
    	/**
    	 * Compares the last names, returning a negative number if the first name comes before the second.
    	 */
    	public int compare(Map<String, Object> Resource1, Map<String, Object> Resource2) 
    	{
    		if (Resource1.get("department").toString().equals(Resource2.get("department").toString()))
    		{
    			return (Integer) Resource1.get("number") - (Integer) Resource2.get("number");
    		}
    		return Resource1.get("department").toString().compareTo(Resource2.get("department").toString());
    	}
	}
    
    
}