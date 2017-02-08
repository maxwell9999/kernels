package org.kernels.schedulr;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Application{

    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Schedulr");

        MainViewController mainView = new MainViewController();


        primaryStage.setScene(createScene(loadMainView("MainView.fxml")));

        primaryStage.show();
    }

    private Pane loadMainView(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(path));

        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
      return scene;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}

