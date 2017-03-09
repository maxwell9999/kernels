package core.resources;

import org.junit.Test;

import core.accounts.FacultyMember;
import core.database.DatabaseCommunicator;
import gui.scheduling.AddPanelController;
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
		FacultyMember instructor = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0);
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
	
	@Test
	public void testInstructor() {
		
		Schedule schedule = new Schedule(); 
		Course course = new Course("CPE", 101, "Fundamentals of CS 1", 5.0, 3, null, 3, 0);
		FacultyMember instructor = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0);
		Room room = new Room(99, 9904, 1, "lecture", null); 
		
		
		Section section = new Section(schedule, course, instructor, room, "09:10:00", 1, "MWF"); 
		assertEquals(instructor, section.getInstructor());
		
		FacultyMember instructor2 = new FacultyMember("Test_User2", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0);
		section.setInstructor(instructor2);
		assertEquals(instructor2, section.getInstructor());
	}

	@Test
	public void testRoom() {
		
		Schedule schedule = new Schedule(); 
		Course course = new Course("CPE", 101, "Fundamentals of CS 1", 5.0, 3, null, 3, 0);
		FacultyMember instructor = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0);
		Room room = new Room(99, 9904, 1, "lecture", null); 
		
		Section section = new Section(schedule, course, instructor, room, "09:10:00", 1, "MWF"); 
		assertEquals(room, section.getRoom());
		
		Room room2 = new Room(98, 9904, 1, "lecture", null); 
		section.setRoom(room2);
		assertEquals(room2, section.getRoom());
	}
	@Test
	public void testTime() {
		Schedule schedule = new Schedule(); 
		Course course = new Course("CPE", 101, "Fundamentals of CS 1", 5.0, 3, null, 3, 0);
		FacultyMember instructor = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0);
		Room room = new Room(99, 9904, 1, "lecture", null); 
		
		Section section = new Section(schedule, course, instructor, room, "09:10:00", 1, "MWF"); 
		assertEquals("09:10:00", section.getStartTime());
		assertEquals(1, section.getDuration());
		
		section.setStartTime("10:10:00");
		section.setDuration(3);
		assertEquals("10:10:00", section.getStartTime());
		assertEquals(3, section.getDuration());
	}

	@Test
	public void testDaysOfWeek() {
		Schedule schedule = new Schedule(); 
		Course course = new Course("CPE", 101, "Fundamentals of CS 1", 5.0, 3, null, 3, 0);
		FacultyMember instructor = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0);
		Room room = new Room(99, 9904, 1, "lecture", null); 
		
		Section section = new Section(schedule, course, instructor, room, "09:10:00", 1, "MWF");
		assertEquals("MWF", section.getDaysOfWeek());
		
		section.setDaysOfWeek("TR");
		assertEquals("TR", section.getDaysOfWeek());
		
	}	
	
	@Test
	public void testDatabaseObject() {
		Schedule schedule = new Schedule("SP", 9999); 
		Course course = new Course("CPE", 101, "Fundamentals of CS 1", 5.0, 3, null, 3, 0);
		FacultyMember instructor = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0);
		Room room = new Room(99, 9904, 1, "lecture", null); 
		
		Section section = new Section(schedule, course, instructor, room, "09:10:00", 1, "MWF");
		
		//DatabaseCommunicator.createNewSchedule("test", 9999, "SP");
		
		assertEquals("department, course_number, building, room_number, instructor, start_hour, days_of_week, schedule_id", section.getKeys());
		assertEquals("DRAFT_9999_SP", section.getTable(schedule, "draft"));
		assertEquals("department='CPE' AND course_number=101 AND instructor='Test_User' AND start_hour='09:10:00'", section.getKeyIdentifier());
		//assertEquals("DRAFT_9999_SP", section.getTable());

	}
	
	@Test
	public void testCreateLabSection() {
		Schedule schedule = new Schedule(); 
		Course course = new Course("CPE", 101, "Fundamentals of CS 1", 5.0, 3, null, 3, 0);
		FacultyMember instructor = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0);
		Room room = new Room(99, 9904, 1, "lecture", null); 
		
		Section section = new Section(schedule, course, instructor, room, "09:10:00", 50, "MWF"); 
		Section labSection = AddPanelController.createLabSection(section); 
		System.out.println("LAB START TIME: " + labSection.getStartTime());
		assertEquals("Testing lab section creation time for 1 hour class...", 
				"10:10:00", labSection.getStartTime()); 
		
		// Note time of initial section was updated to the time of the lab section
		// section start time is now "10:10:00"
		section.setDuration(80);
		labSection = AddPanelController.createLabSection(section); 
		System.out.println("LAB START TIME: " + labSection.getStartTime());
		assertEquals("Testing lab section create time for 1.5 hour class...", 
				"11:40:00", labSection.getStartTime());
	}
	

}
