package gui.feedback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.database.DatabaseCommunicator;
import core.feedback.Feedback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
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
 
	@FXML private ChoiceBox yearBox; 
	@FXML private ChoiceBox termBox; 
	@FXML private TextField usernameField;
    @FXML private RadioButton button1;
    @FXML private RadioButton button2;
    @FXML private RadioButton button3;
    @FXML private RadioButton button4;
    @FXML private RadioButton button5;
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
		populateYears(); 
		populateTerms(); 
		
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
	
	private void populateYears() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT year from schedules;"); 
		List<Integer> years = new ArrayList<Integer>(); 
		for (HashMap<String, Object> row : rows) {
			years.add(Integer.parseInt(row.get("year").toString())); 
		}
		yearBox.setItems(FXCollections.observableArrayList(years)); 
	}

	private void populateTerms() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT term from schedules;"); 
		List<String> terms = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			terms.add(row.get("term").toString()); 
			System.out.println("adding " + row.get("term").toString() + " to terms");
		}
		termBox.setItems(FXCollections.observableArrayList(terms)); 
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
			int year = Integer.parseInt(yearBox.getValue().toString()); 
			String term = termBox.getValue().toString(); 
			String username = usernameField.getText();
			String note = noteField.getText();
			Feedback feedback = new Feedback(year, term, username, note, buttonToggled);
			feedback.addToDatabase();
			System.out.println(username + " rated it a " + buttonToggled + " and said " + note);
			Stage stage = (Stage)button1.getScene().getWindow();
        	stage.close();
		}
		
    }

}
