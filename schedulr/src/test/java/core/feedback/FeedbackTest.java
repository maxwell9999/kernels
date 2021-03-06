package core.feedback;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import core.database.DatabaseCommunicator;
import core.resources.Schedule;

public class FeedbackTest {

	@Test
	public void testAddFeedback() {
		int year = 9999; 
		String term = "SP"; 
		Schedule schedule = new Schedule(term, year); 
		schedule.addToDatabase();
		
		Feedback feedback = new Feedback(9999, "SP", "TestUser", "This schedule rocks!", 5);
		feedback.addToDatabase();
		assertTrue("Testing adding feedback to database...", DatabaseCommunicator.resourceExists("feedback", "username='TestUser'"));
		
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser'");
		assertFalse("Testing removing feedback to database...", DatabaseCommunicator.resourceExists("feedback", "username='TestUser'"));
	}
	
	@Test
	public void testGetAverageRating()
	{
		int year = 9999; 
		String term = "F"; 
		Schedule schedule = new Schedule(term, year); 
		schedule.addToDatabase();
		int scheduleId = Schedule.getScheduleId(year, term); 

		assertEquals("Testing default start...", 0, Feedback.getAverageRating(scheduleId), .1);
		Feedback feedback = new Feedback(year, term, "TestUser", "This schedule rocks!", 5);
		feedback.addToDatabase();
		feedback = new Feedback(year, term, "TestUser", "This schedule rocks!", 3);
		feedback.addToDatabase();
		assertEquals("Testing average...", 4, Feedback.getAverageRating(scheduleId), .1);
		
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser'");
		assertFalse("Testing removing feedback to database...", DatabaseCommunicator.resourceExists("feedback", "username='TestUser'"));
	}
	
	@After
	public void cleanUp()
	{
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser'");
	}

}
