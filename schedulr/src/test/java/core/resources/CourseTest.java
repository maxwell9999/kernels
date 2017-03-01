package core.resources;

import junit.framework.TestCase;

import org.junit.Test;


public class CourseTest extends TestCase{
	
	Course course = new Course(); 

	@Test
	public void testEmptyConstructor() {
		assertTrue("Testing Course empty constructor...", 
				course instanceof Course);
		assertEquals("", course.getDepartment()); 
		assertEquals(0, course.getNumber()); 
		assertEquals("", course.getName()); 
		assertEquals(0.0, course.getWtu()); 
		assertEquals(0, course.getLectHours()); 
		assertEquals("", course.getNotes()); 
		assertEquals(0, course.getLabHours());	
		assertEquals(0, course.getActHours());	
		
	}
	
	@Test
	public void testSetters() {
		course.setDepartment("CPE");
		assertEquals("Testing Course setDepartment...", 
				"CPE", course.getDepartment()); 
		
		course.setNumber(300);
		assertEquals("Testing Course setNumber...", 
				300, course.getNumber()); 
		
		course.setName("Professional Responsibilities");
		assertEquals("Testing Course setName...", 
				"Professional Responsibilities", course.getName()); 
		
		course.setWtu(5.0);
		assertEquals("Testing Course setWtu...", 
				5.0, course.getWtu()); 
		
		course.setLectHours(3);
		assertEquals("Testing Course setLectHours...", 
				3, course.getLectHours()); 
		
		course.setNotes("test");
		assertEquals("Testing Course setNotes...", 
				"test", course.getNotes()); 
		
		course.setLabHours(3);
		assertEquals("Testing Course setLabHours...", 
				3, course.getLabHours());	
		
		course.setActHours(5);
		assertEquals("Testing Course setActHours...", 
				5, course.getActHours());	

	}
	
	@Test
	public void testNonEmptyConstructor() {
		course = new Course("CPE", 101, "Fundamentals of CS 1", 5.0, 3, null, 3, 0);
		assertTrue("Testing Course non-empty constructor...", 
				course instanceof Course);
		assertEquals("CPE", course.getDepartment()); 
		assertEquals(101, course.getNumber()); 
		assertEquals("Fundamentals of CS 1", course.getName()); 
		assertEquals(5.0, course.getWtu()); 
		assertEquals(3, course.getLectHours()); 
		assertNull(course.getNotes()); 
		assertEquals(3, course.getLabHours());	
		assertEquals("department='CPE' AND number=101", course.getKeyIdentifier());
	}
	
	@Test
	public void testAddWtu() {
		course = new Course(); 
		course.addWtu(5.0);
		assertEquals("Testing Course addWtu...", 
				5.0, course.getWtu()); 
	}
	
}
