package gui.resourcesUI;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import core.database.DatabaseCommunicator;
import core.resources.ResourceManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

/**
 * UI for adding a course.
 * @author sarahpadlipsky
 * @version February 15, 2017
 */
public class AddCourseController {
	
	private static final String LECTURE = "Lecture";
	private static final String ACTIVITY = "Activity";
	private static final String SUPERVISORY = "Supervisory";
	
	@FXML private Button confirm;
	@FXML private ChoiceBox department;
	@FXML private TextField courseNumber;
	@FXML private TextField courseTitle;
	@FXML private TextField units;
	@FXML private TextField hours;
	@FXML private ChoiceBox type; 
	@FXML private TextArea notes;
	@FXML private CheckBox includesLab;
	
	// Access to ResourceController to update lists.
	private ResourceController resourceController;
	
	public void initialize() {
		//TODO may want to have these reflect the departments in the database
		department.setItems(FXCollections.observableArrayList("CPE", "SE", "CSC"));
		type.setItems(FXCollections.observableArrayList(LECTURE, ACTIVITY, SUPERVISORY));
	}
	
	/**
     * onAction button for adding new course.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == confirm) {
        	String departmentString = department.getValue().toString();
        	int courseNum = Integer.parseInt(courseNumber.getText());
        	String courseName = courseTitle.getText();
        	double unitsInt = Double.parseDouble(units.getText());
        	int hoursInt = Integer.parseInt(hours.getText());
        	String typeString = type.getValue().toString(); 
        	int labHours = 0;
        	String notesString = notes.getText();
        	
        	// Adds the course to the database if it does not already exist.
        	if (DatabaseCommunicator.resourceExists("courses", "department='" + departmentString + "' AND number=" + courseNum)) {
        		//TODO(Sarah): add error box
        		System.err.println("Already in Database");
        	}
        	else {
        		int lectHours = 0; 
        		int actHours = 0; 
        		
        		if (typeString.equals(LECTURE)) {
        			lectHours = hoursInt;
        			if (includesLab.isSelected()) {
        				labHours = lectHours; 
        			}
        		}
        		else if (typeString.equals(ACTIVITY)) {
        			actHours = hoursInt; 
        		}

	        	ResourceManager.addCourse(departmentString, courseNum, courseName, unitsInt, lectHours, 
	        			labHours, actHours, notes.getText()); 

	        	System.out.println(resourceController);
	        	resourceController.populateCourses();
	        	Stage stage = (Stage)confirm.getScene().getWindow();
	        	stage.close();
        	}
        }
	}
	
	/**
	 * Sets resource controller.
	 * @param resourceController access to ResourceController
	 */
	public void setResourceController(ResourceController resourceController) {
		this.resourceController = resourceController;
	}
}
