package core.accounts;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import core.database.DatabaseCommunicator;
import gui.accountsUI.ResetPasswordController;
import junit.framework.TestCase;

public class AccountManagerTest extends TestCase{

	@Test
	public void testUserAddEditRemove()
	{
		AccountManager.addUser("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 1);
		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User';");
		assertEquals("Testing User Add", 99999, Integer.parseInt(list.get(0).get("empl_id").toString()));
		
		AccountManager.editUser("Test_User", "email", "newEmail@hotmail.com");
		list = DatabaseCommunicator.queryDatabase("SELECT email FROM users WHERE login='Test_User';");
		assertEquals("Testing User Editing", "newEmail@hotmail.com", list.get(0).get("email"));
				
		AccountManager.removeUser("Test_User");
		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User';");
		assertEquals("Testing User Removal", 0, list.size());
	}
	
	@Test
	public void testResetPassword()
	{
		AccountManager.addUser("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 1);
		List<HashMap<String, Object>> list = DatabaseCommunicator.queryDatabase("SELECT pass_hash,reset_password FROM users WHERE login='Test_User';");
		assertTrue("Testing empl_id password", BCrypt.checkpw("99999", list.get(0).get("pass_hash").toString()));
		assertEquals("Testing Reset Password = 1", 1, Integer.parseInt(list.get(0).get("reset_password").toString()));
		
		AccountManager.resetPassword("Test_User", "potato");
		
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
		List<HashMap<String, Object>> userList = AccountManager.getUserList();
		int numUsers = userList.size();
		assertNotNull("Testing that list exists", userList);
		
		
		AccountManager.addUser("Test_User1", 99999, "Test", "AAAAA", "testUser@gmail.com", "", 1);
		AccountManager.addUser("Test_User2", 99999, "Test", "ZZZZZ", "testUser@gmail.com", "", 1);
		userList = AccountManager.getUserList();
		assertEquals("Testing number of users", numUsers + 2, userList.size());
		assertEquals("Testing first user sorted", "Test_User1", userList.get(0).get("login"));
		assertEquals("Testing last user sorted", "Test_User2", userList.get(userList.size() - 1).get("login"));
		
		AccountManager.removeUser("Test_User1");
		AccountManager.removeUser("Test_User2");
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
}
