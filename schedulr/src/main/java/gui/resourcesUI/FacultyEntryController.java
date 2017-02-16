package gui.resourcesUI;

import java.io.IOException;

import core.accounts.AccountManager;
import core.accounts.User;
import core.resources.Course;
import core.resources.ResourceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * UI for viewing single entry for faculty.
 * @author DavidMcIntyre, sarahpadlipsky
 * @version February 14, 2017
 */
public class FacultyEntryController {

	private ResourceController resourceController;
	private User user;
	
	/**
     * onAction button for deleting new course.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
	@FXML
    private void editAction(ActionEvent event) {	
		Stage stage = new Stage();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditAccountView.fxml"));     

    	Parent root = null;
		try {
			root = (Parent)fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}          
		
		EditAccountController editAccountController = fxmlLoader.<EditAccountController>getController();
		editAccountController.setCurrentUser(user);
		editAccountController.setResourceController(resourceController);
    	editAccountController.setList("ready");
    	
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
		AccountManager.removeUser(user.getLogin());
		resourceController.populateFaculty();
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setResourceController(ResourceController resourceController) {
		this.resourceController = resourceController;
	}

}
