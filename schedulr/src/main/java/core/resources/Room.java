package core.resources;

import core.database.DatabaseObject;

public class Room implements DatabaseObject {
	private int building;
	private int number;
	private int capacity;
	private String type; 
	private String notes; 
	private String equipment;
	
	public Room() {
		
	}
	
	public Room(int building, int number, int capacity, String type, String notes, String equipment) {
		this.building = building;
		this.number = number; 
		this.capacity = capacity;
		this.type = type;
		this.notes = notes;
		this.equipment = equipment;
	}
	
	public void setBuilding(int building) {
		this.building = building;
	}
	
	public int getBuilding() {
		return building;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	
	public String getEquipment() {
		return equipment;
	}
	
	public String getKeys(){
		return "building, number, capacity, type, notes, equipment"; 
	}
	
	public String getValues() {
		return building + ", " + number + ", " + capacity + ", '" + type + "', '" + 
				notes + "', " + equipment;
	}
	
	public String getTable() {
		return "rooms"; 
	}
	
}
