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
 * primary = (building, number)
 * @author Simko
 *
 */

public class ResourceManager
{
	/**
     * Query method to add new room to database
     */
	public static void addRoom(int building, int number, int capacity, String type, String notes)
	{

		Room newRoom = new Room(building, number, capacity, type, notes);
		newRoom.updateRoom();
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

	public static void addCourse(String department, int number, String name, double wtu, int lectHours, int labHours, int actHours, String notes)
	{
		Course newCourse = new Course(department, number, name, wtu, lectHours, notes, labHours, actHours);
		newCourse.updateCourse();
	}
	
	/**
     * Query method to remove course from database
     */
	public static void removeCourse(String department, int number)
	{
		DatabaseCommunicator.deleteDatabase("courses", "department='" + department + "' AND number=" + number + ";");
	}
	
	/** 
	 * 
	 * @param department the department offering the course; max length=4 characters
	 * @param number the course number (different than section number)
	 * @return Course object with fields filled in from the database 
	 */
	public static Course getCourse(String department, int courseNumber) {
		Course course = new Course(); 
		List<HashMap<String, Object>> courseAttributes = DatabaseCommunicator.queryDatabase(
				"SELECT * FROM courses WHERE department='" + department + "' AND number=" + courseNumber + ";");
		HashMap<String, Object> attributeMap = courseAttributes.get(0);
		
		course.setDepartment(attributeMap.get("department").toString());
		course.setNumber(Integer.parseInt(attributeMap.get("number").toString()));
		course.setName(attributeMap.get("name").toString());
		course.setWtu(Double.parseDouble(attributeMap.get("wtu").toString()));
		course.setLectHours(Integer.parseInt(attributeMap.get("lect_hours").toString()));
		course.setLabHours(Integer.parseInt(attributeMap.get("lab_hours").toString()));
		course.setActHours(Integer.parseInt(attributeMap.get("act_hours").toString()));
		course.setNotes(attributeMap.get("notes").toString());
	
		return course; 
	}
	
	public static Room getRoom(int building, int roomNumber) {
		Room room = new Room(); 
		
		List<HashMap<String, Object>> roomAttributes = DatabaseCommunicator.queryDatabase(
				"SELECT * FROM rooms WHERE building='" + building + "' AND number=" + roomNumber + ";");
		HashMap<String, Object> attributeMap = roomAttributes.get(0);
		
		room.setBuilding(Integer.parseInt(attributeMap.get("building").toString()));
		room.setNumber(Integer.parseInt(attributeMap.get("number").toString()));
		room.setCapacity(Integer.parseInt(attributeMap.get("capacity").toString()));
		room.setType(attributeMap.get("type").toString());
		room.setNotes(attributeMap.get("notes").toString());
		
		return room; 
	}
	
	/**
	 * import the courses from a properly formatted txt file
	 * @param file
	 */
	public static void importCourses(File file)
	{
		try {
			//File file = new File("/Users/Simko/Downloads/Courseimportfile.txt");
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
					try {
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
					} catch (NumberFormatException num) {
						//skip that parse, not formatted correctly
					}
				}
				courseList.add(course);
				lineScan.close();
				
			}
			fileScan.close();
			DatabaseCommunicator.addAllToDatabase(courseList);
		}
		catch (Exception ex)
		{
			System.err.println("Invalid File");
			ex.printStackTrace();
		}
		
	}

	/**
	 * Returns a sorted course list, sorted by name and number
	 * @return sorted List of courses
	 */
	public static List<Course> getCourseList()
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
			lect_hours = (Integer) map.get("lect_hours");
			lab_hours = (Integer) map.get("lab_hours");
			act_hours = (Integer) map.get("act_hours");
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
	public static List<Room> getRoomList(String identifier) {
		String notes, type;
		int building, number, capacity;
		
		List<Room> roomList = new ArrayList<Room>();
		// handle where clause
		List<HashMap<String, Object>> rooms = DatabaseCommunicator.queryDatabase("SELECT * FROM rooms;");
		for(HashMap<String, Object> map: rooms)
		{
			building = (Integer) map.get("building");
			number = (Integer) map.get("number"); 
			type = (String) map.get("type");
			capacity = (Integer) map.get("capacity");
			notes = (String) map.get("lect_hours");
			roomList.add(new Room(building, number, capacity, type, notes));
		}
		Collections.sort(roomList, new RoomComparator());
		return roomList;
	}
	
	/**
	 * Resource Comparator is the comparator used to compare the Resource's last names to put in alphabetical order.
	 * 
	 * @author Simko
	 *
	 */
    private static class RoomComparator implements Comparator<Room>
    {
    	/**
    	 * Compares the buildings first then room number, putting them in order.
    	 */
    	public int compare(Room Resource1, Room Resource2) 
    	{
    		if (Resource1.getBuilding() == Resource2.getBuilding())
    		{
    			return Resource1.getNumber() - Resource2.getNumber();
    		}
    		return Resource1.getBuilding() - Resource2.getBuilding();
    	}
	}
    
	/**
	 * Resource Comparator is the comparator used to compare the Resource's last names to put in alphabetical order.
	 * 
	 * @author Simko
	 *
	 */
    private static class CourseComparator implements Comparator<Course>
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
