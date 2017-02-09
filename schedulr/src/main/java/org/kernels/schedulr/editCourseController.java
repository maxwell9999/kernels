package org.kernels.schedulr;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class editCourseController {
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
        if (event.getSource() == confirm) {
        	System.out.println("Department: " + department.getValue().toString());
        	System.out.println("Course Number: " + courseNum.getText());
        	System.out.println("Course Title: " + courseTitle.getText());
        	System.out.println("Units: " + units.getText());
        	System.out.println("Hours: " + hours.getText());
        	System.out.println("Includes Lab?: " + includesLab.isSelected());
        	System.out.println("Notes: " + notes.getText());
        }
	}
}
