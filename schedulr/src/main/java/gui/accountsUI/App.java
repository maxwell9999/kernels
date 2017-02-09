package gui.accountsUI;

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
		System.out.println(getClass());
		System.out.println(getClass().getResource("LoginView.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
