package org.kernels.schedulr;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

	
	public void initialize() {
		populateCourses();
		populateRooms();
	}
	
	@FXML
	public void populateCourses() {
		//Back-end connection to populate courseList
		ArrayList<String> courseList = new ArrayList<String>();
		courseList.add("CPE 309 - Software Engineering II");
		courseList.add("CPE 453 - Operating Systems");
		courseList.add("HIST 322 - Modern America");
		
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
            Label temp = (Label) newPane.lookup("#resourceTitle");
            temp.setText(courseList.get(i));
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
            Label temp = (Label) newPane.lookup("#resourceTitle");
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
                newScene = new Scene(loader.load());
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
                newScene = new Scene(loader.load());
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
}
