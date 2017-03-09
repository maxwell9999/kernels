package gui.preferences;

import java.util.*;

import core.accounts.AccountManager;
import core.database.DatabaseCommunicator;
import core.preferences.Preferences;
import gui.scheduling.MainViewController;
import javafx.application.*;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.*;

/**
 * UI for allowing Faculty Member's to pick their preferences.
 * @author sarahpadlipsky
 * @version February 1, 2017
 */
public class TimePreferencesView extends Application{

  // TableView aka Spreadsheet for the view.
  private TableView<Time> tableView;
  // List of all the courses.
  public ObservableList<Time> CourseData;	
  // List of hardcoded times to populate table.
  private List<Time> items;
  // MWF vs. TR choice box.
  ChoiceBox choiceBox = new ChoiceBox();
  
  @Override
  public void start(Stage primaryStage) {

    this.tableView = new TableView<Time>();

    tableView.setSelectionModel(null);

    final TableColumn<Time, String> time = new TableColumn<Time, String>("Time");
    final TableColumn<Time, Boolean> ableToTeachCol = new TableColumn<Time, Boolean>("Able to Teach");
    final TableColumn<Time, Boolean> wantToTeachCol = new TableColumn<Time, Boolean>("Want to Teach");

    ableToTeachCol.setSortable(false);
    wantToTeachCol.setSortable(false);
    time.setSortable(false);

    // Make columns not resizable.
    time.setResizable(false);      
    ableToTeachCol.setResizable(false);
    wantToTeachCol.setResizable(false);

    // Sets widths of columns
    time.prefWidthProperty().bind(tableView.widthProperty().multiply(0.333));
    ableToTeachCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.333));
    wantToTeachCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.333));
    time.setStyle( "-fx-alignment: CENTER;");

    initializeItemList();

    // Makes data observable.
    this.CourseData = FXCollections.observableArrayList(new Callback<Time, Observable[]>() {

        public Observable[] call(Time param) {
            return new Observable[] {param.ableToTeachProperty()};
        }
    });
    
