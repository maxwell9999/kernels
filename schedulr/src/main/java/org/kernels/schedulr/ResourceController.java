package org.kernels.schedulr;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
}
