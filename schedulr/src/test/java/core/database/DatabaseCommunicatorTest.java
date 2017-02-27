package core.database;

import org.junit.After;
import org.junit.Test;

import core.accounts.AccountManager;
import core.accounts.User;
import junit.framework.TestCase;

public class DatabaseCommunicatorTest extends TestCase{
	
	@Test
	public void testResourceExists() {
		//TODO add new user and check if that user exists rather than checking admin
		AccountManager.addUser("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0);
		assertTrue("Testing admin account exists...", 
				DatabaseCommunicator.resourceExists("users", "login='Test_User'")); 
		AccountManager.removeUser("Test_User");

		assertFalse("Testing monkey account does not exist...", 
				DatabaseCommunicator.resourceExists("users", "login='monkey'")); 
	}
	
	@After
	public void cleanUp()
	{
		DatabaseCommunicator.deleteDatabase("users", "login='Test_User'");
	}
	
}
