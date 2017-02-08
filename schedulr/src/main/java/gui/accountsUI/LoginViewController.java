package gui.accountsUI;

import java.io.IOException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    @FXML private Label errorLabel;
    
    private UserAuthenticator auth;
    
    
    public LoginViewController()
    {
    	auth = new UserAuthenticator();
    }
    /**
     * onAction button for faculty login.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
    @FXML
    private void facultyLoginAction(ActionEvent event) throws IOException
    {
    	String userName = usernameField.getText();
    	String password = passwordField.getText();
        System.out.println("Username is " + userName + ", password is " + password);
        List<HashMap<String, Object>> login = auth.checkPassword(userName, password);
        
        //FAILED
        if (login.get(0).get("login") == null)
        {
        	errorLabel.setText("Username and Password do not match.");
        	passwordField.setText("");
        }
        else
        {
        	//RESET PASSWORD
        	if ((Integer) login.get(0).get("reset_password") == 1)
        	{
        		String fxmlFile = "ResetPasswordView.fxml";
        		Stage stage = new Stage();
        		stage.setTitle("Shop Management");
        		Pane myPane = null;
        		myPane = FXMLLoader.load(getClass().getResource(fxmlFile));
        		Scene scene = new Scene(myPane);
        		stage.setScene(scene);
        		stage.show();
        	}
        	errorLabel.setText("");
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
