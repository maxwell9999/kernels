package core.resources;

import java.util.HashMap;
import java.util.List;

import core.database.DatabaseCommunicator;
import core.database.DatabaseObject;

public class Schedule implements DatabaseObject {
	private String term; 
	private int year;
	
	public Schedule() {
		
	}
	
	public Schedule(String term, int year) {
		super();
		this.term = term;
		this.year = year;
	}
	
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
	public int getScheduleId() {
		List<HashMap<String, Object>> scheduleAttributes = DatabaseCommunicator.queryDatabase(
				"SELECT id FROM schedules WHERE year=" + this.year + " AND term='" + this.term + "';");
		return Integer.parseInt(scheduleAttributes.get(0).get("id").toString()); 
	}

	public String getKeys() {
		return "year, term";
	}
	
	public String getValues() {
		return this.getYear() + ", '" + this.getTerm() + "'; ";
	}
	
	public String getTable()
	{
		return "schedules";
	}
	
}
