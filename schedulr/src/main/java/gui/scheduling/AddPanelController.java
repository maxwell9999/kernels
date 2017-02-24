package gui.scheduling;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.database.DatabaseCommunicator;
import de.ks.fxcontrols.weekview.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddPanelController extends VBox {

	private static final Logger log = LoggerFactory.getLogger(AddPanelController.class);

	@FXML
	Text panelTitle;
	@FXML
	ComboBox<String> selectDepartment;
	@FXML
	ComboBox<String> selectNumber; 
	@FXML
	ComboBox<String> selectFaculty;
	@FXML
	ComboBox<String> selectRoomType;
	@FXML
	ComboBox<String> selectRoom;
	@FXML
	TextField section;
	@FXML
	TextField name;
	@FXML 
	TextField capacity; 
	@FXML
	TextField wtu;
	@FXML
	TextArea description;
	@FXML
	TextArea note = new TextArea();
	@FXML
	Spinner<Integer> hourStepper = new Spinner<Integer>();
	@FXML
	Spinner<Integer> minStepper = new Spinner<Integer>();
	@FXML
	Spinner<Integer> length = new Spinner<Integer>();
	@FXML
	Button addToCalendar = new Button();
	@FXML
	CheckBox m = new CheckBox();
	@FXML
	CheckBox t = new CheckBox();
	@FXML
	CheckBox w = new CheckBox();
	@FXML
	CheckBox r = new CheckBox();
	@FXML
	CheckBox f = new CheckBox();
	@FXML
	CheckBox s = new CheckBox();
	@FXML
	CheckBox x = new CheckBox();

	private WeekView<Object> weekView = null;
    private LocalDate begin, end = null;
    private int scheduleId; 
    LinkedList<WeekViewAppointment<Object>> retval;

    public AddPanelController() {}

    @FXML
    public void initialize() {
    	
    	populateDepartments(); 
    	populateFaculty(); 
    	populateRoomTypes(); 

        SpinnerValueFactory<Integer> hSValFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
        hourStepper.setValueFactory(hSValFac);
        hourStepper.setEditable(true);

        SpinnerValueFactory<Integer> mSValFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1);
        minStepper.setValueFactory(mSValFac);
        minStepper.setEditable(true);

    	SpinnerValueFactory<Integer> lengthValFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 360, 50, 60);
        length.setValueFactory(lengthValFac);

        addToCalendar.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                weekView.recreateEntries(addAppt(begin, end, retval));
            }
        });
        
        //TODO make sure naming works
        selectNumber.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		section.setText("" + getSectionNumber(selectDepartment.getValue(), 
        				Integer.parseInt(selectNumber.getValue())));
        		name.setText(getCourseName(selectDepartment.getValue(), Integer.parseInt(selectNumber.getValue())));
        	}
        });
        
        selectRoomType.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		populateRoomNumbers(selectRoomType.getValue()); 
        	}
        });

        selectRoom.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		String[] room = selectRoom.getValue().split("-"); 
        		capacity.setText("" + getCapacity(Integer.parseInt(room[0]), Integer.parseInt(room[1])));
        	}
        });

        selectDepartment.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		populateCourseNumbers(selectDepartment.getValue()); 
        	}
        });

    }

	public void initData(String panelTitle, WeekView<Object> weekView, LocalDate begin, LocalDate end, LinkedList<WeekViewAppointment<Object>> retval) {
		this.panelTitle.setText(panelTitle);
		this.weekView = weekView;
		this.begin = begin;
		this.end = end;
		this.retval = retval;
	}
	
	
	private void populateDepartments() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT DISTINCT department FROM courses;"); 
		List<String> departments = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			departments.add(row.get("department").toString()); 
		}
		selectDepartment.setItems(FXCollections.observableArrayList(departments)); 
	}
	
	private void populateCourseNumbers(String department) {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT number FROM courses WHERE department='" + department + "';"); 
		List<String> numbers = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			numbers.add(row.get("number").toString()); 
		}
		selectNumber.setItems(FXCollections.observableArrayList(numbers)); 
	}

	private void populateFaculty() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT first_name, last_name FROM users WHERE login!='admin';"); 
		List<String> faculty = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			faculty.add(row.get("first_name").toString() + row.get("last_name".toString())); 
		}
		selectFaculty.setItems(FXCollections.observableArrayList(faculty)); 
	}
	
	private void populateRoomTypes() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT DISTINCT type FROM rooms;"); 
		List<String> roomTypes = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			roomTypes.add(row.get("type").toString()); 
		}
		selectRoomType.setItems(FXCollections.observableArrayList(roomTypes)); 
	}
	
	private void populateRoomNumbers(String type) {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT building, number FROM rooms WHERE type='" + type + "';"); 
		List<String> roomNumbers = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			roomNumbers.add(row.get("building").toString() + "-" + row.get("number").toString()); 
		}
		selectRoom.setItems(FXCollections.observableArrayList(roomNumbers)); 
	}
	
	//TODO(Courtney) make sure this works and write tests
	private String getCourseName(String department, int number) {
		String name; 
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
				"SELECT name FROM courses WHERE department='" + department + "' AND number=" + number + ";");
		name = (String) rows.get(0).get("name"); 
		return name; 
	}

	//TODO(Courtney) Write tests 
	private long getSectionNumber(String department, int number) {
		long sectionNumber; 
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
				"SELECT COUNT(*) FROM sections WHERE department='" + department + "' AND number=" + number + "AND schedule_id=" + scheduleId + ";"); 
		sectionNumber = (Long) rows.get(0).get("COUNT(*)") + 1; 
		System.out.println("Section number = " + sectionNumber);
		return sectionNumber; 
	}
	
	private int getCapacity(int building, int number) {
		int capacity; 
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
				"SELECT capacity FROM rooms WHERE building=" + building + " AND number=" + number + ";"); 
		capacity = (Integer) rows.get(0).get("capacity"); 
		return capacity; 
	}

    private int[] selectedDays() {

    	int[] temp = new int[7];

		if (m.isSelected()) {
			temp[0] = 1;
		}
		if (t.isSelected()) {
			temp[1] = 2;
		}
		if (w.isSelected()) {
			temp[2] = 3;
		}
		if (r.isSelected()) {
			temp[3] = 4;
		}
		if (f.isSelected()) {
			temp[4] = 5;
		}
		if (s.isSelected()) {
			temp[5] = 6;
		}
		if (x.isSelected()) {
			temp[6] = 7;
		}

		for(int i : temp) {System.out.print(i);}
		System.out.println();

    	return temp;
    }

	private LinkedList<WeekViewAppointment<Object>> addAppt(LocalDate begin, LocalDate end, LinkedList<WeekViewAppointment<Object>> retval) {

        LocalDate firstDayOfWeek = begin;

        int[] days = selectedDays();
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
        	LocalTime time = LocalTime.of(hourStepper.getValue(), minStepper.getValue());
        	Duration duration = Duration.ofMinutes(length.getValue());
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

	        WeekViewAppointment<Object> timedAppointment = new WeekViewAppointment<>(name.getText() + " " + i + " " + length.getValue() + "m", localDateTime, duration);
	        log.info("Creating new appointment beginning at {} {}", current, time);
	        timedAppointment.setChangeStartCallback((newDate, newTime) -> {
	          log.info("{} now starts on {} {}", timedAppointment.getTitle(), newDate, newTime);
	        });
	        timedAppointment.setNewTimePossiblePredicate(newTimePossiblePredicate);
	        retval.add(timedAppointment);
        }
        return retval;
	}
	
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId; 
	}
