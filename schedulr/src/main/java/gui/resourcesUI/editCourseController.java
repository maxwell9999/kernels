package gui.resourcesUI;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import core.database.DatabaseCommunicator;
import core.resources.Course;
import core.resources.ResourceManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

/**
 * UI for editting a course.
 * @author DavidMcIntyre, sarahpadlipsky
 * @version February 15, 2017
 */
public class editCourseController {
	@FXML private Button save;
	@FXML private ChoiceBox department;
	@FXML private TextField courseNumber;
	@FXML private TextField courseTitle;
	@FXML private TextField units;
	@FXML private TextField hours;
	@FXML private TextArea notes;
	@FXML private CheckBox includesLab;
	
	private Course course;
	// Observable list so it is known when all items have been loaded.
    private ObservableList<String> list = FXCollections.observableArrayList();
	
	public void initialize() {
		department.setItems(FXCollections.observableArrayList("CPE", "SE", "CSC"));
		list.addListener(new ListChangeListener<String>() {

    		// Sets fields with information from current user.
            public void onChanged(ListChangeListener.Change<? extends String> change) {
                courseNumber.setText(Integer.toString(course.getNumber()));
                courseTitle.setText(course.getName());
                units.setText(Double.toString(course.getWtu()));
                hours.setText(Integer.toString(course.getLectHours()));
                notes.setText(course.getNotes());
                
                
            }
        });
	}
	
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == save) {
        	String departmentString = department.getValue().toString();
        	int courseNum = Integer.parseInt(courseNumber.getText());
        	String courseName = courseTitle.getText();
        	int unitsInt = Integer.parseInt(units.getText());
        	int hoursInt = Integer.parseInt(hours.getText());
        	int labHours = 0;
        	String notesString = notes.getText();
        	
        	//TODO(Sarah): Add courtney's edit function here.
        }
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	/**
     * Used so the info will load.
     * @param controller user to set the currentUser.
     */
    public void setList(String string) {
		list.add(string);
	}
}
