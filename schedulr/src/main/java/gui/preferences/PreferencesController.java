package gui.preferences;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PreferencesController {
	
	@FXML
    private void classPreferenceAction(ActionEvent event) throws IOException
    {
		Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		ClassPreferencesView controller = new ClassPreferencesView();
		controller.start(stage);
    }
	
	@FXML
    private void timePreferenceAction(ActionEvent event) throws IOException {
		// TODO(Sarah): Implement this
    }

}
