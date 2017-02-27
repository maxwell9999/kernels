package gui.feedback;

import java.io.IOException;

import core.database.DatabaseCommunicator;
import core.feedback.Feedback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * UI for student feedback.
 * @author sarahpadlipsky
 * @version February 13, 2017
 */
public class StudentFeedbackController {
 
	@FXML private TextField usernameField;
    @FXML private RadioButton button1;
    @FXML private RadioButton button2;
    @FXML private RadioButton button3;
    @FXML private RadioButton button4;
    @FXML private RadioButton button5;
    //TODO(Sarah): Make it have a 256 character limit - if not pop up error message 
	@FXML private TextArea noteField;
	@FXML private Label lengthError;
	// Keeps track of which Radio Button has been selected.
	int buttonToggled = 0;
	// Toggle group for all Radio Buttons.
	final ToggleGroup group = new ToggleGroup();
	
	/**
     * Called after all @FXML annotated fields are populated.
     */
	@FXML
    public void initialize() { 
    	button1.setToggleGroup(group);
    	button2.setToggleGroup(group);
    	button3.setToggleGroup(group);
    	button4.setToggleGroup(group);
    	button5.setToggleGroup(group);
    	// Gets currently selected Radio Button.
    	group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    	    public void changed(ObservableValue<? extends Toggle> ov,
    	        Toggle old_toggle, Toggle new_toggle) {
    	            if (group.getSelectedToggle() != null) {
    	            	int count = 1;
    	                for (Toggle current : group.getToggles()) {
    	                	if (current.equals(group.getSelectedToggle())) {
    	                		buttonToggled = count;
    	                		break;
    	                	}
    	                	count++;
    	                }
    	            }                
    	        }
    	});
    }
	
	/**
     * onAction button for saving feedback.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
	@FXML
    private void onActionSave(ActionEvent event) throws IOException
    {
		// Add database code here. 
		// Note: button selected is in buttonToggled field above.
		if (noteField.getText().length() > 256) {
			lengthError.setText("Max count: 256 characters");
		} else {
			String username = usernameField.getText();
			String note = noteField.getText();
			Feedback feedback = new Feedback(username, note, buttonToggled);
			feedback.addToDatabase();
			System.out.println(username + " rated it a " + buttonToggled + " and said " + note);
			Stage stage = (Stage)button1.getScene().getWindow();
        	stage.close();
		}
		
    }

}
