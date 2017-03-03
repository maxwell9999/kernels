package gui.scheduling;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main( String[] args )
    {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ScheduleMenuView.fxml"));
        Scene scene = new Scene(root, 600, 500);
		/*Parent root = FXMLLoader.load(getClass().getResource("resources.fxml"));
        Scene scene = new Scene(root, 609, 515);*/

        primaryStage.setScene(scene);
        primaryStage.show();
	}
}