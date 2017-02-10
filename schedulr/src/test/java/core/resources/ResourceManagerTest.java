package core.resources;

import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
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
		ResourceManager.addCourse("ABC", 123, "Test Course", 4, 6, 1, null);
		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT name FROM courses WHERE department='ABC' AND number=123;");
		assertEquals("Testing Course Add...", 
				"Test Course", list.get(0).get("name").toString());
		
		ResourceManager.removeCourse("ABC", 123);
		list = DatabaseCommunicator.queryDatabase("SELECT name FROM courses WHERE department='ABC' AND number=123;");
		assertEquals("Testing Room Remove...", 
				0, list.size());
	}
	
}
