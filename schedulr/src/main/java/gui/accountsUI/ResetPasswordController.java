package gui.accountsUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * UI for password reset.
 * @author sarahpadlipsky
 * @version February 2, 2017
 */
public class ResetPasswordController {

	@FXML private TextField passwordField;
    @FXML private TextField confirmField;
    
    @FXML
    private void saveButton(ActionEvent event) throws IOException
    {
    	// Put database code here. 
    	String password = passwordField.getText();
    	String confirmed = confirmField.getText();
    	System.out.println("Password is " + password);
    	System.out.println("Confirmed is " + confirmed);
    }
}
