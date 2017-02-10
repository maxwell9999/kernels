package core.resources;

import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import core.database.DatabaseCommunicator;

public class ResourceManagerTest extends TestCase{

	@Test
	public void testRoomAddEditRemove()
	{
		ResourceManager acctMan= new ResourceManager();
		acctMan.addRoom(99, 9904, 1, null, null);
		List<HashMap<String, Object>> list;

		list = DatabaseCommunicator.queryDatabase("SELECT capacity FROM rooms WHERE building=99 AND number=9904;");
		assertEquals("Testing Room Add", 1, Integer.parseInt(list.get(0).get("capacity").toString()));
	}
	
	/*@Test
	public void testResetPassword()
	{
		ResourceManager acctMan= new ResourceManager();
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
	
	@Test
	public void testGetUserList()
	{
		ResourceManager acctMan= new ResourceManager();
		List<HashMap<String, Object>> userList = acctMan.getUserList();
		int numUsers = userList.size();
		assertNotNull("Testing that list exists", userList);
		
		
		acctMan.addUser("Test_User1", 99999, "Test", "AAAAA", "testUser@gmail.com", "", 1);
		acctMan.addUser("Test_User2", 99999, "Test", "ZZZZZ", "testUser@gmail.com", "", 1);
		userList = acctMan.getUserList();
		assertEquals("Testing number of users", numUsers + 2, userList.size());
		assertEquals("Testing first user sorted", "Test_User1", userList.get(0).get("login"));
		assertEquals("Testing last user sorted", "Test_User2", userList.get(userList.size() - 1).get("login"));
		
		acctMan.removeUser("Test_User1");
		acctMan.removeUser("Test_User2");
		userList = acctMan.getUserList();
		assertEquals("Testing number of users", numUsers, userList.size());
	}*/
}
