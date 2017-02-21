package gui.resourcesUI;

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
 * UI for viewing resources (courses,rooms, and faculty).
 * @author DavidMcIntyre, sarahpadlipsky
 * @version February 14, 2017
 */
public class ResourceController {
	
	@FXML private Button addNewCourse;
	@FXML private Button addNewRoom;
	@FXML private Button addNewFaculty;

	@FXML private Button confirm;
	@FXML private ChoiceBox department;
	@FXML private Label courseDepartment;
	@FXML private Label courseNumber;
	@FXML private Label courseName;
	@FXML private TextField units;
	@FXML private TextField hours;
	@FXML private TextArea notes;
	@FXML private CheckBox includesLab;
	@FXML private VBox courseContainer;
	@FXML private VBox facultyContainer;
	@FXML private VBox roomContainer;
	@FXML private Label test;
	
	// List of courses in database.
	List<Course> courses = new ArrayList<Course>();
	// List of rooms in database.
	List<Room> rooms = new ArrayList<Room>();
	// List of users in database
	List<User> faculty = new ArrayList<User>();

	/**
	 *  Called when controller is initialized to populate GUI.
	 */
	public void initialize() {
		populateCourses();
		populateRooms();
		populateFaculty();
	}
	
	/**
	 * Updates the course list from the database.
	 */
	@FXML
	public void populateCourses() {
		//Back-end connection to populate courseList
		courses = ResourceManager.getCourseList();
		courseContainer.getChildren().clear();
		for(int i = 0; i < courses.size(); i++) {
			Pane newPane = null;
			
			FXMLLoader loader = null;
			try {
				loader = new FXMLLoader(getClass().getResource("courseEntry.fxml"));
				newPane = (Pane) loader.load();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			// Sets values so the ResourceEntryController knows which course it contains.
			CourseEntryController resourceController = loader.<CourseEntryController>getController();
            resourceController.setCourse(courses.get(i));
            resourceController.setResourceController(this);

            // Populates GUI.
            courseContainer.getChildren().add(newPane);
            Label department = (Label) newPane.lookup("#courseDepartment");
            Label number = (Label) newPane.lookup("#courseNumber");
            Label name = (Label) newPane.lookup("#courseName");
            
            department.setText(courses.get(i).getDepartment());
            number.setText(Integer.toString(courses.get(i).getNumber()));
            name.setText(courses.get(i).getName());
		}
	}
	
	/**
	 * Updates the room list from the database.
	 */
	@FXML
	public void populateRooms() {
		//Back-end connection to populate roomList
		rooms = ResourceManager.getRoomList("");

		roomContainer.getChildren().clear();
		for(int i = 0; i < rooms.size(); i++) {
			Pane newPane = null;
			FXMLLoader loader = null;
			try {
				loader = new FXMLLoader(getClass().getResource("roomEntry.fxml"));
				newPane = (Pane) loader.load();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}

			RoomEntryController roomEntryController = loader.<RoomEntryController>getController();
			roomEntryController.setRoom(rooms.get(i));
			roomEntryController.setResourceController(this);
            roomContainer.getChildren().add(newPane);
            Label building = (Label) newPane.lookup("#buildingNumber");
            building.setText(Integer.toString(rooms.get(i).getBuilding()));
            Label room = (Label) newPane.lookup("#roomNumber");
            room.setText(Integer.toString(rooms.get(i).getNumber()));
            
		}
	}
	
	/**
	 * Updates the faculty list from the database.
	 */
	@FXML
	public void populateFaculty() {
		//Back-end connection to populate courseList
		List<User> userList = AccountManager.getUserList();
		faculty = new ArrayList<User>();
		for (User currentUser : userList) {
			faculty.add(currentUser);
		}
		
		facultyContainer.getChildren().clear();
		for(int i = 0; i < faculty.size(); i++) {
			Pane newPane = null;
			FXMLLoader loader = null;
			try {
				loader = new FXMLLoader(getClass().getResource("facultyEntry.fxml"));
				newPane = (Pane) loader.load();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			// Sets values so the ResourceEntryController knows which course it contains.
			FacultyEntryController resourceController = loader.<FacultyEntryController>getController();
            resourceController.setUser(faculty.get(i));
            resourceController.setResourceController(this);
            
            // Populates GUI.
            facultyContainer.getChildren().add(newPane);
            Label lastName = (Label) newPane.lookup("#lastName");
            Label firstName = (Label) newPane.lookup("#firstName");
            Label login = (Label) newPane.lookup("#login");
            lastName.setText(faculty.get(i).getLastName());
            firstName.setText(faculty.get(i).getFirstName());
            login.setText(faculty.get(i).getLogin());
		}
	}
	
	/**
     * onAction button for adding new course, room, and faculty member.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == addNewCourse) {
        	// Add course popup
        	System.out.println(getClass().getResource("addCourse.fxml"));
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourse.fxml"));
            Scene newScene;
            try {
                newScene = new Scene((Parent) loader.load());
            } catch (IOException ex) {
                System.out.println(ex);
                ex.printStackTrace();
                return;
            }
            AddCourseController addCourseController = loader.<AddCourseController>getController();
            addCourseController.setResourceController(this);
            Stage primaryStage = (Stage) addNewCourse.getScene().getWindow();
            Stage inputStage = new Stage();
            inputStage.initOwner(primaryStage);
            inputStage.setScene(newScene);
            inputStage.showAndWait();
        }
        else if (event.getSource() == addNewRoom) {
        	// Add room popup
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddRoom.fxml"));
            Scene newScene;
            try {
                newScene = new Scene((Parent)loader.load());
            } catch (IOException ex) {
            	System.out.println(ex);
            	ex.printStackTrace();
                return;
            }
            
            AddRoomController addRoomController = loader.<AddRoomController>getController();
            addRoomController.setResourceController(this);
            Stage primaryStage = (Stage) addNewRoom.getScene().getWindow();
            Stage inputStage = new Stage();
            inputStage.initOwner(primaryStage);
            inputStage.setScene(newScene);
            inputStage.showAndWait();
        }
        else if (event.getSource() == addNewFaculty) {
        	// Add Faculty popup
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAccountView.fxml"));
            Scene newScene;
            try {
                newScene = new Scene((Parent)loader.load());
            } catch (IOException ex) {
            	System.out.println(ex);
            	ex.printStackTrace();
                return;
            }
            AddAccountController addAccountController = loader.<AddAccountController>getController();
            addAccountController.setResourceController(this);
            Stage primaryStage = (Stage) addNewFaculty.getScene().getWindow();
            Stage inputStage = new Stage();
            inputStage.initOwner(primaryStage);
            inputStage.setScene(newScene);
            inputStage.showAndWait();
        }
    }	
}
