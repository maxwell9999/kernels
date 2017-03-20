package gui.scheduling;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.accounts.User;
import core.database.DatabaseCommunicator;
import core.resources.ResourceManager;
import core.resources.Schedule;
import core.resources.Section;
import de.ks.fxcontrols.weekview.WeekView;
import de.ks.fxcontrols.weekview.WeekViewAppointment;
import gui.accountsUI.LoginViewController;
import gui.feedback.FeedbackViewer;
import gui.feedback.StudentFeedbackController;
import gui.preferences.PreferencesController;
import gui.resourcesUI.ResourceController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainViewController extends VBox {

	private static final Logger log = LoggerFactory.getLogger(MainViewController.class);

    @FXML
    private Button addPanelButton;
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
    @FXML
    private ScrollPane rightScrollPane;

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
        editRmButtonsEnabled(false);

        addPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Add a Class";
        		editRmButtonsEnabled(false);
        		handleClassButtonPress(event);
        	}
        });
        editPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Edit a Class";

        		WeekViewAppointment<Object> selected = getFocusedNode();

        		if (selected != null) {
	        		addPanelButton.setDisable(true);
	        		rmPanelButton.setDisable(true);
	        		handleClassButtonPress(event);
        		} else {
        			System.out.println("Please select a class to edit.");
        		}
        	}
        });
        rmPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		WeekViewAppointment<Object> selected = getFocusedNode();
        		if (selected != null) {
        			retval.remove(selected);
        			weekView.recreateEntries(retval);
        		}
        		editRmButtonsEnabled(false);
        	}
        });

        closeAddPanelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
	           	if (open) {
		    		addPane.getChildren().remove(0);
		    		addPane.getChildren().remove(0);
		    		splitPane.setDividerPositions(0.1505567928730512, 0.9905567928730512);
		    		open = false;
	        		addPanelButton.setDisable(open);
	           	}
            }
        });

