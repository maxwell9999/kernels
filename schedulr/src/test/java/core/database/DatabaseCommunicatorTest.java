package core.database;

import org.junit.Test;

import junit.framework.TestCase;

public class DatabaseCommunicatorTest extends TestCase{
	
	@Test
	public void testResourceExists() {
		assertTrue("Testing admin account exists...", 
				DatabaseCommunicator.resourceExists("users", "login='admin'")); 
		assertFalse("Testing monkey account exists...", 
				DatabaseCommunicator.resourceExists("users", "login='monkey'")); 
	}
}
