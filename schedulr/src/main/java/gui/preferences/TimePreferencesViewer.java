package gui.preferences;

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
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
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
public class TimePreferencesViewer {
	
	
	@FXML private Label loginLabel;
	@FXML private Label ableToTeachLabel;
	@FXML private Label wantToTeachLabel;
	@FXML private VBox timeContainer;
	
	
	// List of MWF Times in database.
	List<HashMap<String, Object>> timesMWF = new ArrayList<HashMap<String,Object>>();
	// List of TR Times in database.
	List<HashMap<String, Object>> timesTR = new ArrayList<HashMap<String,Object>>();


	/**
	 *  Called when controller is initialized to populate GUI.
	 */
	public void initialize() {
		populateTimes();
	}
	
	/**
	 * Updates the course list from the database.
	 */
	@FXML
	public void populateTimes() {
		//Back-end connection to populate courseList
		timesMWF = DatabaseCommunicator.queryDatabase("select * from MWF_Preferences;");
		timesTR = DatabaseCommunicator.queryDatabase("select * from TR_Preferences;");

		timeContainer.getChildren().clear();
		
		int index = 0; 
		for (HashMap<String,Object> mapMTW : timesMWF) {
			
			String loginText = (String) mapMTW.get("login");
			String canTextMWF = (String) mapMTW.get("can");
			String prefTextMWF = (String) mapMTW.get("pref");
			
			String canTextTR = "No preference";
			String prefTextTR = "No preference";
			
			if (canTextMWF.equals("")) {
				canTextMWF = "No preference";
			}
			
			if (prefTextMWF.equals("")) {
				prefTextMWF = "No preference";
			}
			
			for (HashMap<String,Object> mapTR : timesTR) {
				if(loginText.equals(mapTR.get("login"))) {
					canTextTR = (String) mapTR.get("can");
					prefTextTR = (String) mapTR.get("pref");
				}
			}
			
			if (canTextTR.equals("")) {
				canTextTR = "No preference";
			}
			
			if (prefTextTR.equals("")) {
				prefTextTR = "No preference";
			}
			Pane newPane = null;
			
			FXMLLoader loader = null;
			try {
				loader = new FXMLLoader(getClass().getResource("timeEntry.fxml"));
				newPane = (Pane) loader.load();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		
            // Populates GUI.
			timeContainer.getChildren().add(newPane);
            Label loginLabel = (Label) newPane.lookup("#Login");
            Label wantLabelMWF = (Label) newPane.lookup("#MWFPref");
            Label ableLabelMWF = (Label) newPane.lookup("#MWFAble");
            Label wantLabelTR = (Label) newPane.lookup("#TRPref");
            Label ableLabelTR = (Label) newPane.lookup("#TRAble");
            
            loginLabel.setText(loginText);
            wantLabelMWF.setText(prefTextMWF);
            ableLabelMWF.setText(canTextMWF);
            wantLabelTR.setText(prefTextTR);
            ableLabelTR.setText(canTextTR);
		}
		
		for (HashMap<String,Object> mapTR : timesTR) {
			boolean inBoth = false;
			
			for (HashMap<String,Object> mapMWF : timesMWF) {
				if (mapTR.get("login").equals(mapMWF.get("login"))) {
					inBoth = true;
					break;
				}
			}
			
			if (!inBoth) {
				String canTextMWF = "No Preference";
				String prefTextMWF = "No Preference";
				
				String canTextTR = (String) mapTR.get("can");
				String prefTextTR = (String) mapTR.get("pref");
			
			
				Pane newPane = null;
				
				FXMLLoader loader = null;
				try {
					loader = new FXMLLoader(getClass().getResource("timeEntry.fxml"));
					newPane = (Pane) loader.load();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				
				timeContainer.getChildren().add(newPane);
	            Label loginLabel = (Label) newPane.lookup("#Login");
	            Label wantLabelMWF = (Label) newPane.lookup("#MWFPref");
	            Label ableLabelMWF = (Label) newPane.lookup("#MWFAbel");
	            Label wantLabelTR = (Label) newPane.lookup("#TRPref");
	            Label ableLabelTR = (Label) newPane.lookup("#TRAbel");
	            
	            loginLabel.setText((String) mapTR.get("login"));
	            wantLabelMWF.setText(prefTextMWF);
	            ableLabelMWF.setText(canTextMWF);
	            wantLabelTR.setText(prefTextTR);
	            ableLabelTR.setText(canTextTR);
			}
		}
	}
}
