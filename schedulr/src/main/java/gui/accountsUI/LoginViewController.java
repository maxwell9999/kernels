package gui.accountsUI;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import core.accounts.UserAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UI for login.
 * @author sarahpadlipsky
 * @version February 2, 2017
 */
public class LoginViewController {

    private static final Logger log = LoggerFactory.getLogger(LoginViewController.class);
    
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button facultyLoginButton;
    
    private UserAuthenticator auth;
    
    
    public LoginViewController()
    {
    	auth = new UserAuthenticator();
    }
    /**
     * onAction button for faculty login.
     * @param event Necessary field for onAction events.
     */
    @FXML
    private void facultyLoginAction(ActionEvent event)
    {
    	String userName = usernameField.getText();
    	String password = passwordField.getText();
        System.out.println("Username is " + userName + ", password is " + password);
        List<HashMap<String, Object>> login = auth.checkPassword(userName, password);
        
        //FAILED
        if (login.get(0).get("login") == null)
        {
        	
        }
        else
        {
        	//RESET PASSWORD
        	if ((Integer) login.get(0).get("reset_password") == 1)
        	{
        		
        	}
        	System.out.println("User: " + login.get(0).get("login"));
        	System.out.println("Reset: " + login.get(0).get("reset_password"));
        }
        
    }
    
    /**
     * Continue button for student login.
     * @param event Necessary field for onAction events.
     */
    @FXML
    public void studentLoginAction(ActionEvent event)
    {
        System.out.println("Open to new page.");
    }
    

}
