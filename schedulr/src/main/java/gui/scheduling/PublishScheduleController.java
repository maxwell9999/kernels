package gui.scheduling;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.database.DatabaseCommunicator;
import core.resources.Schedule;
import gui.accountsUI.LoginViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PublishScheduleController {
	private static final Logger log = LoggerFactory.getLogger(LoginViewController.class);

	@FXML Button publishButton;
	@FXML RadioButton preRegistration;
	@FXML RadioButton postRegistration; 
	@FXML Text errorText; 
	
	final ToggleGroup group = new ToggleGroup();

	
	@FXML
	private void initialize() {
		preRegistration.setToggleGroup(group);
    	postRegistration.setToggleGroup(group);
	}
	
	@FXML
	private void handlePublishButton(ActionEvent action) throws IOException {
		// Get the current schedule being worked on
		Schedule schedule = MainWindow.getController().getSchedule(); 

		if (schedule == null) {
			errorText.setText("Schedule has not been set.");
			errorText.setTextAlignment(TextAlignment.CENTER);
		}

		else {
			try {
				if (preRegistration.isSelected()) {
					if (!DatabaseCommunicator.scheduleExists("PREREG", schedule.getYear(), schedule.getTerm())) {
						DatabaseCommunicator.createNewScheduleTable("PREREG", schedule.getYear(), schedule.getTerm());
					}
					DatabaseCommunicator.saveSchedule("PREREG", schedule.getYear(), schedule.getTerm()); 
					DatabaseCommunicator.saveSchedule("DRAFT", schedule.getYear(), schedule.getTerm()); 
				}
				else if (postRegistration.isSelected()) {
					if (!DatabaseCommunicator.scheduleExists("POSTREG", schedule.getYear(), schedule.getTerm())) {
						DatabaseCommunicator.createNewScheduleTable("POSTREG", schedule.getYear(), schedule.getTerm());
					}
					DatabaseCommunicator.saveSchedule("POSTREG", schedule.getYear(), schedule.getTerm()); 
					DatabaseCommunicator.saveSchedule("DRAFT", schedule.getYear(), schedule.getTerm()); 
				}
			}

			catch (SQLException e) {
				Stage stage = new Stage();
				Pane myPane = null;
				FXMLLoader loader = null;
				loader = new FXMLLoader(this.getClass().getResource("SchedulePublishingError.fxml"));
				myPane = (Pane) loader.load();
				Scene scene = new Scene(myPane);
				stage.setScene(scene);
				stage.show();
				e.printStackTrace();
			}
		
			Stage currentStage = (Stage) publishButton.getScene().getWindow(); 
			currentStage.close(); 
		}
	}
	
}
