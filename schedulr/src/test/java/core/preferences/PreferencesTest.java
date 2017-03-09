package core.preferences;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import core.database.DatabaseCommunicator;

public class PreferencesTest {

	@Test
	public void calculateBlock() {
		Preferences pref = new Preferences();
		ArrayList<String> list = new ArrayList<String>();
		list.add("8:00");
		list.add("8:30");
		list.add("9:00");
		list.add("9:30");
		list.add(null);
		list.add(null);
		list.add("11:00");
		list.add(null);
		list.add("12:00");
		list.add("12:30");
		String expected = "8:00-10:00, 11:00-11:30, 12:00-13:00";
		assertEquals("Testing calculate block...", expected, pref.calculateBlock(list));
	}
	
	@Test
	public void PreferenceTest() {
		Preferences pref = new Preferences();
		ArrayList<String> prefList = new ArrayList<String>();
		prefList.add("8:00");
		prefList.add("8:30");
		prefList.add("9:00");
		prefList.add("9:30");
		prefList.add(null);
		prefList.add(null);
		prefList.add("11:00");
		prefList.add(null);
		prefList.add("12:00");
		prefList.add("12:30");
		
		ArrayList<String> canList = new ArrayList<String>();
		canList.add("13:00");
		canList.add("13:30");
		canList.add("14:00");
		canList.add("14:30");
		canList.add(null);
		canList.add(null);
		canList.add("15:00");
		canList.add(null);
		canList.add("16:00");
		canList.add("16:30");
		
		pref.setMWFPreferences("test", canList, prefList);
		
		HashMap<String, Object> map = DatabaseCommunicator.queryDatabase("Select * from MWF_Preferences where login='test';").get(0);
		String expected = "8:00-10:00, 11:00-11:30, 12:00-13:00";
		assertEquals("Testing preference block...", expected, map.get("pref").toString());
		expected = "13:00-15:00, 15:00-15:30, 16:00-17:00";
		assertEquals("Testing can block...", expected, map.get("can").toString());
		
		pref.setTRPreferences("test", canList, prefList);
		
		map = DatabaseCommunicator.queryDatabase("Select * from TR_Preferences where login='test';").get(0);
		expected = "8:00-10:00, 11:00-11:30, 12:00-13:00";
		assertEquals("Testing preference block...", expected, map.get("pref").toString());
		expected = "13:00-15:00, 15:00-15:30, 16:00-17:00";
		assertEquals("Testing can block...", expected, map.get("can").toString());
	}

}
