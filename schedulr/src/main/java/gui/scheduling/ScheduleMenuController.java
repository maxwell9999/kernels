package gui.scheduling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gui.accountsUI.LoginViewController;
import gui.accountsUI.ResetPasswordController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScheduleMenuController {
	private static final Logger log = LoggerFactory.getLogger(LoginViewController.class);
    
    @FXML private Button editScheduleButton;
    @FXML private Button addScheduleButton;
    
    @FXML
    private void editScheduleButton(ActionEvent event) throws IOException {
    	Stage currentStage = (Stage) editScheduleButton.getScene().getWindow(); 
    	currentStage.close(); 
    	String fxmlFile = "ScheduleSelectionView.fxml"; 
    	Stage stage = new Stage(); 
    	Pane myPane = null; 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	myPane = (Pane) loader.load(); 
    	// use if need to pass anything to controller
    	//ScheduleSelectionController scheduleSelectionController = loader.<ScheduleSelectionController>getController(); 
    	Scene scene = new Scene(myPane); 
    	stage.setScene(scene);
    	stage.show(); 

    }

    @FXML
    private void addScheduleButton(ActionEvent event) throws IOException {
    	Stage currentStage = (Stage) addScheduleButton.getScene().getWindow(); 
    	currentStage.close(); 
    	String fxmlFile = "AddScheduleView.fxml"; 
    	Stage stage = new Stage(); 
    	Pane myPane = null; 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	myPane = (Pane) loader.load(); 
    	// use if need to pass anything to controller
    	//ScheduleSelectionController scheduleSelectionController = loader.<ScheduleSelectionController>getController(); 
    	Scene scene = new Scene(myPane); 
    	stage.setScene(scene);
    	stage.show(); 

    } 
}