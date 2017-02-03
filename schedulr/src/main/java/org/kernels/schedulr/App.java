package org.kernels.schedulr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main( String[] args )
    {
//    	// To launch ClassPreferencesView.
//        Application.launch(ClassPreferencesView.class, args);
        
       // To launch Login Page.
    	launch(args);
    	
    	
   

    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
        Parent parent = (Parent) loader.load(getClass().getResourceAsStream("LoginView.fxml"));
        Scene scene = new Scene(parent, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
