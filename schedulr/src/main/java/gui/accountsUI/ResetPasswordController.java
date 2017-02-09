package gui.accountsUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * UI for password reset.
 * @author sarahpadlipsky
 * @version February 8, 2017
 */
public class ResetPasswordController {

	@FXML private TextField passwordField;
    @FXML private TextField confirmField;
    @FXML private Button saveButton;
    @FXML private Label errorLabel;
    
    /**
     * onAction button for saving new password.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
    @FXML
    private void saveButton(ActionEvent event) throws IOException
    {
    	String password = passwordField.getText();
    	String confirmed = confirmField.getText();
    	System.out.println("Password is " + password);
    	System.out.println("Confirmed is " + confirmed);
        
        if (password.equals(confirmed)) {
        	// Put database code here. 
        	// To close current window.
        	Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        
        } else {
        	errorLabel.setText("Passwords do not match.");
        	passwordField.setText("");
        	confirmField.setText("");
        }

        // TODO(Sarah): Will also have it open another application and not go back to login screen.
    	
    }
}
