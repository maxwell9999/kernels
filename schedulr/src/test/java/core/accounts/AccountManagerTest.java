package core.accounts;

import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import core.database.DatabaseCommunicator;
import org.mindrot.jbcrypt.BCrypt;

public class AccountManagerTest extends TestCase{

	@Test
	public void testUserAddEditRemove()
	{
		AccountManager acctMan= new AccountManager();
		acctMan.addUser("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 1);
		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User';");
		assertEquals("Testing User Add", 99999, Integer.parseInt(list.get(0).get("empl_id").toString()));
		
		acctMan.editUser("Test_User", "email", "newEmail@hotmail.com");
		list = DatabaseCommunicator.queryDatabase("SELECT email FROM users WHERE login='Test_User';");
		assertEquals("Testing User Editing", "newEmail@hotmail.com", list.get(0).get("email"));
		
		acctMan.removeUser("Test_User");
		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User';");
		assertEquals("Testing User Removal", 0, list.size());
	}
	
	@Test
	public void testResetPassword()
	{
		AccountManager acctMan= new AccountManager();
		acctMan.addUser("Test_User", 99999, "Test", "User", "testUser@gmail.com", "", 1);
		List<HashMap<String, Object>> list = DatabaseCommunicator.queryDatabase("SELECT pass_hash,reset_password FROM users WHERE login='Test_User';");
		assertTrue("Testing empl_id password", BCrypt.checkpw("99999", list.get(0).get("pass_hash").toString()));
		assertEquals("Testing Reset Password = 1", 1, Integer.parseInt(list.get(0).get("reset_password").toString()));
		
		acctMan.resetPassword("Test_User", "potato");
		
		list = DatabaseCommunicator.queryDatabase("SELECT pass_hash,reset_password FROM users WHERE login='Test_User';");
		assertTrue("Testing reset password", BCrypt.checkpw("potato", list.get(0).get("pass_hash").toString()));
		assertEquals("Testing Reset Password = 0", 0, Integer.parseInt(list.get(0).get("reset_password").toString()));
		
		acctMan.removeUser("Test_User");
		list = DatabaseCommunicator.queryDatabase("SELECT empl_id FROM users WHERE login='Test_User';");
		assertEquals("Testing User Removal", 0, list.size());
	}
}
