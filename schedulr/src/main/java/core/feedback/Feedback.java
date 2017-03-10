package core.feedback;

import java.util.HashMap;
import java.util.List;

import core.database.DatabaseCommunicator;
import core.database.DatabaseObject;
import core.resources.Schedule;

public class Feedback implements DatabaseObject
{
	private int year; 
	private String term; 
	private String feedback;
	private String username;
	private int rating;
	private int scheduleID;
	
	//NOTE feedback can only be inserted if a schedule exists 
	//TODO (Courtney) handle situation when schedule does not yet exist for year/term
	public Feedback(int year, String term, String username, String feedback, int rating) {
		this.year = year; 
		this.term = term;
		this.username = username;
		this.feedback = feedback;
		this.rating = rating;
		this.scheduleID = Schedule.getScheduleId(this.year, this.term);
	}
	public String getKeys() {
		return "feedback, username, rating, schedule_id";
	}

	public String getValues() {
		return "'" + feedback + "', '" + username + "', " + rating + ", " + Schedule.getScheduleId(this.year, this.term);
	}
	
	public String getTable()
	{
		return "feedback";
	}
	
	public static double getAverageRating(int scheduleId)
	{
		double sum = 0;
		List<HashMap<String, Object>> ratings = DatabaseCommunicator.queryDatabase("Select rating from feedback where schedule_id=" + scheduleId + ";");
		if (ratings.size() == 0)
		{
			return 0;
		}
		for(HashMap<String, Object> map : ratings) {
			sum += (Integer) map.get("rating");
		}
		return sum / ratings.size();
	}
	
	public void addToDatabase() {
		DatabaseCommunicator.replaceDatabase(this);
	}
	public String getKeyIdentifier() {
		return null;
	}


}
