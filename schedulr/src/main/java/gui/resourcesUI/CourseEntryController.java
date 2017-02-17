package gui.resourcesUI;

import java.io.IOException;

import core.resources.Course;
import core.resources.ResourceManager;
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
public class CourseEntryController {

	private ResourceController resourceController;
	private Course course;
	
	/**
     * onAction button for deleting new course.
     * @param event Necessary field for onAction events.
     * @throws IOException 
     */
	@FXML
    private void editAction(ActionEvent event) {	
		Stage stage = new Stage();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editcourse.fxml"));     

    	Parent root = null;
		try {
			root = (Parent)fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}          
		// Sets values so the ResourceEntryController knows which course it contains.
		editCourseController courseController = fxmlLoader.<editCourseController>getController();
		courseController.setCourse(course);
		courseController.setList("ready");
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
		ResourceManager.removeCourse(course.getDepartment(), course.getNumber());
		resourceController.populateCourses();
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public void setResourceController(ResourceController resourceController) {
		this.resourceController = resourceController;
	}

}
