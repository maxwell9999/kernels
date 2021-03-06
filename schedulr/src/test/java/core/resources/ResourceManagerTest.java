package core.resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Test;

import core.database.DatabaseCommunicator;


public class ResourceManagerTest extends TestCase{

	@Test
	public void testRoomAddEditGetRemove()
	{
		ResourceManager.addRoom(99, 9904, 1, "lecture", "");

		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT capacity FROM rooms WHERE building=99 AND number=9904;");
		assertEquals("Testing Room Add...", 
				1, Integer.parseInt(list.get(0).get("capacity").toString()));
		
		Room temp = ResourceManager.getRoom(99, 9904);
		assertEquals("Testing Room Get...", 99, temp.getBuilding());
		assertEquals(9904, temp.getNumber()); 
		
		temp.setNotes("This is a note");
		temp.updateRoom();
		
		//Get the room from the database again
		temp = ResourceManager.getRoom(99, 9904);
		assertEquals("Testing Room Get...", 99, temp.getBuilding());
		assertEquals(9904, temp.getNumber()); 
		assertEquals("Testing editing note...", "This is a note", temp.getNotes()); 
		
		ResourceManager.removeRoom(99, 9904);
		list = DatabaseCommunicator.queryDatabase("SELECT * FROM rooms WHERE building=99 AND number=9904;");
		assertEquals("Testing Room Remove...", 
				0, list.size());
	}
	
	public void testCourseAddEditGetRemove()
	{
		ResourceManager.addCourse("ABC", 123, "Test Course", 4, 6, 0, 0, "");
		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT name FROM courses WHERE department='ABC' AND number=123;");
		assertEquals("Testing Course Add...", 
				"Test Course", list.get(0).get("name").toString());
		
		Course temp = ResourceManager.getCourse("ABC", 123); 
		assertEquals("Testing Course Get...", 
				"ABC", temp.getDepartment());
		assertEquals(123, temp.getNumber()); 
		
		//TODO add test for edit
		temp.setNotes("This is a happy note");
		temp.updateCourse();
		
		//Get the course from the database again
		temp = ResourceManager.getCourse("ABC", 123); 
		assertEquals("Testing Course Get...", "ABC", temp.getDepartment());
		assertEquals(123, temp.getNumber());
		assertEquals("Testing edit note", "This is a happy note", temp.getNotes());
		
		ResourceManager.removeCourse("ABC", 123);
		list = DatabaseCommunicator.queryDatabase("SELECT name FROM courses WHERE department='ABC' AND number=123;");
		assertEquals("Testing Room Remove...", 
				0, list.size());
	}
	
	
	@Test
	public void testGetRoomList()
	{
		List<Room> list = ResourceManager.getRoomList("");
		int numRooms = list.size();
		assertNotNull("Testing that list exists", list);
		
		ResourceManager.addRoom(0, 9904, 1, "lecture", "");
		ResourceManager.addRoom(99, 9904, 1, "lecture", "");
		ResourceManager.addRoom(99, 9905, 1, "lecture", "");

		list = ResourceManager.getRoomList("");
		
		assertEquals("Testing number of rooms", numRooms + 3, list.size());
		assertEquals("Testing first room building...", 0, list.get(0).getBuilding());
		assertEquals("Testing first room number...", 9904, list.get(0).getNumber());
		
		//TODO Modify this test. Need to select a subset of rooms to sort
		//NOTE this test will fail once the database is populated
		assertEquals("Testing building sort...", 9904, list.get(list.size() - 2).getNumber());
		assertEquals("Testing room sort...", 9905, list.get(list.size() - 1).getNumber());
		
		ResourceManager.removeRoom(0, 9904);
		ResourceManager.removeRoom(99, 9904);
		ResourceManager.removeRoom(99, 9905);
		list = ResourceManager.getRoomList("");
		assertEquals("Testing number of rooms...", numRooms, list.size());
	}
	
