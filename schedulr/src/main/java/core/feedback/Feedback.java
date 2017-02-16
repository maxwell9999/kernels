package core.feedback;

import java.util.List;
import java.util.HashMap;

import core.database.DatabaseCommunicator;
import core.database.DatabaseObject;

public class Feedback implements DatabaseObject
{
	private String feedback;
	private String username;
	private int rating;
	private int scheduleID;
	
	public Feedback(String username, String feedback, int rating) {
		this.username = username;
		this.feedback = feedback;
		this.rating = rating;
		this.scheduleID = 1;
	}
	public String getKeys() {
		return "feedback, username, rating, schedule_id";
	}

	public String getValues() {
		return "'" + feedback + "', '" + username + "', " + rating + ", (SELECT id from schedules WHERE id=1)";
	}
	
	public String getTable()
	{
		return "feedback";
	}
	
	public static double getAverageRating(int schedule)
	{
		double sum = 0;
		List<HashMap<String, Object>> ratings = DatabaseCommunicator.queryDatabase("Select rating from feedback where schedule_id=" + schedule + ";");
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
		DatabaseCommunicator.insertDatabase(this);
	}

}
