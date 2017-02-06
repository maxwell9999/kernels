import org.kernels.schedulr.accounts.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AccountManager am = new AccountManager();
		am.addUser("test", 12345, "Craig", "Yeti", "cyeti@hotmail.com", "", 1);
		am.editUser("test", "office_location", "10-412");
		//am.removeUser("test");

	}

}
