package core.resources;

import org.junit.Test;

import core.database.DatabaseCommunicator;
import junit.framework.TestCase;

public class ScheduleTest extends TestCase {
	
	@Test
	public void testEmptyConstructor() {
		Schedule schedule = new Schedule(); 
		assertTrue("Testing empty constructor...", 
				schedule instanceof Schedule); 
	}
	
	@Test 
	public void testNonEmptyConstructor() {
		Schedule schedule = new Schedule("F", 2017); 
		assertTrue("Testing non-empty constructor...", 
				schedule instanceof Schedule); 
		assertEquals("F", schedule.getTerm()); 
		assertEquals(2017, schedule.getYear()); 
		
	}
	
	@Test
	public void testSetters() {
		Schedule schedule = new Schedule(); 
		
		schedule.setTerm("F");
		assertEquals("Testing setTerm...",
				"F", schedule.getTerm());
		
		schedule.setYear(2017);
		assertEquals("Testing setYear", 
				2017, schedule.getYear()); 
	}
	
	@Test
	public void testGetScheduleId() {
		
		DatabaseCommunicator.deleteDatabase("schedules", "year=99999;");
		
		Schedule schedule = new Schedule("XX", 99999); 
		Schedule schedule2 = new Schedule("ZZ", 99999); 
		
		DatabaseCommunicator.createNewScheduleTable("DRAFT", 99999, "XX");
		schedule.addToDatabase();
		DatabaseCommunicator.createNewScheduleTable("DRAFT", 99999, "ZZ");
		schedule2.addToDatabase();
		
		assertEquals("Testing getScheduleId without parameters...", 
				schedule.getScheduleId() + 1, schedule2.getScheduleId()); 
		
		assertEquals("Testing getScheduleId with parameters...",
				Schedule.getScheduleId(99999, "XX") + 1, Schedule.getScheduleId(99999, "ZZ")); 
		
		DatabaseCommunicator.deleteDatabase("schedules", "year=99999;");
		DatabaseCommunicator.deleteScheduleTable("DRAFT", 99999, "XX");
		DatabaseCommunicator.deleteScheduleTable("DRAFT", 99999, "ZZ");
	}
	
	public void testDatabaseObject() {
		Schedule schedule = new Schedule("XX", 9999); 
		assertEquals("year, term", schedule.getKeys());
		assertEquals("9999, 'XX'", schedule.getValues());
		assertEquals("schedules", schedule.getTable());
		assertEquals("term='XX' AND year=9999", schedule.getKeyIdentifier());
	}

}
