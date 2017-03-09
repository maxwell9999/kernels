package core.preferences;

import java.util.List;

import core.database.DatabaseCommunicator;
import core.database.DatabaseObject;

public class Preferences implements DatabaseObject {
	
	private String table;
	private String values;
	private String login;
	
	
	public void setMWFPreferences(String login, List<String> canTeachList, List<String> preferTeachList)
	{
		updateDatabase("MWF_Preferences", login, canTeachList, preferTeachList);
	}
	
	public void setTRPreferences(String login, List<String> canTeachList, List<String> preferTeachList)
	{
		updateDatabase("TR_Preferences", login, canTeachList, preferTeachList);
	}
	
	private void updateDatabase(String setTable, String login, List<String> canTeachList, List<String> preferTeachList) {
		String canTeach = calculateBlock(canTeachList);
		String prefTeach = calculateBlock(preferTeachList);
		updateValues(login, setTable, "'" + canTeach + "', '" + prefTeach + "'");
		DatabaseCommunicator.replaceDatabase(this);
	}
	
	public String calculateBlock(List<String> unblockedList)
	{
		StringBuilder output = new StringBuilder();
	
		String startBlock = "";
		String endBlock = "";
		for (int i = 0; i < unblockedList.size(); i++)
		{
			if (unblockedList.get(i) != null) {
				startBlock = unblockedList.get(i);
				while (i < unblockedList.size() && unblockedList.get(i) != null) {
					endBlock = getNextBlock(unblockedList.get(i));
					i++;
				}
				output.append(startBlock + "-" + endBlock + ", ");
			}
		}
		output.delete(output.length() - 2, output.length());
		return output.toString();
	}
	
	private void updateValues(String login, String table, String values)
	{
		this.login = login;
		this.table = table;
		this.values = "'" + login + "', " + values;
	}
	
	private String getNextBlock(String currentTime) {
		int currentStartHour = Integer.parseInt(currentTime.split(":")[0]);
		
		//If the time is 00 - 30
		if (currentTime.charAt(currentTime.length() - 2) == '0') {
			return currentStartHour + ":30";
		}
		//If it is a 30-00 hour
		else if (currentTime.charAt(currentTime.length() - 2) == '3') {
			return (currentStartHour + 1) + ":00";	
		}
		else {
			System.out.println("ERROR");
			return null;
		}
	}

	@Override
	public String getKeys() 
	{
		return "login, can, pref";
	}

	@Override
	public String getValues() 
	{
		return values;
	}

	@Override
	public String getTable() {
		return table;
	}

	@Override
	public String getKeyIdentifier() {
		return "login='" + login + "'";
	}
	
	

}