	@Test
	public void testGetCourseList()
	{
		List<Course> list = ResourceManager.getCourseList();
		int numCourse = list.size();
		assertNotNull("Testing that list exists", list);
		
		ResourceManager.addCourse("AAA", 123, "Test Course", 4, 6, 0, 0, "");
		ResourceManager.addCourse("AAA", 124, "Test Course", 4, 6, 0, 1, "");
		ResourceManager.addCourse("ZZZ", 123, "Test Course", 4, 6, 0, 0, "");
		list = ResourceManager.getCourseList();

		assertEquals("Testing number of courses", numCourse + 3, list.size());
		assertEquals("Testing first course dept...", "AAA", list.get(0).getDepartment());
		assertEquals("Testing first course number...", 123, list.get(0).getNumber());
		
		assertEquals("Testing dept sort...", "ZZZ", list.get(list.size() - 1).getDepartment());
		assertEquals("Testing dept sort...", 123, list.get(list.size() - 1).getNumber());
		
		assertEquals("Testing number sort...", 124, list.get(1).getNumber());
		
		ResourceManager.removeCourse("AAA", 123);
		ResourceManager.removeCourse("AAA", 124);
		ResourceManager.removeCourse("ZZZ", 123);
		list = ResourceManager.getCourseList();
		assertEquals("Testing number of courses...", numCourse, list.size());
	}
	
	@Test
	public void testImportList() throws IOException
	{
		File temp = File.createTempFile("temp", ".txt");
		temp.deleteOnExit();
		BufferedWriter out = new BufferedWriter(new FileWriter(temp));
		out.write("ZZZ 999, Computer Science for Dummies, 2 activity, 4 unit, 3 lect, 2 unit, 2 lab, 5 unit, banana");
		out.close();
		ResourceManager.importCourses(temp);
		assertTrue("Testing proper import... ", DatabaseCommunicator.resourceExists("courses", "department='ZZZ' AND number=999"));
		String name = DatabaseCommunicator.queryDatabase("SELECT name FROM courses WHERE department='ZZZ' AND number=999;").get(0).get("name").toString();
		assertEquals("Testing proper name...", "Computer Science for Dummies", name);
		
		File temp2 = File.createTempFile("temp", ".txt");
		temp2.deleteOnExit();
		out = new BufferedWriter(new FileWriter(temp2));
		out.write("ZZZ 999, Computer Science for Smarties, 2 activity, 4 unit, 3 lect, 2 unit, 2 lab, 5 unit, banana");
		out.close();
		ResourceManager.importCourses(temp2);
		assertTrue(DatabaseCommunicator.resourceExists("courses", "department='ZZZ' AND number=999"));
		name = DatabaseCommunicator.queryDatabase("SELECT name FROM courses WHERE department='ZZZ' AND number=999;").get(0).get("name").toString();
		assertEquals("Testing name change...", "Computer Science for Smarties", name);
		
		ResourceManager.removeCourse("ZZZ", 999);
		assertFalse(DatabaseCommunicator.resourceExists("courses", "department='ZZZ' AND number=999"));
	}
	
	@After
	public void cleanUp()
	{
		DatabaseCommunicator.deleteDatabase("rooms", "building=0 and number=9904");
		DatabaseCommunicator.deleteDatabase("rooms", "building=99 and number=9904");
		DatabaseCommunicator.deleteDatabase("rooms", "building=99 and number=9905");
		DatabaseCommunicator.deleteDatabase("courses", "department='ABC' and number=123");
		DatabaseCommunicator.deleteDatabase("courses", "department='AAA' and number=123");
		DatabaseCommunicator.deleteDatabase("courses", "department='AAA' and number=124");
		DatabaseCommunicator.deleteDatabase("courses", "department='ZZZ' and number=123");
		DatabaseCommunicator.deleteDatabase("courses", "department='ZZZ' and number=999");
	}	
}
