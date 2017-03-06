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
	public void testGetScheduleId(){
		Schedule schedule = new Schedule("XX", 9999); 
		Schedule schedule2 = new Schedule("ZZ", 9999); 
		schedule.addToDatabase();
		schedule2.addToDatabase();
		
		assertEquals("Testing getScheduleId without parameters...", 
				schedule.getScheduleId() + 1, schedule2.getScheduleId()); 
		
		assertEquals("Testing getScheduleId with parameters...",
				Schedule.getScheduleId(9999, "XX") + 1, Schedule.getScheduleId(9999, "ZZ")); 
		
		DatabaseCommunicator.deleteDatabase("schedules", "year=9999");
	}
	
	public void testDatabaseObject() {
		Schedule schedule = new Schedule("XX", 9999); 
		assertEquals("year, term", schedule.getKeys());
		assertEquals("9999, 'XX'", schedule.getValues());
		assertEquals("schedules", schedule.getTable());
		assertEquals("term='XX' AND year=9999", schedule.getKeyIdentifier());
	}

}
