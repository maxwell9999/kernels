package gui.resourcesUI;

/**
 * UI for editing a room.
 * @author DavidMcIntyre
 * @version February 14, 2017
 */
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.swing.SingleSelectionModel;

import core.database.DatabaseCommunicator;
import core.resources.Room;
import core.resources.ResourceManager;
import core.resources.Room;

public class editRoomController {
	
	private static final String SMARTROOM = "Smartroom"; 
	private static final String LECTURE = "Lecture"; 
	private static final String LAB = "Lab"; 
	
	@FXML private Button confirm;
	@FXML private ChoiceBox roomType;
	@FXML private TextField buildingNumber;
	@FXML private TextField roomNumber;
	@FXML private TextField capacity;
	@FXML private TextArea notes;
	@FXML private Label errorLabel;
	
	// Current room.
	private Room room;
	// Observable list so it is known when all items have been loaded.
    private ObservableList<String> list = FXCollections.observableArrayList();
    // Access to ResourceController.
    private ResourceController controller;
	
    // Called when controller is first loaded.
	public void initialize() {
		roomType.setItems(FXCollections.observableArrayList(SMARTROOM, LECTURE, LAB));
		// Sets up listener to populate fields with current room information.
		list.addListener(new ListChangeListener<String>() {

    		// Sets fields with information from current user.
            public void onChanged(ListChangeListener.Change<? extends String> change) {
                buildingNumber.setText(Integer.toString(room.getBuilding()));
                roomNumber.setText(Integer.toString(room.getNumber()));
                capacity.setText(Integer.toString(room.getCapacity()));
                notes.setText(room.getNotes());
                String type = room.getType();
                if (type.equals(SMARTROOM)) {
                	roomType.getSelectionModel().select(0);
                }else if (type.equals(LECTURE)) {
                	roomType.getSelectionModel().select(1);
                } else if(type.equals(LAB)) {
                	roomType.getSelectionModel().select(2);
                }
                buildingNumber.setEditable(false);
                roomNumber.setEditable(false);;
            }
        }); 
	}
	
	 /**
     * onAction function for saving a room.
     * @param event Necessary field for onAction events.
     */
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == confirm) {
        	try {
	        	int buildingInt = Integer.parseInt(buildingNumber.getText());
	        	int roomInt = Integer.parseInt(roomNumber.getText());
	        	int capacityInt = Integer.parseInt(capacity.getText());
	        	String roomTypeString = roomType.getValue().toString();
	        	String notesString = notes.getText();
	        	
	        	Room room = new Room(buildingInt, roomInt, capacityInt, roomTypeString, notesString);
	        	
	        	if (DatabaseCommunicator.resourceExists(room)) {
	        		
	        		room.setBuilding(buildingInt);
	        		room.setNumber(roomInt);
	        		room.setCapacity(capacityInt);
	        		room.setType(roomTypeString);
	        		room.setNotes(notesString);
	        		room.updateRoom();
		        	controller.populateRooms();
	        		Stage stage = (Stage)confirm.getScene().getWindow();
		        	stage.close();
	        	}
        	} catch (Exception e) {
	        	errorLabel.setText("*All fields required.");
	        }
        }
	}
	
	/**
     * Used so the info will load.
     * @param controller user to set the currentUser.
     */
    public void setList(String string) {
		list.add(string);
	}
    
    /**
     * Sets current room.
     * @param controller user to set the current room.
     */
    public void setRoom(Room room) {
    	this.room = room;
    }
    
    /**
     * Gives access to ResourceController.
     * @param controller access to ResourceController.
     */
    public void setController(ResourceController controller) {
    	this.controller = controller;
    }
}
