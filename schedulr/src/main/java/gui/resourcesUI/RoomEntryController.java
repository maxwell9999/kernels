package gui.resourcesUI;

import java.io.IOException;

import core.resources.Course;
import core.resources.ResourceManager;
import core.resources.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * UI for viewing single entry for rooms and courses.
 * @author DavidMcIntyre, sarahpadlipsky
 * @version February 14, 2017
 */
public class RoomEntryController {

	private ResourceController resourceController;
	private Room room;
	
	/**
     * onAction button for deleting new course.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
	@FXML
    private void editAction(ActionEvent event) {	
		Stage stage = new Stage();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editroom.fxml"));     

    	Parent root = null;
		try {
			root = (Parent)fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}          
		// Sets values so the ResourceEntryController knows which course it contains.
		editRoomController roomController = fxmlLoader.<editRoomController>getController();
		roomController.setRoom(room);
		roomController.setList("ready");
		roomController.setController(resourceController);
    	Scene scene = new Scene(root); 

    	stage.setScene(scene);    

    	stage.show();   
	}
	
	/**
     * onAction button for deleting new course.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
	@FXML
    private void deleteAction(ActionEvent event) {
		ResourceManager.removeRoom(room.getBuilding(), room.getNumber());
		resourceController.populateRooms();
	}
	
	/**
     * Sets current room
     * @param room sets current room
     */
	public void setRoom(Room room) {
		this.room = room;
	}
	
	/**
     * Access to ResourceController.
     * @param TimePreferencesViewer to set ResourceController
     */
	public void setResourceController(ResourceController resourceController) {
		this.resourceController = resourceController;
	}

}
