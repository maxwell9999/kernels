package core.accounts;

import org.junit.After;
import org.junit.Test;

import core.database.DatabaseCommunicator;
import junit.framework.TestCase;

public class FacultyMemberTest extends TestCase {
		
	@Test
	public void testNonEmptyConstructor() {
		FacultyMember facultyMember = new FacultyMember("Test_User", 99999, "Test", "User", "testUser@gmail.com", "99-999", 0, 0); 
		
		assertTrue("Testing non-empty constructor...", 
				facultyMember instanceof FacultyMember); 
		
		assertEquals("Testing login...",
				"Test_User", facultyMember.getLogin());
		assertEquals("Testing login...",
				99999, facultyMember.getEmplId());
		assertEquals("Testing login...",
				"Test", facultyMember.getFirstName());
		assertEquals("Testing login...",
				"User", facultyMember.getLastName());
		assertEquals("Testing login...",
				"testUser@gmail.com", facultyMember.getEmail());
		assertEquals("Testing login...",
				"99-999", facultyMember.getOfficeLocation());
		
	}
	
}
