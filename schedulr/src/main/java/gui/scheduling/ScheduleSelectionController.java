package gui.scheduling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gui.accountsUI.LoginViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ScheduleSelectionController {
	private static final Logger log = LoggerFactory.getLogger(LoginViewController.class);
    
    @FXML private ComboBox yearBox;
    @FXML private ComboBox termBox;
    @FXML private Button openButton; 
    
    @FXML
    private void openScheduleButton(ActionEvent event) throws IOException {
    	int year = Integer.parseInt(yearBox.getValue().toString()); 
    	String term = termBox.getValue().toString(); 
    	
    	// TODO Load the schedule
    	
    	Stage currentStage = (Stage) openButton.getScene().getWindow(); 
    	currentStage.close(); 

    }
}
