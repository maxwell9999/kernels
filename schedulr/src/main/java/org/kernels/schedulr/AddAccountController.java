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
public class AddAccountController {

    private static final Logger log = LoggerFactory.getLogger(AddAccountController.class);
    
    @FXML private TextField username;
    @FXML private TextField employeeID;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField office;
    @FXML private CheckBox checkbox;

    /**
     * onAction button for saving new account.
     * @param event Necessary field for onAction events.
     */
    @FXML
    private void saveAccount(ActionEvent event)
    {
    	String userNameString = username.getText();
    	String employeeIDString = employeeID.getText();
    	String firstNameString = firstName.getText();
    	String lastNameString = lastName.getText();
    	String emailString = email.getText();
    	String officeString = office.getText();
    	
    	Boolean scheduler = checkbox.isSelected();

        System.out.println("Username is " + userNameString);
        System.out.println("EmployeeID is " + employeeIDString);
        System.out.println("First Name" + firstNameString);
        System.out.println("Last Name is " + lastNameString);
        System.out.println("Email is " + emailString);
        System.out.println("Office is " + officeString);
        System.out.println("Department Scheduler? " + scheduler);
    }
  
    

}
