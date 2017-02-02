package org.kernels.schedulr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.slf4j.*;

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
