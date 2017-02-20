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
import javafx.scene.control.TextArea;

import javax.swing.SingleSelectionModel;

import core.database.DatabaseCommunicator;
import core.resources.Room;
import core.resources.ResourceManager;

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
	
	private Room room;
	// Observable list so it is known when all items have been loaded.
    private ObservableList<String> list = FXCollections.observableArrayList();
	
	public void initialize() {
		roomType.setItems(FXCollections.observableArrayList(SMARTROOM, LECTURE, LAB));
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
            }
        }); 
	}
	
	@FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == confirm) {
        	int buildingInt = Integer.parseInt(buildingNumber.getText());
        	int roomInt = Integer.parseInt(roomNumber.getText());
        	int capacityInt = Integer.parseInt(capacity.getText());
        	String roomTypeString = roomType.getValue().toString();
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
	
	/**
     * Used so the info will load.
     * @param controller user to set the currentUser.
     */
    public void setList(String string) {
		list.add(string);
	}
    
    public void setRoom(Room room) {
    	this.room = room;
    }
}
