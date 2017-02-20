package core.resources;



import junit.framework.TestCase;
import org.junit.Test;


public class RoomTest extends TestCase{
	
	Room room = new Room(); 

	@Test
	public void testEmptyConstructor() {
		assertTrue("Testing Room empty constructor...", 
				room instanceof Room);
	
		assertEquals(0, room.getBuilding()); 
		assertEquals(0, room.getNumber()); 
		assertEquals(0, room.getCapacity());
		assertNull(room.getType()); 
		assertNull(room.getNotes()); 
		
	}
	
	@Test
	public void testSetters() {
		room.setBuilding(14);
		assertEquals("Testing Room setBuilding...", 
				14, room.getBuilding()); 
		
		room.setNumber(303);
		assertEquals("Testing Room setNumber...",
				303, room.getNumber());
		
		room.setCapacity(35);
		assertEquals("Testing Room setCapacity...",
				35, room.getCapacity());
		
		room.setType("AlienwareLab");
		assertEquals("Testing Room setType...",
				"AlienwareLab", room.getType());
		
		room.setNotes("Alienware Lab reserved for advanced graphics classes.");
		assertEquals("Testing Room setNotes...",
				"Alienware Lab reserved for advanced graphics classes.", room.getNotes());
	}
	
	@Test
	public void testNonEmptyConstructor() {
		room = new Room(14, 247, 30, "SmallLecture", "Chairs are old.");
		assertTrue("Testing Room non-empty constructor...", 
				room instanceof Room);
		assertEquals(14, room.getBuilding()); 
		assertEquals(247, room.getNumber()); 
		assertEquals(30, room.getCapacity()); 
		assertEquals("SmallLecture", room.getType()); 
		assertEquals("Chairs are old.", room.getNotes()); 
	}
	
}


