package gui.accountsUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.accounts.AccountManager;
import core.accounts.User;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * UI for editing an account.
 * @author sarahpadlipsky
 * @version February 14, 2017
 */
public class EditAccountController {

	private static final int SCHEDULER = 1; 
	private static final int FACULTY_MEMBER = 0; 
    private static final Logger log = LoggerFactory.getLogger(EditAccountController.class);
    private boolean error = false;
    @FXML private TextField username;
    @FXML private TextField employeeID;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField office;
    @FXML private CheckBox checkbox;
    @FXML private Label errorMessage;
    @FXML private Button removeButton;
    
    // Access to faculty directory which is still open.
    private FacultyDirectoryController facultyController;
    // User that was clicked.
    private User currentUser;
    // Observable list so it is known when all items have been loaded.
    private ObservableList<String> list = FXCollections.observableArrayList();
    // Access to AccountManager.
    private AccountManager account;
    
    public EditAccountController()
    {
    	account = new AccountManager();
    	list.addListener(new ListChangeListener<String>() {

    		// Sets fields with information from current user.
            public void onChanged(ListChangeListener.Change<? extends String> change) {
                username.setText(currentUser.getLogin());
                employeeID.setText(Integer.toString(currentUser.getEmplId()));
                firstName.setText(currentUser.getFirstName());
                lastName.setText(currentUser.getLastName());
                email.setText(currentUser.getEmail());
                office.setText(currentUser.getOfficeLocation());
                if (currentUser.getRole() == SCHEDULER)
                	checkbox.setSelected(true);
                
                username.setEditable(false);
                employeeID.setEditable(false);
                firstName.setEditable(false);
                lastName.setEditable(false);
                email.setEditable(false);
                office.setEditable(false);
                checkbox.setDisable(true);
                
            }
        });
    }

    /**
     * onAction function for removing or saving an account.
     * @param event Necessary field for onAction events.
     */
    @FXML
    private void removeOrSaveAccount(ActionEvent event) {
    	
    	// Removes the current user from the database.
    	if (removeButton.getText().equals("Remove")) {
    		AccountManager.removeUser(currentUser.getLogin());
    		facultyController.updateList();
    		Stage currentStage = (Stage) removeButton.getScene().getWindow();
            currentStage.close();
    		
    	} else if (removeButton.getText().equals("Save")) {
    		// TODO(Sarah): What if we remove the user, and then add a new one completely? Hard to save ONLY new info - but what if info is incorrect? Then info has been deleted.
    	}
  
    }
    
    /**
     * onAction function for editing an account.
     * @param event Necessary field for onAction events.
     */
    @FXML
    private void editAccount(ActionEvent event) {
  
    	username.setEditable(true);
        employeeID.setEditable(true);
        firstName.setEditable(true);
        lastName.setEditable(true);
        email.setEditable(true);
        office.setEditable(true);
        checkbox.setDisable(false);
        
        removeButton.setText("Save");
        
    }
    
    /**
     * Sets the FacultyDirectoryController.
     * @param controller controller to set the FacultyDirectoryController to.
     */
    public void setFacultyController(FacultyDirectoryController controller) {
		facultyController = controller;
		
	}
    
    /**
     * Sets the current  User.
     * @param controller user to set the currentUser.
     */
    public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
		
	}
    
    /**
     * Used so the info will load.
     * @param controller user to set the currentUser.
     */
    public void setList(String string) {
		list.add(string);
		
	}

}