//
    this.CourseData.addAll(items);

    // Add items to TableView.
    tableView.setItems(this.CourseData);
    tableView.getColumns().addAll(time, ableToTeachCol, wantToTeachCol);

    time.setCellValueFactory(new PropertyValueFactory<Time, String>("Time"));
    PropertyValueFactory<Time,Boolean> pValue = new PropertyValueFactory<Time, Boolean>("ableToTeach");
    ableToTeachCol.setCellValueFactory(pValue);
    ableToTeachCol.setCellFactory(CheckBoxTableCell.forTableColumn(ableToTeachCol));
    ableToTeachCol.setEditable(true);

    wantToTeachCol.setCellValueFactory(new PropertyValueFactory<Time, Boolean>("wantToTeach"));
    wantToTeachCol.setCellFactory(CheckBoxTableCell.forTableColumn(wantToTeachCol));
    wantToTeachCol.setEditable(true);
    
    tableView.setEditable(true);

    Label label = new Label("Time Preferences");
    label.setFont(new Font("Arial", 20));


    Button button = new Button();
    button.setText("Save");

    // Set listener for Save button.
    button.setOnAction(new EventHandler<ActionEvent>() {

    public void handle(ActionEvent event) {
    	String value = (String) choiceBox.getValue();
	  	String currentUser = MainViewController.getUser().getLogin();
	  	Preferences preference = new Preferences();
  	
  		ArrayList<String> want = new ArrayList<>();
		ArrayList<String> able = new ArrayList<>();
		
		for (int i = 0; i < items.size(); i++) {
			boolean ableBool = items.get(i).isAbleToChecked();
			boolean wantBool = items.get(i).isWantToChecked();
			
			if(ableBool){
				able.add(items.get(i).getTime());
			} else {
				able.add("null");
			}
			
			if(wantBool) {
				want.add(items.get(i).getTime());
			} else {
				want.add("null");
			}
		}
		
		for (int i = 0; i < items.size(); i++) {
			System.out.println(able.get(i));
		}
		
		for (int i = 0; i < items.size(); i++) {
			System.out.println(want.get(i));
		}
		// It WAS MWF - store as such
	  	if (value.equals("MWF")) {
	  		preference.setMWFPreferences(currentUser, able, want);
	  	} else {
	  		preference.setTRPreferences(currentUser, able, want);
	  	}
      }
    });

    choiceBox = new ChoiceBox();
    
	List<String> days = new ArrayList<String>(); 
	days.add("MWF");
	days.add("TR");
	choiceBox.setItems(FXCollections.observableArrayList(days)); 
	choiceBox.getSelectionModel().selectFirst();
    
	choiceBox.setOnAction(new EventHandler<ActionEvent>() {

	    public void handle(ActionEvent event) {
	        saveData();
	    }
	});

    final BorderPane border = new BorderPane();
    border.setTop(label);
    
    final BorderPane addedForView = new BorderPane();
    border.setCenter(addedForView);
    addedForView.setTop(choiceBox);
    addedForView.setCenter(tableView);


    final BorderPane buttonPane = new BorderPane();
    addedForView.setBottom(buttonPane);
    buttonPane.setRight(button);

    Scene scene = new Scene(border, 500, 500);
    primaryStage.setScene(scene);
    primaryStage.show();

  }
  
  public void initializeItemList() {
	  items = new ArrayList<Time>();
	  items.add(new Time("7:00"));
	  items.add(new Time("7:30"));
	  items.add(new Time("8:00"));
	  items.add(new Time("8:30"));
	  items.add(new Time("9:00"));
	  items.add(new Time("9:30"));
	  items.add(new Time("10:00"));
	  items.add(new Time("10:30"));
	  items.add(new Time("11:00"));
	  items.add(new Time("11:30"));
	  items.add(new Time("12:00"));
	  items.add(new Time("12:30"));
	  items.add(new Time("13:00"));
	  items.add(new Time("13:30"));
	  items.add(new Time("14:00"));
	  items.add(new Time("14:30"));
	  items.add(new Time("15:00"));
	  items.add(new Time("15:30"));
	  items.add(new Time("16:00"));
	  items.add(new Time("16:30"));
	  items.add(new Time("17:00"));
	  items.add(new Time("17:30"));
	  items.add(new Time("18:00"));
	  items.add(new Time("18:30"));
	  items.add(new Time("19:00"));
	  items.add(new Time("19:30"));
	  items.add(new Time("20:00"));
	  items.add(new Time("20:30"));
	  items.add(new Time("21:00"));
	  items.add(new Time("21:30"));
  }
  
  public void saveData() {
	  	String value = (String) choiceBox.getValue();
	  	String currentUser = MainViewController.getUser().getLogin();
	  	Preferences preference = new Preferences();
  	
  		ArrayList<String> want = new ArrayList<>();
		ArrayList<String> able = new ArrayList<>();
		
		for (int i = 0; i < items.size(); i++) {
			boolean ableBool = items.get(i).isAbleToChecked();
			boolean wantBool = items.get(i).isWantToChecked();
			
			if(ableBool){
				able.add(items.get(i).getTime());
			} else {
				able.add("null");
			}
			
			if(wantBool) {
				want.add(items.get(i).getTime());
			} else {
				want.add("null");
			}
		}
  	// It WAS MWF - store as such
  	if (value.equals("TR")) {
  		preference.setMWFPreferences(currentUser, able, want);
  	} else {
  		preference.setTRPreferences(currentUser, able, want);
  	}
  }

  public static class Time {
    private StringProperty time;
    private StringProperty timeID;
    private BooleanProperty ableToTeach;
    private BooleanProperty wantToTeach;

    public Time(String time) {
      this.time = new SimpleStringProperty(time);
      this.ableToTeach = new SimpleBooleanProperty(false);
      this.wantToTeach = new SimpleBooleanProperty(false);
    }

    public String getTime() {
      return time.get();
    }

    public String getTimeID() {
      return timeID.get();
    }

    public boolean isAbleToChecked() {
      return ableToTeach.get();
    }

    public boolean isWantToChecked() {
      return wantToTeach.get();
    }

    public void setTime(String time) {
      this.time.set(time);
    }

    public void setAbleToChecked(boolean ableToTeach) {
      this.ableToTeach.set(ableToTeach);
    }

     public void setWantToChecked(boolean ableToTeach) {
      this.wantToTeach.set(ableToTeach);
    }

    public StringProperty TimeProperty() {
      return time;
    }

    public StringProperty TimeIDProperty() {
      return timeID;
    }

    public BooleanProperty ableToTeachProperty() {
      return ableToTeach;
    }

    public BooleanProperty wantToTeachProperty() {
      return wantToTeach;
    }
  }
}