package core.resources;

import org.junit.Test;

import core.accounts.FacultyMember;
import junit.framework.TestCase;

public class SectionTest extends TestCase {
	
	@Test
	public void testEmptyConstructor() {
		Section section = new Section(); 
		assertTrue("Testing empty constructor...", 
				section instanceof Section); 
	}
	
	@Test
	public void testNonEmptyConstructor() {
		Schedule schedule = new Schedule(); 
		Course course = new Course("CPE", 101, "Fundamentals of CS 1", 5.0, 3, null, 3, 0);
		FacultyMember instructor = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "");
		Room room = new Room(99, 9904, 1, "lecture", null); 
		
		Section section = new Section(schedule, course, instructor, room, "09:10:00", 1, "MWF"); 
		
		assertTrue("Testing non-empty constructor...", 
				section instanceof Section);

		assertEquals("Testing get department...",
				"CPE", section.getDepartment());
		assertEquals("Testing get number...", 
				101, section.getNumber()); 
		assertEquals("Testing get name...", 
				"Fundamentals of CS 1", section.getName()); 
		assertEquals("Testing get wtu...",
				5.0, section.getWtu()); 
		assertEquals("Testing get lecture hours...",
				3, section.getLectHours()); 
		assertNull("Testing get notes...",
				section.getNotes()); 
		assertEquals("Testing get lab hours...",
				3 , section.getLabHours()); 
		assertEquals("Testing get activity hours...",
				0 , section.getActHours());
		assertEquals("Testing get instructor...",
				instructor, section.getInstructor()); 
		assertEquals("Testing get room...",
				room, section.getRoom()); 
		assertEquals("Testing get start time...",
				"09:10:00", section.getStartTime()); 
		assertEquals("Testing get duration...",
				1, section.getDuration()); 
		assertEquals("Testing get days of week...",
				"MWF", section.getDaysOfWeek()); 

	}

}
