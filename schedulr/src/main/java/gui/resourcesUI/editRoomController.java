package gui.resourcesUI;

/**
 * UI for editting a room.
 * @author DavidMcIntyre
 * @version February 14, 2017
 */
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import core.database.DatabaseCommunicator;
import core.resources.ResourceManager;

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
        	int buildingInt = Integer.parseInt(buildingNumber.getText());
        	int roomInt = Integer.parseInt(roomNumber.getText());
        	int capacityInt = Integer.parseInt(capacity.getText());
        	String roomTypeString = roomType.getValue().toString();
        	String equipmentString = equipment.getText();
        	String notesString = notes.getText();
        	
        	if (DatabaseCommunicator.resourceExists("rooms", "building=" + buildingInt + " AND room=" + roomInt)) {
        		//TODO make error message
        	}
        	else {
	        	ResourceManager.addRoom(buildingInt, roomInt, capacityInt, roomTypeString,
	        			notesString);
	        	
	        	Stage stage = (Stage)confirm.getScene().getWindow();
	        	stage.close();
        	}
        }
	}
}
