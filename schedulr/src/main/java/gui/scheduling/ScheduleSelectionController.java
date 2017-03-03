package gui.scheduling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.database.DatabaseCommunicator;
import gui.accountsUI.LoginViewController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ScheduleSelectionController {
	private static final Logger log = LoggerFactory.getLogger(LoginViewController.class);
    
    @FXML private ChoiceBox yearBox;
    @FXML private ChoiceBox termBox;
    @FXML private Button openButton; 
    
    @FXML
    public void initialize() {
    	populateYears(); 
    	yearBox.setOnAction(new selectYearHandler());
    }
    
    
    private void populateYears() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT DISTINCT year FROM schedules;"); 
		List<String> years = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			years.add(row.get("year").toString()); 
		}
		yearBox.setItems(FXCollections.observableArrayList(years)); 
    }
    private void populateTerms(int year) {
    	List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT DISTINCT term FROM schedules WHERE year=" + year + ";"); 
		List<String> terms = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			terms.add(row.get("term").toString()); 
		}
		termBox.setItems(FXCollections.observableArrayList(terms)); 
    }
    @FXML
    private void openScheduleButton(ActionEvent event) throws IOException {
    	int year = Integer.parseInt(yearBox.getValue().toString()); 
    	String term = termBox.getValue().toString(); 
    	
    	// TODO Load the schedule
    	
    	Stage currentStage = (Stage) openButton.getScene().getWindow(); 
    	currentStage.close(); 

    }
    
	//Show the list of rooms when the person clicks the room drop down
	class selectYearHandler implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent event) {
			populateTerms(Integer.parseInt(yearBox.getValue().toString())); 
		}
	}
}
