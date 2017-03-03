package core.accounts;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import core.database.DatabaseCommunicator;

public class UserTest {

	@Test
	public void testSets() {
		AccountManager.addUser("Test_User", 99999, "Joe", "Bobby", "flowerprincess123@yahoo.com", "Hell", User.FACULTY_MEMBER, 0, 0);
		User user = AccountManager.getUser("Test_User");
		assertEquals("Test_User", user.getLogin());
		assertEquals(99999, user.getEmplId());
		assertEquals("Joe", user.getFirstName());
		assertEquals("Bobby", user.getLastName());
		assertEquals("flowerprincess123@yahoo.com", user.getEmail());
		assertEquals("Hell", user.getOfficeLocation());
		
		user.setEmail("satanspawn1@hotmail.com");
		user.setEmplId(10000);
		user.setFirstName("Phillip");
		user.setLastName("Yogurt");
		user.setOfficeLocation("Candyland");
		
		user.updateUser();
		user = AccountManager.getUser("Test_User");
		
		assertEquals("Test_User", user.getLogin());
		assertEquals(10000, user.getEmplId());
		assertEquals("Phillip", user.getFirstName());
		assertEquals("Yogurt", user.getLastName());
		assertEquals("satanspawn1@hotmail.com", user.getEmail());
		assertEquals("Candyland", user.getOfficeLocation());
		
		AccountManager.removeUser("Test_User");
	}
	
	@After
	public void cleanUp()
	{
		DatabaseCommunicator.deleteDatabase("users", "login='Test_User'");
	}

}
