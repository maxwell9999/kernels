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
import core.database.DatabaseObject;

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
		File file = new File("/Users/Simko/Downloads/Courseimportfile.txt");
		ArrayList<DatabaseObject> courseList= new ArrayList<DatabaseObject>();
		Course course;
		Scanner fileScan = new Scanner(file);
		Scanner lineScan;
		String[] parsedString;
		double value;
		
		while (fileScan.hasNextLine())
		{
			course = new Course();
			lineScan = new Scanner(fileScan.nextLine());
			course.setDepartment(lineScan.next());
			lineScan.useDelimiter(",");
			course.setNumber(Integer.parseInt(lineScan.next().trim()));
			course.setName(lineScan.next().trim());
			while (lineScan.hasNext())
			{
				parsedString = lineScan.next().trim().split(" ");
				value = Double.parseDouble(parsedString[0]);
				if (parsedString[1].contains("lect"))
				{
					course.setLectHours((int) value);
				}
				else if (parsedString[1].contains("lab"))
				{
					course.setLabHours((int) value);
				}
				else if (parsedString[1].contains("activity"))
				{
					course.setActHours((int) value);
				}
				else if (parsedString[1].contains("unit"))
				{
					course.addWtu(value);
				}
			}
			lineScan.close();
			courseList.add(course);
			
		}
		fileScan.close();
		DatabaseCommunicator.addAllToDatabase(courseList);
		
	}

	/**
	 * Returns a sorted course list, sorted by name and number
	 * @return sorted List of courses
	 */
	public List<Course> getCourseList()
	{
		String dept, name, notes;
		int num, lect_hours, lab_hours, act_hours;
		double wtu;
		
		List<Course> courseList = new ArrayList<Course>();
		List<HashMap<String, Object>> courseMap = DatabaseCommunicator.queryDatabase("SELECT * FROM courses;");
		for(HashMap<String, Object> map: courseMap)
		{
			dept = map.get("department").toString();
			name = map.get("name").toString();
			notes = (String) map.get("notes");
			num = (Integer) map.get("number");
			lect_hours = 0;//(Integer) map.get("lect_hours");
			lab_hours = 0;//(Integer) map.get("lab_hours");
			act_hours = 0;//(Integer) map.get("act_hours");
			wtu = (Double) map.get("wtu");
			courseList.add(new Course(dept, num, name, wtu, lect_hours, notes, lab_hours, act_hours));
		}
		Collections.sort(courseList, new CourseComparator());
		return courseList;
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
    class CourseComparator implements Comparator<Course>
    {
    	/**
    	 * Compares the last names, returning a negative number if the first name comes before the second.
    	 */
    	public int compare(Course Resource1, Course Resource2) 
    	{
    		if (Resource1.getDepartment().equals(Resource2.getDepartment()))
    		{
    			return Resource1.getNumber() - Resource2.getNumber();
    		}
    		return Resource1.getDepartment().compareTo(Resource2.getDepartment());
    	}
	}
    
    
}