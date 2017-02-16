package gui.resourcesUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.accounts.AccountManager;
import core.database.DatabaseCommunicator;
import gui.accountsUI.FacultyDirectoryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * UI for adding account.
 * @author sarahpadlipsky
 * @version February 6, 2017
 */
public class AddAccountController {

	private static final int SCHEDULER = 1; 
	private static final int FACULTY_MEMBER = 0; 
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
    
    private ResourceController resourceController;
    
    private AccountManager account;
    
    public AddAccountController()
    {
    	account = new AccountManager();
    }
    
    
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
    	
    	if (userNameString.equals("") || employeeIDString.equals("") || 
    			emailString.equals("") || firstNameString.equals("") || lastNameString.equals("")) {
    		error = true;
    	}
    	
    	if (!error) {
    		// Do database saving of account.
    		errorMessage.setText("");
            int role = scheduler ? SCHEDULER : FACULTY_MEMBER;
            
            if (DatabaseCommunicator.resourceExists("users", "login='" + userNameString + "'")) {
            	errorMessage.setText("Login already exists");
            	errorMessage.setAlignment(Pos.CENTER);
            }
            else {
	            AccountManager.addUser(userNameString, Integer.parseInt(employeeIDString), 
	            		firstNameString, lastNameString, emailString, officeString, role);
	            resourceController.populateFaculty();
	            Stage currentStage = (Stage) checkbox.getScene().getWindow();
                currentStage.close();
    		}
            
            
    	} else {
    		// There was an error.
    		String errorString = "* Fields required.";
    		errorMessage.setText(errorString);
    		errorMessage.setAlignment(Pos.CENTER);
    	}
    }
    
    /**
     * Sets the ResourceController.
     * @param controller controller to set the ResourceController to.
     */
    public void setResourceController(ResourceController resourceController) {
    	this.resourceController = resourceController;
		
	}

}
