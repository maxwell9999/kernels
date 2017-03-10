package core.database;

import org.junit.After;
import org.junit.Test;

import core.accounts.AccountManager;
import core.accounts.DepartmentScheduler;
import core.accounts.User;
import junit.framework.TestCase;

public class DatabaseCommunicatorTest extends TestCase{
	
	@Test
	public void testResourceExists() {
		User user = new DepartmentScheduler("Test_User", 99999, "Test", "User", "testUser@gmail.com", "");
		AccountManager.addUser("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 0, 0, 0);
		assertTrue("Testing account exists...", 
				DatabaseCommunicator.resourceExists(user)); 
		AccountManager.removeUser("Test_User");

		user = new DepartmentScheduler("monkey", 99999, "Test", "User", "testUser@gmail.com", "");
		assertFalse("Testing monkey account does not exist...", 
				DatabaseCommunicator.resourceExists(user)); 
	}
	
	@After
	public void cleanUp()
	{
		DatabaseCommunicator.deleteDatabase("users", "login='Test_User'");
	}
	
}
