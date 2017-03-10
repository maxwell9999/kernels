package gui.feedback;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.helpers.Loader;

import core.accounts.AccountManager;
import core.accounts.User;
import core.database.DatabaseCommunicator;
import core.resources.Course;
import core.resources.ResourceManager;
import core.resources.Room;
import core.resources.Schedule;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * UI for viewing time preferences.
 * @author sarahpadlipsky
 * @version March 8, 2017
 */
public class FeedbackViewer {
	
	@FXML private Label loginLabel;
	@FXML private Label scoreLabel;
	@FXML private Label feedbackLabel;
	@FXML private VBox feedbackContainer;
	@FXML private ChoiceBox<String> scheduleChoiceBox;
	
	// List of the terms
	List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	// List of all the feedbacks
	List<HashMap<String,Object>> feedbacks = new ArrayList<HashMap<String,Object>>();

	/**
	 *  Called when controller is initialized to populate GUI.
	 */
	public void initialize() {
		populateChoiceBox();
	}
	
	/**
	 * Populates choice boxes with terms and years
	 */
	public void populateChoiceBox() {
		list = DatabaseCommunicator.queryDatabase("select year,term from schedules;");
		
		ArrayList<String> forChoiceBox = new ArrayList<String>();
		for (HashMap<String,Object> map : list) {
			Integer year = (Integer) map.get("year");
			String stringYear = Integer.toString(year);
			String term = (String ) map.get("term");
			
			forChoiceBox.add(stringYear + " " + term);
		}
		
		scheduleChoiceBox.setItems(FXCollections.observableArrayList(forChoiceBox)); 
		scheduleChoiceBox.setOnAction(new EventHandler<ActionEvent>() {

		    public void handle(ActionEvent event) {
		    			        
		    	String facultyName = (String) scheduleChoiceBox.getValue();
		    	int selectedIndex = scheduleChoiceBox.getSelectionModel().getSelectedIndex();
		    	Integer intYear = (Integer) list.get(selectedIndex).get("year");
		    	String term = (String) list.get(selectedIndex).get("term");
		    	
		    	int id = Schedule.getScheduleId(intYear, term);
		    	System.out.println(id);
		    	populateFeedback(id);

		    }
		});
	}
	
	/**
	 * Updates the feedback list from the database.
	 * @param id id for the current view
	 */
	@FXML
	public void populateFeedback(int id) {
		//Back-end connection to populate courseList
		feedbacks = DatabaseCommunicator.queryDatabase("select * from feedback;");
		feedbackContainer.getChildren().clear();
		for (HashMap<String,Object> map : feedbacks) {
			
			if(((Integer) map.get("schedule_id")).equals(id)) {
				String loginText = (String) map.get("username");
				int score = (int) map.get("rating");
				String feedback = (String) map.get("feedback");
				
				Pane newPane = null;
				
				FXMLLoader loader = null;
				try {
					loader = new FXMLLoader(getClass().getResource("feedbackEntry.fxml"));
					newPane = (Pane) loader.load();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			
	            // Populates GUI.
	            feedbackContainer.getChildren().add(newPane);
	            Label loginLabel = (Label) newPane.lookup("#loginLabel");
	            Label scoreLabel = (Label) newPane.lookup("#scoreLabel");
	            Label feedbackLabel = (Label) newPane.lookup("#messageLabel");
	            
	            loginLabel.setText(loginText);
	            scoreLabel.setText(Integer.toString(score));
	            feedbackLabel.setText(feedback);
			}
		}				
	}
}