//        MouseEvent e;
//        Object content = e.getDragboard().getContent(WeekView.getDataFormat());
//        System.out.println(content);
    }


    public WeekViewAppointment<Object> getFocusedNode() {
    	for (WeekViewAppointment<Object> appointment : retval) {
    		if (appointment.getFocused()) {
    			System.out.println("focused " + appointment.toString());
    			return appointment;
    		}
    	}
    	return null;
    }

	public void handleClassButtonPress(ActionEvent event) {
        if (open == false) {
        	try {

	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPanel.fxml"));
	        	Pane addClassPanel = (Pane) loader.load();
	    	    AddPanelController addPanelCtrl = loader.<AddPanelController>getController();
	    	    addPanelCtrl.initData(titleString, weekView, begin, end, retval, closeAddPanelButton);
	    	    addPanelCtrl.setSchedule(schedule);

	    	    addPane.getChildren().add(closeAddPanelButton);
	    		closeAddPanelButton.setText("Close");
	            addPane.getChildren().add(addClassPanel);
	            splitPane.setDividerPositions(0.1505567928730512, 0.7305567928730512);
	            open = true;

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
    }

	public void editRmButtonsEnabled(Boolean status){
        editPanelButton.setDisable(!status);
        rmPanelButton.setDisable(!status);
	}

    public void addCalendar(WeekView<Object> calendar) {
        GridPane calendarView = (GridPane) calendar;
        calendarPane.setPrefSize(800, 600);
        calendarPane.getChildren().add(calendarView);
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

    @FXML private void publishMenuItemClicked(ActionEvent event) throws IOException {

    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		PublishScheduleController controller = new PublishScheduleController();
		loader = new FXMLLoader(controller.getClass().getResource("SchedulePublishView.fxml"));
		myPane = (Pane) loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
	private void saveMenuItemClicked(ActionEvent event) throws IOException {
    	try {
    		Stage stage = new Stage();
    		Pane myPane = null;
    		FXMLLoader loader = null;

			if (!DatabaseCommunicator.saveSchedule("DRAFT", schedule.getYear(), schedule.getTerm())) {
				// error
				loader = new FXMLLoader(this.getClass().getResource("ScheduleSavingError.fxml"));
			}
			else {
				// confirmation
				loader = new FXMLLoader(this.getClass().getResource("ScheduleSavingConfirmation.fxml"));
			}

			myPane = (Pane) loader.load();
			Scene scene = new Scene(myPane);
			stage.setScene(scene);
			stage.show();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
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


    	if (user.getRole() == User.FACULTY_MEMBER) {
        	Stage stage = new Stage();
    		Pane myPane = null;
    		FXMLLoader loader = null;
    		StudentFeedbackController controller = new StudentFeedbackController();
    		loader = new FXMLLoader(controller.getClass().getResource("StudentFeedback.fxml"));
    		myPane = (Pane) loader.load();
    		Scene scene = new Scene(myPane);
    		stage.setScene(scene);
    		stage.show();
    	} else if (user.getRole() == User.SCHEDULER) {

    		Stage stage = new Stage();
    		Pane myPane = null;
    		FXMLLoader loader = null;
    		FeedbackViewer controller = new FeedbackViewer();
    		loader = new FXMLLoader(controller.getClass().getResource("feedbackViewer.fxml"));
    		myPane = (Pane) loader.load();
    		Scene scene = new Scene(myPane);
    		stage.setScene(scene);
    		stage.show();

    	}
    }

    @FXML
	private void logoutMenuItemClicked(ActionEvent event) throws IOException {

    	//TODO(Sarah): Implement this
    	Stage stage = new Stage();
    	LoginViewController controller = new LoginViewController();
    	Parent root = FXMLLoader.load(controller.getClass().getResource("LoginView.fxml"));
		Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) addPanelButton.getScene().getWindow();
        currentStage.close();
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

    public void setSchedule(Schedule schedule) {
    	this.schedule = schedule;
    }

    public Schedule getSchedule() {
    	return this.schedule;
    }

    /**
	 * Selects the days of the week
	 * @return The days selected
	 */
    private int[] selectedDaysFromString(String str) {

    	int[] temp = new int[7];

		if (str.contains("M")) {
			temp[0] = 1;
		}
		if (str.contains("T")) {
			temp[1] = 2;
		}
		if (str.contains("W")) {
			temp[2] = 3;
		}
		if (str.contains("R")) {
			temp[3] = 4;
		}
		if (str.contains("F")) {
			temp[4] = 5;
		}
		if (str.contains("S")) {
			temp[5] = 6;
		}
		if (str.contains("X")) {
			temp[6] = 7;
		}

		for(int i : temp) {System.out.print(i);}
		System.out.println();

    	return temp;
    }

    /**
     * Adds a ui element for a section at the specified time
     * @param begin Beginning time for the appointment
     * @param end End time for the appointment
     * @param retval Pointer to weekview object to add the appointment to
     * @return retval
     */
	public void addApptFromSection(LocalDate begin, Section section, LinkedList<WeekViewAppointment<Object>> retval) {

        LocalDate firstDayOfWeek = begin;
    	Duration duration = Duration.ofMinutes(section.getDuration());
    	LocalTime time = LocalTime.parse(section.getStartTime());

        int[] days = selectedDaysFromString(section.getDaysOfWeek());
        int j = 1;

	    for (int i : days) {
	    	if (i < 1 || i > 7) {
	    		j++;
	    		if (j == 7) {
	    			System.out.println("No days selected.");
	    		}
	    		continue;
	    	}
	    	i--;
	    	LocalDate current = firstDayOfWeek.plusDays(i);
			LocalDateTime localDateTime = LocalDateTime.of(current, time);

	        BiPredicate<LocalDate, LocalTime> newTimePossiblePredicate = (newDate, newTime) -> {
	            if (newTime == null) {
	              return true;
	            }
	            if (newTime.getHour() >= 0 && newTime.getHour() <= 24) {
	              return true;
	            } else {
	              log.info("Wrong time {}", newTime);
	              return false;
	            }
	          };

	        WeekViewAppointment<Object> timedAppointment = new WeekViewAppointment<>(section.getName() + " " + i + " " + section.getDuration() + "m", localDateTime, duration);
	        log.info("Creating new appointment beginning at {} {}", current, time);
	        timedAppointment.setChangeStartCallback((newDate, newTime) -> {
	          log.info("{} now starts on {} {}", timedAppointment.getTitle(), newDate, newTime);
	        });
	        timedAppointment.setNewTimePossiblePredicate(newTimePossiblePredicate);

	        retval.add(timedAppointment);
	    }
	}

	public void displaySections(List<Section> sections) {
		for (Section section : sections) {
			if (weekView.titleString.length() < 1) {
				weekView.titleString = (section.getSchedule().getTerm() + " " + section.getSchedule().getYear());
			}
			addApptFromSection(begin, section, retval);
		}
		weekView.recreateEntries(retval);
	}
}