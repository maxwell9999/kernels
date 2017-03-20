package gui.scheduling;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.accounts.AccountManager;
import core.accounts.FacultyMember;
import core.database.DatabaseCommunicator;
import core.resources.Course;
import core.resources.ResourceManager;
import core.resources.Room;
import core.resources.Schedule;
import core.resources.Section;
import de.ks.fxcontrols.weekview.WeekView;
import de.ks.fxcontrols.weekview.WeekViewAppointment;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.text.TextAlignment;

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
	@FXML
	Button closeButton = new Button();

	private WeekView<Section> weekView = null;
    private LocalDate begin, end = null;
    private Schedule schedule;
    private String daysOfWeek = "";
    LinkedList<WeekViewAppointment<Section>> retval;

    public AddPanelController() {}

    @FXML
    /**
     * Initializes the scene, runs at the beginning of initialization
     */
    public void initialize() {

    	//Populate all of the fields
    	populateDepartments();
    	populateFaculty();
    	populateRoomTypes();

    	initializeSpinners(0, 0, 50);
    	setHandlers();
    }

    private void setHandlers() {
    	addToCalendar.setOnAction(new addToCalendarHandler());
        selectNumber.setOnAction(new selectNumberHandler());

        //Show the list of rooms when the person clicks the room drop down
        selectRoomType.setOnAction(new selectRoomTypeHandler());

        //When a room is selected, autofill the capacity
        selectRoom.setOnAction(new selectRoomHandler());

        //Automatically generate a course number
        selectDepartment.setOnAction(new selectDepartmentHandler());
    }

	/**
	 * Sets up the spinners with correct data
	 */
	private void initializeSpinners(int hour, int min, int len) {

    	//set up GUI steppers
    	hourStepper.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, hour, 1));
        hourStepper.setEditable(true);

        minStepper.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, min, 1));
        minStepper.setEditable(true);

        length.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 360, len, 30));
	}

	public void initData(String panelTitle, WeekView<Section> weekView, LocalDate begin, LocalDate end, LinkedList<WeekViewAppointment<Section>> retval, Button closeButton) {
		this.panelTitle.setText(panelTitle);
		this.panelTitle.setTextAlignment(TextAlignment.CENTER);
		this.weekView = weekView;
		this.begin = begin;
		this.end = end;
		this.retval = retval;
		this.closeButton = closeButton;
	}

	/**
	 * Generates a list of the departments and put it into a dropdown
	 */
	private void populateDepartments() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT DISTINCT department FROM courses;");
		List<String> departments = new ArrayList<String>();
		for (HashMap<String, Object> row : rows) {
			departments.add(row.get("department").toString());
		}
		selectDepartment.setItems(FXCollections.observableArrayList(departments));
	}

	/**
	 * Generates a course number based on the department
	 * @param department Department where the course is being added
	 */
	private void populateCourseNumbers(String department) {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT number FROM courses WHERE department='" + department + "';");
		List<String> numbers = new ArrayList<String>();
		for (HashMap<String, Object> row : rows) {
			numbers.add(row.get("number").toString());
		}
		selectNumber.setItems(FXCollections.observableArrayList(numbers));
	}

	/**
	 * Fills the dropdown with all of the faulty member logins
	 */
	private void populateFaculty() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT login FROM users WHERE login!='admin';");
		List<String> faculty = new ArrayList<String>();
		for (HashMap<String, Object> row : rows) {
			faculty.add(row.get("login").toString());
		}
		selectFaculty.setItems(FXCollections.observableArrayList(faculty));
	}

	/**
	 * Gets all of the possible room types from the database and populates them all into a dropdown menu.
	 */
	private void populateRoomTypes() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT DISTINCT type FROM rooms;");
		List<String> roomTypes = new ArrayList<String>();
		for (HashMap<String, Object> row : rows) {
			roomTypes.add(row.get("type").toString());
		}
		selectRoomType.setItems(FXCollections.observableArrayList(roomTypes));
	}

	/**
	 * Checks the type of the room denoted by the scheduler and returns a list of rooms that match that room type
	 * @param type The type of classroom (ie. smart room)
	 */
	private void populateRoomNumbers(String type) {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT building, number FROM rooms WHERE type='" + type + "';");
		List<String> roomNumbers = new ArrayList<String>();
		for (HashMap<String, Object> row : rows) {
			roomNumbers.add(row.get("building").toString() + "-" + row.get("number").toString());
		}
		selectRoom.setItems(FXCollections.observableArrayList(roomNumbers));
	}

	/**
	 * Method to get auto-fill data (section number, name, and wtu) for a course
	 * @param department the department the course in contained in
	 * @param number the number describing the course
	 * @return String in format of "sectionNumber, name, wtu"
	 */
	//TODO(Courtney) make sure this works and write tests
	private String getCourseData(String department, int number) {
		String name;
		double wtu;
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
				"SELECT name, wtu FROM courses WHERE department='" + department + "' AND number=" + number + ";");
		name = (String) rows.get(0).get("name");
		wtu = (Double) rows.get(0).get("wtu");
		long sectionNumber = getSectionNumber(department, number);
		return sectionNumber + ", " + name + ", " + wtu;
	}

	//TODO(Courtney) Write tests
	private long getSectionNumber(String department, int number) {
		long sectionNumber;
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
				"SELECT COUNT(*) FROM TEMP_SCHEDULE WHERE department='" + department + "' AND course_number=" + number + " AND schedule_id=" + schedule.getScheduleId() + ";");
		if (rows.size() == 0)
		{
			return 1;
		}
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

	/**
	 * Method to add a Section to the database with fields specified in the AddPanel UI
	 * NOTE: must be called AFTER addAppt() in order for the days of the week to be properly stored
	 */
	private Section createSection() {
		Course course = ResourceManager.getCourse(selectDepartment.getValue(), Integer.parseInt(selectNumber.getValue()));
		FacultyMember instructor = (FacultyMember) AccountManager.getUser(selectFaculty.getValue());
		String[] roomCombo = selectRoom.getValue().split("-");
		int building = Integer.parseInt(roomCombo[0]);
		int roomNumber = Integer.parseInt(roomCombo[1]);
		Room room = ResourceManager.getRoom(building, roomNumber);
		String startTime = LocalTime.of(hourStepper.getValue(), minStepper.getValue(), 0).toString();
		int duration = length.getValue();

		Section section = new Section(schedule, course, instructor, room, startTime, duration, daysOfWeek);
		return section;
	}

	/**
	 * Method to create a lab section immediately after the specified lecture section
	 * NOTE: the start time of the lecture section will be changed after execution of this method
	 * @param lectSection the lecture section the lab is to be scheduled for
	 * @return the lab section with an updated start time
	 */
	public Section createLabSection(Section lectSection) {

		int duration = lectSection.getDuration();
    	DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
    	LocalTime lt = LocalTime.parse(lectSection.getStartTime());
    	String labStartTime = df.format(lt.plusMinutes(duration + 10));

		Section labSection = new Section(lectSection.getSchedule(),
				ResourceManager.getCourse(selectDepartment.getValue(), Integer.parseInt(selectNumber.getValue())),
										 lectSection.getInstructor(),
										 lectSection.getRoom(),
										 labStartTime,
										 duration,
										 lectSection.getDaysOfWeek());

    	return labSection;
	}

	/**
	 * @return a new room object
	 */
	private Room getRoom() {
		Room room = new Room();
		return room;
	}

	/**
	 * Selects the days of the week
	 * @return The days selected
	 */
    private int[] selectedDays() {

    	int[] temp = new int[7];

		if (m.isSelected()) {
			temp[0] = 1;
			daysOfWeek += "M";
		}
		if (t.isSelected()) {
			temp[1] = 2;
			daysOfWeek += "T";
		}
		if (w.isSelected()) {
			temp[2] = 3;
			daysOfWeek += "W";
		}
		if (r.isSelected()) {
			temp[3] = 4;
			daysOfWeek += "R";
		}
		if (f.isSelected()) {
			temp[4] = 5;
			daysOfWeek += "F";
		}
		if (s.isSelected()) {
			temp[5] = 6;
			daysOfWeek += "S";
		}
		if (x.isSelected()) {
			temp[6] = 7;
			daysOfWeek += "X";
		}

		for(int i : temp) {System.out.print(i);}
		System.out.println();

    	return temp;
    }

    /**
     * Adds an appointment at the specified time
     * @param begin Beginning time for the appointment
     * @param end End time for the appointment
     * @param retval Pointer to weekview object to add the appointment to
     * @return retval
     */
	private Section addAppt(LocalDate begin, LocalDate end, LinkedList<WeekViewAppointment<Section>> retval) {

        LocalDate firstDayOfWeek = begin;
    	Duration duration = Duration.ofMinutes(length.getValue());
    	LocalTime time = LocalTime.of(hourStepper.getValue(), minStepper.getValue(), 0);

        int[] days = selectedDays();
        int j = 1;


        Section newSection = createSection();
        Section labSection = null;
        newSection.addToDatabase();
        if (newSection.getLabHours() > 0) {
        	labSection = createLabSection(newSection);
        	labSection.addToDatabase();
        }
        daysOfWeek = "";

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

	        WeekViewAppointment<Section> timedAppointment = new WeekViewAppointment<Section>(selectDepartment.getValue() + selectNumber.getValue() + " " + name.getText(), localDateTime, duration);
	        log.info("Creating new appointment beginning at {} {}", current, time);
	        log.info("Creating new section {}", newSection.getKeyIdentifierWithDays());
//	        timedAppointment.setChangeStartCallback((newDate, newTime) -> {
//	          log.info("{} now starts on {} {}", timedAppointment.getTitle(), newDate, newTime);
//	        });
//	        timedAppointment.setNewTimePossiblePredicate(newTimePossiblePredicate);

	        timedAppointment.setUserData(newSection);
	        retval.add(timedAppointment);

	        if (labSection != null) {
	           Duration tenMinutes = Duration.ofMinutes(10);
        	   LocalTime labTime = time.plusNanos(duration.toNanos() + tenMinutes.toNanos());
        	   LocalDateTime labDateTime = labTime.atDate(current);
        	   WeekViewAppointment<Section> lab = new WeekViewAppointment<Section>("Lab: " + selectDepartment.getValue() + selectNumber.getValue() + " " + name.getText(), labDateTime, duration);
   	           log.info("Creating new appointment beginning at {} {}", current, labTime);
   	           log.info("Creating new section {}", labSection.getKeyIdentifierWithDays());
//   	           lab.setChangeStartCallback((newDate, newTime) -> {
//   	           log.info("{} now starts on {} {}", lab.getTitle(), newDate, newTime);
//   	           });
//   	           lab.setNewTimePossiblePredicate(newTimePossiblePredicate);
   	           lab.setUserData(labSection);
   	           retval.add(lab);
	        }
        }

        return newSection;
	}

	/**
	 * Sets the schedule ID for the class
	 * @param scheduleId schedule ID to use
	 */
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	/**
	 * Given faculty member and a time, query the database to see if there are teacher conflicts with any other classes at that time
	 * @param section Current section object to be added
	 * @return returns true if there are no conflicts
	 */
	private boolean checkTeacherConflicts(Section section) {

		//TODO(Courtney)Check if teacher is already booked in the time slot
		return true;
	}

	private boolean checkWtu(Section section) {
		int wtu = 0;
		String tableName = schedule.getTableName("DRAFT");
		FacultyMember teacher = section.getInstructor();
		List<HashMap<String, Object>> classList = DatabaseCommunicator.queryDatabase(
				"SELECT SUM(C.wtu) FROM courses C INNER JOIN sections S ON C.department = S.department" +
				"AND C.number = S.course_number WHERE S.instructor='" + teacher.getLogin() +
				"' AND S.schedule_id=" + schedule.getScheduleId() + ";");
		if (classList.size() != 0)
		{
			wtu = (Integer) (classList.get(0).get("SUM(C.wtu)"));
		}
		if (wtu > teacher.getMaxWtu())
		{
			return true;
		}
		return false;
	}

	//TODO(Courtney) You said that you could do this with a query super easily
	/**
	 * Given a room and a time, query the database to see if that room is already booked at that time
	 * @param section Current section object to be added
	 * @return returns false if there are no conflicts
	 */
	private boolean checkRoomConflicts(Section section) {
		Room room = section.getRoom();
		return true;
	}

	class addToCalendarHandler implements EventHandler<ActionEvent> {

		/**
		 * When add is clicked, check for teacher conflicts, if none, then add the sections to the database and update the view
		 */
		public void handle(ActionEvent event) {

			Section curSection = addAppt(begin, end, retval);

			if(checkTeacherConflicts(curSection) && checkRoomConflicts(curSection)) {
					weekView.recreateEntries(retval);
			}
			closeButton.fire();
		}
	}

	class selectNumberHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent event) {
			// courseData format: sectionNumber, name, wtu
			String courseData = getCourseData(selectDepartment.getValue(), Integer.parseInt(selectNumber.getValue()));
			String[] fields = courseData.split(", ");

			System.out.println(courseData);

			section.setText(fields[0]);
			name.setText(fields[1]);
			wtu.setText(fields[2]);
		}
	}

	//Show the list of rooms when the person clicks the room drop down
	class selectRoomTypeHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent event) {
			populateRoomNumbers(selectRoomType.getValue());
		}
	}

	//When a room is selected, autofill the capacity
	class selectRoomHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent event) {
			String[] room = selectRoom.getValue().split("-");
			capacity.setText("" + getCapacity(Integer.parseInt(room[0]), Integer.parseInt(room[1])));
		}
	}

	//Automatically generate a course number
	class selectDepartmentHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent event) {
			populateCourseNumbers(selectDepartment.getValue());
		}
	}

    private void selectDayBoxesFromString(String str) {
		if (str.contains("M")) {
			m.setSelected(true);
		}
		if (str.contains("T")) {
			t.setSelected(true);
		}
		if (str.contains("W")) {
			w.setSelected(true);
		}
		if (str.contains("R")) {
			r.setSelected(true);
		}
		if (str.contains("F")) {
			f.setSelected(true);
		}
		if (str.contains("S")) {
			s.setSelected(true);
		}
		if (str.contains("X")) {
			x.setSelected(true);
		}
    }

	public void fillInFields(WeekViewAppointment<Section> selected) {

		Section temp = selected.getUserData();
		selectDepartment.getSelectionModel().select(temp.getDepartment());
		selectNumber.getSelectionModel().select(String.valueOf(temp.getNumber()));
		selectFaculty.getSelectionModel().select(temp.getInstructor().getLogin());
		selectRoom.getSelectionModel().select(temp.getRoom().getBuilding() + "-" + temp.getRoom().getNumber());
		selectRoomType.getSelectionModel().select(temp.getRoom().getType());

		//trigger action events to fill rest of fields
		Event.fireEvent(selectDepartment, new ActionEvent());
		Event.fireEvent(selectNumber, new ActionEvent());
		Event.fireEvent(selectFaculty, new ActionEvent());
		Event.fireEvent(selectRoom, new ActionEvent());
		Event.fireEvent(selectRoomType, new ActionEvent());

		initializeSpinners(selected.getStartTime().getHour(), selected.getStartTime().getMinute(), (int) selected.getDuration().toMinutes());

		selectDayBoxesFromString(temp.getDaysOfWeek());

	}
}
