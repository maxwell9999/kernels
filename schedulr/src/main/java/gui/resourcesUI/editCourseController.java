package gui.resourcesUI;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import core.resources.ResourceManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class editCourseController {
	@FXML private Button confirm;
	@FXML private ChoiceBox department;
	@FXML private TextField courseNumber;
	@FXML private TextField courseTitle;
	@FXML private TextField units;
	@FXML private TextField hours;
	@FXML private TextArea notes;
	@FXML private CheckBox includesLab;
	
	public void initialize() {
		department.setItems(FXCollections.observableArrayList("CPE", "SE", "CSC"));
	}
	
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == confirm) {
        	String departmentString = department.getValue().toString();
        	int courseNum = Integer.parseInt(courseNumber.getText());
        	String courseName = courseTitle.getText();
        	int unitsInt = Integer.parseInt(units.getText());
        	int hoursInt = Integer.parseInt(hours.getText());
        	boolean includeLab = includesLab.isSelected();
        	String notesString = notes.getText();
        	
        	// TODO check that the course does not already exist
        	ResourceManager.addCourse(departmentString, courseNum, courseName, unitsInt, hoursInt, 
        			includeLab ? 1 : 0, (notesString.equals("")) ? "null" : notesString);
        	
        	Stage stage = (Stage)confirm.getScene().getWindow();
        	stage.close();
        }
	}
}
