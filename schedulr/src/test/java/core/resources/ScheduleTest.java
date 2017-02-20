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
		
		assertEquals("Testing getScheduleId...", 
				1, schedule.getScheduleId()); 
	}

}
