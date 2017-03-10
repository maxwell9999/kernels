package core.feedback;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.database.DatabaseCommunicator;
import core.resources.Schedule;

public class FeedbackTest {

	@Before
	public void setUp()
	{
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser';");
		DatabaseCommunicator.deleteDatabase("schedules", "year=99999 AND term='F';");
	}
	
	@Test
	public void testAddFeedback() {

		int year = 99999; 
		String term = "F"; 
		DatabaseCommunicator.createNewSchedule("DRAFT", 99999, "F");
		
		int size = DatabaseCommunicator.queryDatabase("select * from feedback").size();

		Feedback feedback = new Feedback(year, term, "TestUser", "This schedule rocks!", 5);
		feedback.addToDatabase();
		assertEquals("Testing adding feedback to database...", size + 1, DatabaseCommunicator.queryDatabase("select * from feedback").size());
		
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser';");
		assertEquals("Testing removing feedback to database...", size, DatabaseCommunicator.queryDatabase("select * from feedback").size());
		
		DatabaseCommunicator.deleteScheduleTable("DRAFT", 99999, "F");
		DatabaseCommunicator.deleteDatabase("schedules", "year=99999 AND term='F';");
		
	}
	
	@Test
	public void testGetAverageRating()
	{
		int year = 99999; 
		String term = "F"; 
		Schedule schedule = new Schedule(term, year); 
		DatabaseCommunicator.createNewSchedule("DRAFT", 99999, "F");
		int scheduleId = Schedule.getScheduleId(year, term); 

		assertEquals("Testing default start...", 0, Feedback.getAverageRating(scheduleId), .1);
		Feedback feedback = new Feedback(year, term, "TestUser", "This schedule rocks!", 5);
		feedback.addToDatabase();
		feedback = new Feedback(year, term, "TestUser", "This schedule rocks!", 3);
		feedback.addToDatabase();
		assertEquals("Testing average...", 4, Feedback.getAverageRating(scheduleId), .1);
		
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser';");
		assertFalse("Testing removing feedback to database...", DatabaseCommunicator.resourceExists(feedback));
		
		DatabaseCommunicator.deleteScheduleTable("DRAFT", 99999, "F");
		DatabaseCommunicator.deleteDatabase("schedules", "year=99999 AND term='F';");
	}
	
	@After
	public void cleanUp()
	{
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser';");
	}

}
