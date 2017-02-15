package gui.accountsUI;

import java.util.*;
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
public class ClassPreferencesView extends Application{

  // TableView aka Spreadsheet for the view.
  private TableView<Course> tableView;
  // List of all the courses.
  public ObservableList<Course> CourseData;	
  
  @Override
  public void start(Stage primaryStage) {

    this.tableView = new TableView<Course>();

    tableView.setSelectionModel(null);

    final TableColumn<Course, String> Course = new TableColumn<Course, String>("Course");
    final TableColumn<Course, Boolean> ableToTeachCol = new TableColumn<Course, Boolean>("Able to Teach");
    final TableColumn<Course, Boolean> wantToTeachCol = new TableColumn<Course, Boolean>("Want to Teach");

    ableToTeachCol.setSortable(false);
    wantToTeachCol.setSortable(false);
    Course.setSortable(false);

    // Make columns not resizable.
    Course.setResizable(false);      
    ableToTeachCol.setResizable(false);
    wantToTeachCol.setResizable(false);

    // Sets widths of columns
    Course.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
    ableToTeachCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.245));
    wantToTeachCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.245));

    // TODO: Populate from database.
    final List<Course> items=Arrays.asList(
      new Course("CPE 101", "3"), 
      new Course("CPE 102", "4"), 
      new Course("CPE 103", "5"), 
      new Course("CPE 225", "6"), 
      new Course("CPE 300", "7"),
      new Course("CPE 307", "3"), 
      new Course("CPE 308", "4"), 
      new Course("CPE 309", "5"), 
      new Course("CPE 349", "6"), 
      new Course("CPE 357", "7"));

    // Makes data observable.
    this.CourseData = FXCollections.observableArrayList(new Callback<Course, Observable[]>() {

        public Observable[] call(Course param) {
            return new Observable[] {param.ableToTeachProperty()};
        }
    });

    this.CourseData.addAll(items);

    // Add items to TableView.
    tableView.setItems(this.CourseData);
    tableView.getColumns().addAll(Course, ableToTeachCol, wantToTeachCol);

    Course.setCellValueFactory(new PropertyValueFactory<Course, String>("Course"));
    PropertyValueFactory<Course,Boolean> pValue = new PropertyValueFactory<Course, Boolean>("ableToTeach");
    ableToTeachCol.setCellValueFactory(pValue);
    ableToTeachCol.setCellFactory(CheckBoxTableCell.forTableColumn(ableToTeachCol));
    ableToTeachCol.setEditable(true);

    wantToTeachCol.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("wantToTeach"));
    wantToTeachCol.setCellFactory(CheckBoxTableCell.forTableColumn(wantToTeachCol));
    wantToTeachCol.setEditable(true);
    
    tableView.setEditable(true);

    Label label = new Label("Class Preferences");
    label.setFont(new Font("Arial", 20));
    Label noteSection = new Label("Notes:");

    final TextArea textArea = new TextArea();
    textArea.setPrefHeight(100);

    Button button = new Button();
    button.setText("Save");

    // Set listener for Save button.
    // TODO: Store this information in database.
    button.setOnAction(new EventHandler<ActionEvent>() {

    public void handle(ActionEvent event) {
        for (Course course : CourseData) {
          System.out.println(course.getCourse() + "- Able: " + course.isAbleToChecked() + " - Want: " + course.isWantToChecked());
        }

        String textString = textArea.getText();
        System.out.println("Note section says: " + textString);
      }
    });

    final BorderPane border = new BorderPane();
    border.setTop(label);
    border.setCenter(tableView);

    final BorderPane borderBottom = new BorderPane();
    borderBottom.setCenter(textArea);
    borderBottom.setTop(noteSection);

    final BorderPane buttonPane = new BorderPane();
    buttonPane.setRight(button);

    borderBottom.setBottom(buttonPane);
    border.setBottom(borderBottom);

    Scene scene = new Scene(border, 500, 500);
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  // TODO: This will interact with Course made by back-end folks.
  public static class Course {
    private StringProperty Course;
    private StringProperty CourseID;
    private BooleanProperty ableToTeach;
    private BooleanProperty wantToTeach;

    public Course(String Course, String CourseID) {
      this.Course = new SimpleStringProperty(Course);
      this.CourseID = new SimpleStringProperty(CourseID);
      this.ableToTeach = new SimpleBooleanProperty(false);
      this.wantToTeach = new SimpleBooleanProperty(false);
    }

    public String getCourse() {
      return Course.get();
    }

    public String getCourseID() {
      return CourseID.get();
    }

    public boolean isAbleToChecked() {
      return ableToTeach.get();
    }

    public boolean isWantToChecked() {
      return wantToTeach.get();
    }

    public void setCourse(String Course) {
      this.Course.set(Course);
    }

    public void setCourseID(String CourseID) {
      this.CourseID.set(CourseID);
    }

    public void setAbleToChecked(boolean ableToTeach) {
      this.ableToTeach.set(ableToTeach);
    }

     public void setWantToChecked(boolean ableToTeach) {
      this.wantToTeach.set(ableToTeach);
    }

    public StringProperty CourseProperty() {
      return Course;
    }

    public StringProperty CourseIDProperty() {
      return CourseID;
    }

    public BooleanProperty ableToTeachProperty() {
      return ableToTeach;
    }

    public BooleanProperty wantToTeachProperty() {
      return wantToTeach;
    }
  }
}