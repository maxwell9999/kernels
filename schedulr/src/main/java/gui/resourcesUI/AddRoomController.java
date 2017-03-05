package gui.resourcesUI;

/**
 * UI for adding a room.
 * @author sarahpadlipsky
 * @version February 14, 2017
 */
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

import core.database.DatabaseCommunicator;
import core.resources.ResourceManager;
import core.resources.Room;

public class AddRoomController {
	
	private static final String SMARTROOM = "Smartroom"; 
	private static final String LECTURE = "Lecture"; 
	private static final String LAB = "Lab"; 
	
	@FXML private Button confirm;
	@FXML private ChoiceBox roomType;
	@FXML private TextField buildingNumber;
	@FXML private TextField roomNumber;
	@FXML private TextField capacity;
	@FXML private TextArea notes;
	@FXML private Label roomError;
	
	private ResourceController controller;
	
	public void initialize() {
		roomType.setItems(FXCollections.observableArrayList(SMARTROOM, LECTURE, LAB));
	}
	
	/**
     * onAction button for adding new room.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == confirm) {
        	int buildingInt = 0;
        	int roomInt = 0;
        	try {
	        	buildingInt = Integer.parseInt(buildingNumber.getText());
	        	roomInt = Integer.parseInt(roomNumber.getText());
	        	int capacityInt = Integer.parseInt(capacity.getText());
	        	String roomTypeString = roomType.getValue().toString();
	        	String notesString = notes.getText();
	        	
	        	Room room = new Room(buildingInt, roomInt, capacityInt, roomTypeString,
	        			notesString);
	        	
	        	if (DatabaseCommunicator.resourceExists(room)) {
	        		roomError.setText("Room already exists.");
	        	}
	        	else {
	        		roomError.setText("");
		        	ResourceManager.addRoom(buildingInt, roomInt, capacityInt, roomTypeString,
		        			notesString);
		        	controller.populateRooms();
		        	Stage stage = (Stage)confirm.getScene().getWindow();
		        	stage.close();
	        	}
        	} catch (Exception e) {
        		roomError.setText("All fields are required.");
        	}
        }
	}
	
	/**
	 * Sets resource controller.
	 * @param resourceController access to ResourceController
	 */
	public void setResourceController(ResourceController controller) {
		this.controller = controller;
	}
}
