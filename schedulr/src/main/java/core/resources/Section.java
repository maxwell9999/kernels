package core.resources;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import core.accounts.FacultyMember;
import core.database.DatabaseCommunicator;
import core.database.DatabaseObject;

public class Section extends Course implements DatabaseObject {
	
	private Schedule schedule; 
	private FacultyMember instructor; 
	private Room room; 
	private String startTime; 
	private int duration; // hours per day
	private String daysOfWeek; 
	
	public Section()  {
		
	}

	// daysOfWeek must be one of "MWF" or "TR"
	public Section(Schedule schedule, Course course, FacultyMember instructor, Room room, String startTime, int duration, String daysOfWeek) {
		super(course.getDepartment(), course.getNumber(), course.getName(), course.getWtu(), course.getLectHours(), 
				course.getNotes(), course.getLabHours(), course.getActHours()); 
		this.schedule = schedule;
		this.instructor = instructor; 
		this.room = room;
		this.startTime = startTime; 
		this.duration = duration; 
		this.daysOfWeek = daysOfWeek; 
	}

	public FacultyMember getInstructor() {
		return instructor;
	}

	public void setInstructor(FacultyMember instructor) {
		this.instructor = instructor;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(String daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	
	public String getKeys() {
		return "department, course_number, building, room_number, instructor, start_hour, days_of_week, schedule_id";
	}

	//TODO fix formatting of start time to match database
	//TODO null in test
	public String getValues() {
		System.out.println("'" + this.getDepartment() + "', " + this.getNumber() + ", " + room.getBuilding() + ", " + room.getNumber() + ", '" + 
				instructor.getLogin() + "', '" + 
				(this.getStartTime()+":00") + "', '" + 
				this.daysOfWeek + "', " + 
				schedule.getScheduleId()) ;
		return "'" + this.getDepartment() + "', " + this.getNumber() + ", " + room.getBuilding() + ", " + room.getNumber() + ", '" + 
				instructor.getLogin() + "', '" + 
				this.getStartTime() + "', '" + 
				this.daysOfWeek + "', " + 
				schedule.getScheduleId() ;
	}
	
	public String getTable(Schedule schedule, String status)
	{
		String tableName = status.toUpperCase() + "_" + schedule.getYear() + "_" + schedule.getTerm().toUpperCase(); 
		return tableName;  
	}
	
	public void addToDatabase() {
		DatabaseCommunicator.replaceDatabase(this);
	}
	
	public String getKeyIdentifier()
	{
		return "department='" + this.getDepartment() + "' AND course_number=" + this.getNumber() + " AND instructor='" + instructor.getLogin() + 
				"' AND start_hour='" + this.getStartTime() + "'";
	}
	
	//TODO update this method
	public String getTable()
	{
		return schedule.getTableName("draft");
	}
	
}
