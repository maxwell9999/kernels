package org.kernels.schedulr;

import javafx.scene.control.TextField;
import java.io.IOException;

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
	
	@FXML
	public void populateCourses() {
		courseContainer.clear();
		for(int i = 0; i < courseList.size(); i++) {
			Pane newPane = (Pane) FXMLLoader.load(getClass().getResource("resource.fxml"));
            courseContainer.getChildren().add(newPane);
		}
	}
	
	@FXML
	public void populateRooms() {
		roomContainer.clear();
		for(int i = 0; i < roomList.size(); i++) {
			Pane newPane = (Pane) FXMLLoader.load(getClass().getResource("resource.fxml"));
            roomContainer.getChildren().add(newPane);
            
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
