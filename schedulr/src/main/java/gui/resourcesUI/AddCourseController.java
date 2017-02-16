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
	@FXML private Button confirm;
	@FXML private ChoiceBox department;
	@FXML private TextField courseNumber;
	@FXML private TextField courseTitle;
	@FXML private TextField units;
	@FXML private TextField hours;
	@FXML private TextArea notes;
	@FXML private CheckBox includesLab;
	
	// Access to ResourceController to update lists.
	private ResourceController resourceController;
	
	public void initialize() {
		department.setItems(FXCollections.observableArrayList("CPE", "SE", "CSC"));
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
        	int unitsInt = Integer.parseInt(units.getText());
        	int hoursInt = Integer.parseInt(hours.getText());
        	int labHours = 0;
        	String notesString = notes.getText();
        	
        	// Adds the course to the database if it does not already exist.
        	if (DatabaseCommunicator.resourceExists("courses", "department='" + departmentString + "' AND number=" + courseNum)) {
        		//TODO(Sarah): add error box
        		System.err.println("Already in Database");
        	}
        	else {
	        	ResourceManager.addCourse(departmentString, courseNum, courseName, unitsInt, hoursInt, 
	        			(notesString.equals("")) ? "null" : notesString, labHours); 
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