/*
	private LinkedList<WeekViewAppointment<Object>> editAppt(LocalDate begin, LocalDate end, LinkedList<WeekViewAppointment<Object>> retval) {

        LocalDate firstDayOfWeek = begin;

        int[] days = selectedDays();
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
        	LocalTime time = LocalTime.of(hourStepper.getValue(), minStepper.getValue());
        	Duration duration = Duration.ofMinutes(length.getValue());
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

	        WeekViewAppointment<Object> timedAppointment = new WeekViewAppointment<>(name.getText() + " " + i + " " + length.getValue() + "m", localDateTime, duration);
	        log.info("Creating new appointment beginning at {} {}", current, time);
	        timedAppointment.setChangeStartCallback((newDate, newTime) -> {
	          log.info("{} now starts on {} {}", timedAppointment.getTitle(), newDate, newTime);
	        });
	        timedAppointment.setNewTimePossiblePredicate(newTimePossiblePredicate);
	        retval.add(timedAppointment);
        }
        return retval;
	}

	private LinkedList<WeekViewAppointment<Object>> rmAppt(LocalDate begin, LocalDate end, LinkedList<WeekViewAppointment<Object>> retval) {

        LocalDate firstDayOfWeek = begin;

        int[] days = selectedDays();
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
        	LocalTime time = LocalTime.of(hourStepper.getValue(), minStepper.getValue());
        	Duration duration = Duration.ofMinutes(length.getValue());
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

	        WeekViewAppointment<Object> timedAppointment = new WeekViewAppointment<>(name.getText() + " " + i + " " + length.getValue() + "m", localDateTime, duration);
	        log.info("Creating new appointment beginning at {} {}", current, time);
	        timedAppointment.setChangeStartCallback((newDate, newTime) -> {
	          log.info("{} now starts on {} {}", timedAppointment.getTitle(), newDate, newTime);
	        });
	        timedAppointment.setNewTimePossiblePredicate(newTimePossiblePredicate);
	        retval.add(timedAppointment);
        }
        return retval;
	}*/
}
