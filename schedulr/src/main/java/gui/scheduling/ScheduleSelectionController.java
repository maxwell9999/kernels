package gui.scheduling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.accounts.AccountManager;
import core.accounts.FacultyMember;
import core.database.DatabaseCommunicator;
import core.resources.Course;
import core.resources.ResourceManager;
import core.resources.Room;
import core.resources.Schedule;
import core.resources.Section;
import gui.accountsUI.LoginViewController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ScheduleSelectionController {
	private static final Logger log = LoggerFactory.getLogger(LoginViewController.class);
    
    @FXML private ChoiceBox yearBox;
    @FXML private ChoiceBox termBox;
    @FXML private ChoiceBox typeBox;  
    @FXML private Button openButton; 
    
    @FXML
    public void initialize() {
    	populateYears(); 
    	yearBox.setOnAction(new selectYearHandler());
    	
    	List<String> types = new ArrayList<String>(Arrays.asList("Draft", "Pre-Registration", "Post-Registration"));
    	typeBox.setItems(FXCollections.observableArrayList(types));
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
    	String status = typeBox.getValue().toString(); 
    	//TODO add a typeBox for draft, prereg published or postreg published
    	Schedule schedule = new Schedule(term, year); 
    	
    	loadSchedule(status, year, term, schedule); 
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
    	MainWindow.getController().setSchedule(schedule);
    	Stage currentStage = (Stage) openButton.getScene().getWindow(); 
    	currentStage.close(); 

    }
    
    private List<Section> loadSchedule(String status, int year, String term, Schedule schedule) {
    	List<Section> sections = new ArrayList<Section>(); 

    	String tableName = status + "_" + year + "_" + term; 
    	List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT * from " + tableName + ";"); 
    	for (HashMap<String, Object> row : rows) {
    		String department = row.get("department").toString(); 
    		int courseNumber = Integer.parseInt(row.get("course_number").toString()); 
    		Course course = ResourceManager.getCourse(department, courseNumber); 
    		
    		FacultyMember instructor = (FacultyMember) AccountManager.getUser(row.get("instructor").toString()); 
    		
    		int building = Integer.parseInt(row.get("building").toString());
    		int roomNumber = Integer.parseInt(row.get("building").toString());
    		Room room = ResourceManager.getRoom(building, roomNumber); 
    		
    		String startTime = row.get("start_hour").toString(); 
    		int duration = Integer.parseInt(row.get("duration").toString());
    		String daysOfWeek = row.get("days_of_week").toString(); 
    		Section section = new Section(schedule, course, instructor, room, startTime, duration, daysOfWeek); 
    		sections.add(section);
    	}

    	return sections; 
    }
    
	//Show the list of rooms when the person clicks the room drop down
	class selectYearHandler implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent event) {
			populateTerms(Integer.parseInt(yearBox.getValue().toString())); 
		}
	}
}
