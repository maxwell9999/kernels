package gui.feedback;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

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
	@FXML private TextArea noteField;
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
    	    	
    	// Gets currently selected Radio Butotn.
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
		String username = usernameField.getText();
		String note = noteField.getText();
		System.out.println(username + " rated it a " + buttonToggled + " and said " + note);
    }

}
