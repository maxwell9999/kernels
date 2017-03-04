package gui.scheduling;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.database.DatabaseCommunicator;
import core.resources.Schedule;
import gui.accountsUI.LoginViewController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AddScheduleController {
	private static final Logger log = LoggerFactory.getLogger(LoginViewController.class);
    
    @FXML private TextField yearField;
    @FXML private ChoiceBox termSelector;
    @FXML private Button createScheduleButton; 
    @FXML private Label errorLabel; 
    
    @FXML 
    public void initialize() {
    	ArrayList<String> terms = new ArrayList<String>(Arrays.asList("F", "W", "SP", "SU")); 
		termSelector.setItems(FXCollections.observableArrayList(terms)); 
    }
    
    @FXML
    private void createScheduleAction(ActionEvent event) throws IOException, SQLException {
    	int year = Integer.parseInt(yearField.getText()); 
    	String term = termSelector.getValue().toString(); 
    	Schedule schedule = new Schedule(term, year); 
    	
    	if (!DatabaseCommunicator.scheduleExists("draft", year, term)) { 
    		DatabaseCommunicator.createNewSchedule("draft", year, term);
    		Stage currentStage = (Stage) createScheduleButton.getScene().getWindow(); 
        	currentStage.close(); 
    	}
    	else {
    		// ERROR
    		errorLabel.setText("Schedule already exists");
    		errorLabel.setTextAlignment(TextAlignment.CENTER);
    		yearField.setText("");
    	}
    }
    
    
}
