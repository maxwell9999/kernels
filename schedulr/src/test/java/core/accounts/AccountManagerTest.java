package core.accounts;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import core.database.DatabaseCommunicator;
import gui.accountsUI.ResetPasswordController;
import junit.framework.TestCase;

public class AccountManagerTest extends TestCase{

	@Test
	public void testUserAddEditRemove()
	{
		AccountManager.addUser("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", User.SCHEDULER, 0, 0);
		AccountManager.addUser("Test_User12", 99999, "AAA", "User", "testUser@gmail.com", "", User.FACULTY_MEMBER, 3.0, 4.0);
		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User';");
		assertEquals("Testing User Add", 99999, Integer.parseInt(list.get(0).get("empl_id").toString()));
		
		User test = AccountManager.getUser("Test_User");
		test.setEmail("newEmail@hotmail.com");
		test.updateUser();
		list = DatabaseCommunicator.queryDatabase("SELECT email FROM users WHERE login='Test_User';");
		assertEquals("Testing User Editing", "newEmail@hotmail.com", list.get(0).get("email"));
		test = AccountManager.getUser("Test_User");
		
		test = AccountManager.getUser("Test_User12");
		AccountManager.changeRole(test, User.SCHEDULER);
		list = DatabaseCommunicator.queryDatabase("SELECT email FROM users WHERE login='Test_User12';");
		test = AccountManager.getUser("Test_User");
		assertEquals(User.SCHEDULER, test.getRole());
				
		AccountManager.removeUser("Test_User");
		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User';");
		assertEquals("Testing User Removal", 0, list.size());
		AccountManager.removeUser("Test_User12");
		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User12';");
		assertEquals("Testing User Removal", 0, list.size());
	}
	
	@Test
	public void testResetPassword()
	{
		AccountManager.addUser("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 1, 0, 0);
		List<HashMap<String, Object>> list = DatabaseCommunicator.queryDatabase("SELECT pass_hash,reset_password FROM users WHERE login='Test_User';");
		assertTrue("Testing empl_id password", BCrypt.checkpw("99999", list.get(0).get("pass_hash").toString()));
		assertEquals("Testing Reset Password = 1", 1, Integer.parseInt(list.get(0).get("reset_password").toString()));
		
		AccountManager.getUser("Test_User").changePassword("potato");
		
		list = DatabaseCommunicator.queryDatabase("SELECT pass_hash,reset_password FROM users WHERE login='Test_User';");
		assertTrue("Testing reset password", BCrypt.checkpw("potato", list.get(0).get("pass_hash").toString()));
		assertEquals("Testing Reset Password = 0", 0, Integer.parseInt(list.get(0).get("reset_password").toString()));
		
		AccountManager.removeUser("Test_User");
		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User';");
		assertEquals("Testing User Removal", 0, list.size());
	}
	
	@Test
	public void testGetUserList()
	{
		List<User> userList = AccountManager.getUserList();
		int numUsers = userList.size();
		assertNotNull("Testing that list exists", userList);
		
		
		AccountManager.addUser("Test_User1", 99999, "Test", "AAAAA", "testUser@gmail.com", "", 1, 0, 0);
		AccountManager.addUser("Test_User2", 99999, "Test", "ZZZZZ", "testUser@gmail.com", "", 1, 0, 0);
		AccountManager.addUser("Test_User3", 99999, "AAA", "AAAAA", "testUser@gmail.com", "", 1, 0, 0);
		userList = AccountManager.getUserList();
		assertEquals("Testing number of users", numUsers + 3, userList.size());
		assertEquals("Testing first user sorted", "Test_User3", userList.get(0).getLogin());
		assertEquals("Testing first user sorted", "Test_User1", userList.get(1).getLogin());
		assertEquals("Testing last user sorted", "Test_User2", userList.get(userList.size() - 1).getLogin());
		
		AccountManager.removeUser("Test_User1");
		AccountManager.removeUser("Test_User2");
		AccountManager.removeUser("Test_User3");
		userList = AccountManager.getUserList();
		assertEquals("Testing number of users", numUsers, userList.size());
	}
	
	public void testValidPassword() {
		assertFalse("Testing valid password (cow)...", ResetPasswordController.isValid("cow")); 
		assertTrue("Testing valid password (TestPassword1)...", ResetPasswordController.isValid("TestPassword1")); 
		assertFalse("Testing valid password (testpassword1)...", ResetPasswordController.isValid("testpassword1")); 
		assertFalse("Testing valid password (TESTPASSWORD123)...", ResetPasswordController.isValid("TESTPASSWORD123"));  
		assertFalse("Testing valid password (aReaLLyLongPassword1234567890)...", ResetPasswordController.isValid("aReaLLyLongPassword1234567890")); 
	}
	
	public void testGetUser() {
		AccountManager.addUser("Test_User1", 99999, "Test", "AAAAA", "testUser@gmail.com", "", User.SCHEDULER, 0, 0);
		User test = AccountManager.getUser("Test_User1");
		assertTrue(test instanceof DepartmentScheduler);
		assertEquals(User.SCHEDULER, test.getRole());
		assertEquals(99999, test.getEmplId());
		assertEquals("testUser@gmail.com", test.getEmail());
		assertEquals("AAAAA", test.getLastName());
		assertEquals("", test.getOfficeLocation());
		AccountManager.removeUser("Test_User1");
	}
	
	@After
	public void cleanUp()
	{
		DatabaseCommunicator.deleteDatabase("users", "empl_id=99999");
	}
}
