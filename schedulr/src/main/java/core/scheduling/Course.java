package core.scheduling;

import core.database.DatabaseCommunicator;

public class Course {
	//| department | number | name | wtu  | lect_hours | notes | lab_hours |
	
	private String department;
	private int number;
	private String name;
	private int wtu;
	private int lect_hours;
	private String notes;
	private int lab_hours;
	
	public Course(String dept, int num, String name, int wtu, int lect_hours, String notes, int lab_hours)
	{
		this.department = dept;
		this.number = num;
		this.name = name;
		this.wtu = wtu;
		this.lect_hours = lect_hours;
		this.notes = notes;
		this.lab_hours = lab_hours;
	}
	
	public Course()
	{
		this.department = null;
		this.number = 0;
		this.name = null;
		this.wtu = 0;
		this.lect_hours = 0;
		this.notes = null;
		this.lab_hours = 0;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWtu() {
		return wtu;
	}

	public void setWtu(int wtu) {
		this.wtu = wtu;
	}

	public int getLect_hours() {
		return lect_hours;
	}

	public void setLect_hours(int lect_hours) {
		this.lect_hours = lect_hours;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getLab_hours() {
		return lab_hours;
	}

	public void setLab_hours(int lab_hours) {
		this.lab_hours = lab_hours;
	}
	
	public void addToDatabase()
	{
		String fieldString = "department, number, name, wtu, lect_hours, notes, lab_hours";
		String valueString = "'" + department + "', " + number + ", '" + name + "', " + wtu + ", " + 
				lect_hours + ", " + notes + ", " + lab_hours;
		
		DatabaseCommunicator.insertDatabase("courses", fieldString, valueString);
	}
}
