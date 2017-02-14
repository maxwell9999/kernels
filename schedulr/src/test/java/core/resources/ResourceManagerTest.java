package core.resources;

import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import core.accounts.AccountManager;
import core.database.DatabaseCommunicator;

public class ResourceManagerTest extends TestCase{

	@Test
	public void testRoomAddEditRemove()
	{
		ResourceManager.addRoom(99, 9904, 1, "lecture", null, null);
		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT capacity FROM rooms WHERE building=99 AND number=9904;");
		assertEquals("Testing Room Add...", 
				1, Integer.parseInt(list.get(0).get("capacity").toString()));
		
		ResourceManager.removeRoom(99, 9904);
		list = DatabaseCommunicator.queryDatabase("SELECT count(*) FROM rooms;");
		assertEquals("Testing Room Remove...", 
				0, Integer.parseInt(list.get(0).get("count(*)").toString()));
	}
	
	public void testCourseAddEditRemove()
	{
		ResourceManager.addCourse("ABC", 123, "Test Course", 4, 6, null, 0);
		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT name FROM courses WHERE department='ABC' AND number=123;");
		assertEquals("Testing Course Add...", 
				"Test Course", list.get(0).get("name").toString());
		
		ResourceManager.removeCourse("ABC", 123);
		list = DatabaseCommunicator.queryDatabase("SELECT name FROM courses WHERE department='ABC' AND number=123;");
		assertEquals("Testing Room Remove...", 
				0, list.size());
	}
	
	
	@Test
	public void testGetRoomList()
	{
		ResourceManager man = new ResourceManager();
		List<HashMap<String, Object>> list = man.getRoomList();
		int numRooms = list.size();
		assertNotNull("Testing that list exists", list);
		
		ResourceManager.addRoom(0, 9904, 1, "lecture", null, null);
		ResourceManager.addRoom(99, 9904, 1, "lecture", null, null);
		ResourceManager.addRoom(99, 9905, 1, "lecture", null, null);
		list = man.getRoomList();
		assertEquals("Testing number of rooms", numRooms + 3, list.size());
		assertEquals("Testing first room building...", 0, list.get(0).get("building"));
		assertEquals("Testing first room number...", 9904, list.get(0).get("number"));
		
		assertEquals("Testing building sort...", 9904, list.get(list.size() - 2).get("number"));
		assertEquals("Testing room sort...", 9905, list.get(list.size() - 1).get("number"));
		
		ResourceManager.removeRoom(0, 9904);
		ResourceManager.removeRoom(99, 9904);
		ResourceManager.removeRoom(99, 9905);
		list = man.getRoomList();
		assertEquals("Testing number of rooms...", numRooms, list.size());
	}
	
	@Test
	public void testGetCourseList()
	{
		ResourceManager man = new ResourceManager();
		List<HashMap<String, Object>> list = man.getCourseList();
		int numCourse = list.size();
		assertNotNull("Testing that list exists", list);
		
		ResourceManager.addCourse("AAA", 123, "Test Course", 4, 6, null, 0);
		ResourceManager.addCourse("AAA", 124, "Test Course", 4, 6, null, 1);
		ResourceManager.addCourse("ZZZ", 123, "Test Course", 4, 6, null, 0);
		list = man.getCourseList();
		assertEquals("Testing number of courses", numCourse + 3, list.size());
		assertEquals("Testing first course dept...", "AAA", list.get(0).get("department"));
		assertEquals("Testing first course number...", 123, list.get(0).get("number"));
		
		assertEquals("Testing dept sort...", "ZZZ", list.get(list.size() - 1).get("department"));
		assertEquals("Testing dept sort...", 123, list.get(list.size() - 1).get("number"));
		
		assertEquals("Testing number sort...", 124, list.get(1).get("number"));
		
		ResourceManager.removeCourse("AAA", 123);
		ResourceManager.removeCourse("AAA", 124);
		ResourceManager.removeCourse("ZZZ", 123);
		list = man.getCourseList();
		assertEquals("Testing number of courses...", numCourse, list.size());
	}
	
	
}
