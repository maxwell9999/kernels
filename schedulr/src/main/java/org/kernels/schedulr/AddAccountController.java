package org.kernels.schedulr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.util.ArrayList;

import org.slf4j.*;

/**
 * UI for adding account.
 * @author sarahpadlipsky
 * @version February 6, 2017
 */
public class AddAccountController {

    private static final Logger log = LoggerFactory.getLogger(AddAccountController.class);
    private boolean error = false;
    @FXML private TextField username;
    @FXML private TextField employeeID;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField office;
    @FXML private CheckBox checkbox;
    @FXML private Label errorMessage;

    /**
     * onAction button for saving new account.
     * @param event Necessary field for onAction events.
     */
    @FXML
    private void saveAccount(ActionEvent event)
    {
    	error = false;
    	String userNameString = username.getText();
    	String employeeIDString = employeeID.getText();
    	String firstNameString = firstName.getText();
    	String lastNameString = lastName.getText();
    	String emailString = email.getText();
    	String officeString = office.getText();
    	Boolean scheduler = checkbox.isSelected();
    	
    	if (userNameString.equals("") || employeeIDString.equals("") || emailString.equals("") || firstNameString.equals("") || lastNameString.equals("")) {
    		error = true;
    	}
    	
    	if (!error) {
    		// Do database saving of account.
    		errorMessage.setText("");
    		System.out.println("Username is " + userNameString);
            System.out.println("EmployeeID is " + employeeIDString); 
            System.out.println("First Name" + firstNameString);
            System.out.println("Last Name is " + lastNameString);
            System.out.println("Email is " + emailString);
            System.out.println("Office is " + officeString);
            System.out.println("Department Scheduler? " + scheduler);
    		
    	} else {
    		// There was an error.
    		String errorString = "* Fields required.";
    		errorMessage.setText(errorString);
    		errorMessage.setAlignment(Pos.CENTER);
    	}
    }
  
    

}
