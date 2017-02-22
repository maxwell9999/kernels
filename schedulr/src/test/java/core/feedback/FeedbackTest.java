package core.feedback;

import static org.junit.Assert.*;

import org.junit.Test;

import core.database.DatabaseCommunicator;

public class FeedbackTest {

	@Test
	public void testAddFeedback() {
		Feedback feedback = new Feedback("TestUser", "This schedule rocks!", 5);
		feedback.addToDatabase();
		assertTrue("Testing adding feedback to database...", DatabaseCommunicator.resourceExists("feedback", "username='TestUser'"));
		
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser'");
		assertFalse("Testing removing feedback to database...", DatabaseCommunicator.resourceExists("feedback", "username='TestUser'"));
	}
	
	@Test
	public void testGetAverageRating()
	{
		assertEquals("Testing default start...", 0, Feedback.getAverageRating(1), .1);
		Feedback feed = new Feedback("TestUser", "This schedule rocks!", 5);
		feed.addToDatabase();
		feed = new Feedback("TestUser", "This schedule rocks!", 3);
		feed.addToDatabase();
		assertEquals("Testing average...", 4, Feedback.getAverageRating(1), .1);
		
		DatabaseCommunicator.deleteDatabase("feedback", "username='TestUser'");
		assertFalse("Testing removing feedback to database...", DatabaseCommunicator.resourceExists("feedback", "username='TestUser'"));
	}

}
