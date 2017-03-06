package gui.scheduling;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import core.accounts.User;
import core.resources.Schedule;
import core.resources.ResourceManager;
import de.ks.fxcontrols.weekview.WeekView;
import de.ks.fxcontrols.weekview.WeekViewAppointment;
import gui.feedback.StudentFeedbackController;
import gui.preferences.PreferencesController;
import gui.resourcesUI.ResourceController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import gui.resourcesUI.*;
import gui.feedback.*;
import gui.preferences.PreferencesController;

public class MainViewController extends VBox {

	//TODO Remove the change weeks buttons (if possible), prevent drag and drop features
    @FXML //  fx:id="addPanelButton"
    private Button addPanelButton; // Value injected by FXMLLoader
    @FXML
    private Button editPanelButton;
    @FXML
    private Button rmPanelButton;
    @FXML
    private Button closeAddPanelButton = new Button();
    @FXML
    private VBox addPane;
    @FXML
    private AnchorPane calendarPane;
    @FXML
    private SplitPane splitPane;

    private boolean open;
    private Schedule schedule; 

    private WeekView<Object> weekView;
    private LocalDate begin, end;
    private LinkedList<WeekViewAppointment<Object>> retval;
    private String titleString;
    
    private static User user;

	public MainViewController(WeekView<Object> weekView, LocalDate begin, LocalDate end, LinkedList<WeekViewAppointment<Object>> retval) {
		this.weekView = weekView;
		this.begin = begin;
		this.end = end;
		this.retval = retval;
	}

    @FXML
    public void initialize() {
        assert addPanelButton != null : "fx:id=\"addPanelButton\" was not injected: check your FXML file 'simple.fxml'.";
        assert addPane != null : "fx:id=\"addPane\" was not injected: check your FXML file 'simple.fxml'.";
        assert calendarPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'simple.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

        open = false;

        addPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Add a Class";
        		handleClassButtonPress(event);
        	}
        });
        editPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Edit a Class";
        		handleClassButtonPress(event);
        	}
        });
        rmPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Remove a Class";
        		handleClassButtonPress(event);
        	}
        });

        closeAddPanelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
	           	if (open) {
		    		//addPanelButton.setText("Add Q Class");
		    		addPane.getChildren().remove(0);
		    		addPane.getChildren().remove(0);
		    		splitPane.setDividerPositions(0.1005567928730512, 0.9905567928730512);
		    		open = false;
	           	}
            }
        });

    }

	public void handleClassButtonPress(ActionEvent event) {
        try {

        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPanel.fxml"));
        	Pane addClassPanel = (Pane) loader.load();
    	    AddPanelController addPanelCtrl = loader.<AddPanelController>getController();
    	    addPanelCtrl.initData(titleString, weekView, begin, end, retval);
    	    addPanelCtrl.setSchedule(schedule);

    	    addPane.getChildren().add(closeAddPanelButton);
    		closeAddPanelButton.setText("Close");
            addPane.getChildren().add(addClassPanel);
            open = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCalendar(WeekView<Object> calendar) {
        GridPane calendarView = (GridPane) calendar;
        calendarPane.setPrefSize(800, 600);
        calendarPane.getChildren().add(calendarView);
    }
    
    public void setSchedule(Schedule schedule) {
    	this.schedule = schedule; 
    }
    
    private void selectSchedule() throws IOException {
    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		ScheduleSelectionController controller = new ScheduleSelectionController();
		loader = new FXMLLoader(controller.getClass().getResource("ScheduleSelectionView.fxml"));
		myPane = (Pane) loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
	private void createMenuItemClicked(ActionEvent event) throws IOException {
    	
    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		AddScheduleController controller = new AddScheduleController();
		loader = new FXMLLoader(controller.getClass().getResource("AddScheduleView.fxml"));
		myPane = (Pane) loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();
		
	}
    
    @FXML
	private void openMenuItemClicked(ActionEvent event) throws IOException {
    	
    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		ScheduleSelectionController controller = new ScheduleSelectionController();
		loader = new FXMLLoader(controller.getClass().getResource("ScheduleSelectionView.fxml"));
		myPane = (Pane) loader.load(); 
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();
		
	}
    
    @FXML
	private void saveMenuItemClicked(ActionEvent event) throws IOException {
    	/*
    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		ResourceController controller = new ResourceController();
		loader = new FXMLLoader(controller.getClass().getResource("resources.fxml"));
		myPane = (Pane) loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();
		*/
	}

    @FXML
	private void resourceMenuItemClicked(ActionEvent event) throws IOException {
    	
    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		ResourceController controller = new ResourceController();
		loader = new FXMLLoader(controller.getClass().getResource("resources.fxml"));
		myPane = (Pane) loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();
		
	}
    
    @FXML
	private void preferenceMenuItemClicked(ActionEvent event) throws IOException {
    	
    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		PreferencesController controller = new PreferencesController();
		loader = new FXMLLoader(controller.getClass().getResource("PreferencesChoiceView.fxml"));
		controller.setCurrentUser(user);
		myPane = (Pane) loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();	
	}
    
    @FXML
	private void importMenuItemClicked(ActionEvent event) throws IOException {
    	
    	FileChooser chooser = new FileChooser();
    	chooser.setTitle("Open Resource File");
    	File file = chooser.showOpenDialog(new Stage());
    	if (file != null)
    		ResourceManager.importCourses(file);		
	}
    
    @FXML
	private void feedbackMenuItemClicked(ActionEvent event) throws IOException {
    	
    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		StudentFeedbackController controller = new StudentFeedbackController();
		loader = new FXMLLoader(controller.getClass().getResource("StudentFeedback.fxml"));
		myPane = (Pane) loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();
    }  
       
    @FXML
	private void editAccountMenuItemClicked(ActionEvent event) throws IOException {
    	
    	//TODO(Sarah): Implement this	
	}
    
    @FXML
	private void aboutMenuItemClicked(ActionEvent event) throws IOException {
    	
    	//TODO(Sarah): Implement this	
	}
    
    public void setUser(User user) {
    	this.user = user;
    }
    
    public static User getUser() {
    	return user; 
    }
}