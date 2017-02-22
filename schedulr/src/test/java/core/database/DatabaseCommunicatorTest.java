package core.database;

import org.junit.Test;

import junit.framework.TestCase;

public class DatabaseCommunicatorTest extends TestCase{
	
	@Test
	public void testResourceExists() {
		//TODO add new user and check if that user exists rather than checking admin
		assertTrue("Testing admin account exists...", 
				DatabaseCommunicator.resourceExists("users", "login='admin'")); 
		assertFalse("Testing monkey account does not exist...", 
				DatabaseCommunicator.resourceExists("users", "login='monkey'")); 
	}
}
