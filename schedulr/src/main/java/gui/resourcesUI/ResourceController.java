package gui.resourcesUI;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.accounts.AccountManager;
import gui.accountsUI.FacultyDirectoryController.FacultyMember;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ResourceController {
	@FXML private Button addNewCourse;
	@FXML private Button addNewRoom;
	
	@FXML private Button confirm;
	@FXML private ChoiceBox department;
	@FXML private TextField courseNum;
	@FXML private TextField courseTitle;
	@FXML private TextField units;
	@FXML private TextField hours;
	@FXML private TextArea notes;
	@FXML private CheckBox includesLab;
	@FXML private VBox courseContainer;
	@FXML private VBox roomContainer;
	@FXML private Label test;
	
	// List of courses in database.
	ArrayList<Course> courseList = new ArrayList<Course>();


	
	public void initialize() {
		populateCourses();
		populateRooms();
	}
	
	@FXML
	public void populateCourses() {
		//Back-end connection to populate courseList
		
		courseList.add(new Course("CPE 308", "Software Engineering I"));
		courseList.add(new Course("CPE 309", "Software Engineering II"));
		
		courseContainer.getChildren().clear();
		for(int i = 0; i < courseList.size(); i++) {
			Pane newPane = null;
			try {
				newPane = (Pane) FXMLLoader.load(getClass().getResource("resourceEntry.fxml"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
            courseContainer.getChildren().add(newPane);
            Label temp = (Label) newPane.lookup("#departmentTitle");
            Label temp2 = (Label) newPane.lookup("#courseTitle");

            temp.setText(courseList.get(i).courseTitle.get());
            temp2.setText((String)courseList.get(i).courseName.get());

		}
	}
	
	@FXML
	public void populateRooms() {
		//Back-end connection to populate roomList
		ArrayList<String> roomList = new ArrayList<String>();
		roomList.add("Building 14 - Room 255");
		roomList.add("Building 10 - Room 227");
		roomList.add("Building 180 - Room 342");
		
		roomContainer.getChildren().clear();
		for(int i = 0; i < roomList.size(); i++) {
			Pane newPane = null;
			try {
				newPane = (Pane) FXMLLoader.load(getClass().getResource("resourceEntry.fxml"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
            roomContainer.getChildren().add(newPane);
            Label temp = (Label) newPane.lookup("#departmentTitle");
            temp.setText(roomList.get(i));
		}
	}
	
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == addNewCourse) {
        	// Edit courses popup
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("editcourse.fxml"));
            Scene newScene;
            try {
                newScene = new Scene((Parent) loader.load());
            } catch (IOException ex) {
                System.out.println(ex);
                ex.printStackTrace();
                return;
            }

            Stage primaryStage = (Stage) addNewCourse.getScene().getWindow();
            Stage inputStage = new Stage();
            inputStage.initOwner(primaryStage);
            inputStage.setScene(newScene);
            inputStage.showAndWait();
        }
        else if (event.getSource() == addNewRoom) {
        	// Edit room popup
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("editroom.fxml"));
            Scene newScene;
            try {
                newScene = new Scene((Parent)loader.load());
            } catch (IOException ex) {
            	System.out.println(ex);
            	ex.printStackTrace();
                return;
            }

            Stage primaryStage = (Stage) addNewCourse.getScene().getWindow();
            Stage inputStage = new Stage();
            inputStage.initOwner(primaryStage);
            inputStage.setScene(newScene);
            inputStage.showAndWait();
        }
    }
	
	/**
     * Course is a class to correctly populate the ListView.
     */
	public static class Course {
	    private final SimpleStringProperty courseTitle;
	    private final SimpleStringProperty courseName;
	 
	    private Course(String courseTitle, String courseName) {
	        this.courseTitle = new SimpleStringProperty(courseTitle);
	        this.courseName = new SimpleStringProperty(courseName);
	    }
	 
	    public String getCourseTitle() {
	        return this.courseTitle.get();
	    }
	    public void setCourseTitle(String courseTitle) {
	    	this.courseTitle.set(courseTitle);
	    }
	        
	    public String getCourseName() {
	        return this.courseName.get();
	    }
	    public void setCourseName(String courseName) {
	    	this.courseName.set(courseName);
	    }    
	}	
	
	/**
     * Course is a class to correctly populate the ListView.
     */
	public static class Room {
	    private final SimpleStringProperty courseTitle;
	    private final SimpleStringProperty courseName;
	 
	    private Course(String courseTitle, String courseName) {
	        this.courseTitle = new SimpleStringProperty(courseTitle);
	        this.courseName = new SimpleStringProperty(courseName);
	    }
	 
	    public String getCourseTitle() {
	        return this.courseTitle.get();
	    }
	    public void setCourseTitle(String courseTitle) {
	    	this.courseTitle.set(courseTitle);
	    }
	        
	    public String getCourseName() {
	        return this.courseName.get();
	    }
	    public void setCourseName(String courseName) {
	    	this.courseName.set(courseName);
	    }    
	}	
}
