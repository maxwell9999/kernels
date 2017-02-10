package gui.resourcesUI;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class editRoomController {
	@FXML private Button confirm;
	@FXML private ChoiceBox roomType;
	@FXML private TextField buildingNumber;
	@FXML private TextField roomNumber;
	@FXML private TextField capacity;
	@FXML private TextArea equipment;
	@FXML private TextArea notes;
	
	public void initialize() {
		roomType.setItems(FXCollections.observableArrayList("Smartroom", "Lecture", "Lab"));
	}
	
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == confirm) {
        	System.out.println("Building Number: " + buildingNumber.getText());
        	System.out.println("Room Number: " + roomNumber.getText());
        	System.out.println("Capacity: " + capacity.getText());
        	System.out.println("Room Type: " + roomType.getValue().toString());
        	System.out.println("Equipment: " + equipment.getText());
        	System.out.println("Notes: " + notes.getText());
        	Stage stage = (Stage)confirm.getScene().getWindow();
        	stage.close();
        }
	}
}
