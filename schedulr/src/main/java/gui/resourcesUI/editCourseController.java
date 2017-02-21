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
 * UI for editing a course.
 * @author DavidMcIntyre, sarahpadlipsky
 * @version February 15, 2017
 */
public class editCourseController {
	
	private static final String LECTURE = "Lecture";
	private static final String ACTIVITY = "Activity";
	private static final String SUPERVISORY = "Supervisory";
	
	@FXML private Button save;
	@FXML private ChoiceBox department;
	@FXML private TextField courseNumber;
	@FXML private TextField courseTitle;
	@FXML private TextField units;
	@FXML private TextField hours;
	@FXML private TextArea notes;
	@FXML private CheckBox includesLab;
	@FXML private ChoiceBox type;
	
	private ResourceController controller;
	private Course course;
	// Observable list so it is known when all items have been loaded.
    private ObservableList<String> list = FXCollections.observableArrayList();
	
	public void initialize() {
		department.setItems(FXCollections.observableArrayList("CPE", "SE", "CSC"));
		type.setItems(FXCollections.observableArrayList(LECTURE, ACTIVITY, SUPERVISORY));
		list.addListener(new ListChangeListener<String>() {

    		// Sets fields with information from current user.
            public void onChanged(ListChangeListener.Change<? extends String> change) {
                courseNumber.setText(Integer.toString(course.getNumber()));
                courseTitle.setText(course.getName());
                units.setText(Double.toString(course.getWtu()));
                
                // Sets hours
                if (course.getLectHours() != 0) {
                    hours.setText(Integer.toString(course.getLectHours()));
                    type.getSelectionModel().select(0);
                } else if (course.getLabHours() != 0) {
                    hours.setText(Integer.toString(course.getLabHours()));
                    type.getSelectionModel().select(1);
                } else if (course.getActHours() != 0) {
                    hours.setText(Integer.toString(course.getActHours()));
                    type.getSelectionModel().select(2);
                }
                
                notes.setText(course.getNotes());
                String dept = course.getDepartment();
                if(dept.equals("CPE")){
                    department.getSelectionModel().select(0);
                } else if(dept.equals("SE")) {
                    department.getSelectionModel().select(1);

                } else if(dept.equals("CSC")) {
                    department.getSelectionModel().select(2);
                }  
                
                department.setDisable(false);
                courseNumber.setEditable(false);;
            }
        });
	}
	
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == save) {
        	String departmentString = department.getValue().toString();
        	int courseNum = Integer.parseInt(courseNumber.getText());
        	String courseName = courseTitle.getText();
        	double unitsInt = Double.parseDouble(units.getText());
        	int hoursInt = Integer.parseInt(hours.getText());
        	String typeString = type.getValue().toString(); 
        	int labHours = 0;
        	int lectHours = 0;
        	int actHours = 0;
        	String notesString = notes.getText();
        	
        	course.setDepartment(departmentString);
        	course.setNumber(courseNum);
        	course.setName(courseName);
        	course.setWtu(unitsInt);
        	        	
        	if(includesLab.isSelected()) {
        		labHours = hoursInt;
        	}
        	
        	if (typeString.equals(LECTURE)) {
        		lectHours = hoursInt;
        	} else if (typeString.equals(ACTIVITY)) {
        		actHours = hoursInt;
        	}
        	
        	course.setLabHours(labHours);
        	course.setLectHours(lectHours);
        	course.setActHours(actHours);
        	course.updateCourse();
        	controller.populateCourses();
        	Stage stage = (Stage)save.getScene().getWindow();
        	stage.close();
        }
	}
	
	/**
     * Sets the current  Course.
     * @param controller user to set the current course.
     */
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
    /**
     * Access to ResourceController.
     * @param controller to set ResourceController
     */
    public void setController(ResourceController controller) {
    	this.controller = controller;
    }
}
