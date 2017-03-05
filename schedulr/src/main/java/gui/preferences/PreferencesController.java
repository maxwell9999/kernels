package gui.preferences;

import java.io.IOException;

import core.accounts.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PreferencesController {
	
	User user;
	@FXML
    private void classPreferenceAction(ActionEvent event) throws IOException
    {
		// TODO(Sarah): Uncomment once user is being passed around
//		if (user.getRole() == User.FACULTY_MEMBER) {
//			Stage stage = new Stage();
//			Pane myPane = null;
//			FXMLLoader loader = null;
//			ClassPreferencesView controller = new ClassPreferencesView();
//			controller.start(stage);
//		} else if (user.getRole() == User.SCHEDULER) {
//			// TODO(Sarah): Implement this
//		}
		
		Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		ClassPreferencesViewer controller = new ClassPreferencesViewer();
		controller.start(stage);
    }
	
	@FXML
    private void timePreferenceAction(ActionEvent event) throws IOException {
		// TODO(Sarah): Implement this
		if (user.getRole() == User.FACULTY_MEMBER) {
//			Stage stage = new Stage();
//			Pane myPane = null;
//			FXMLLoader loader = null;
//			ClassPreferencesView controller = new ClassPreferencesView();
//			controller.start(stage);
		} else if (user.getRole() == User.SCHEDULER) {
			// TODO(Sarah): Implement this
		}
    }

	public void setCurrentUser(User user) {
		this.user = user;
	}
	 
}
