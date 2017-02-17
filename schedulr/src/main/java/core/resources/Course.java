package core.resources;

import core.database.DatabaseCommunicator;
import core.database.DatabaseObject;

public class Course implements DatabaseObject{
	
	private String department;
	private int number;
	private String name;
	private double wtu;
	private int lectHours;
	private String notes;
	private int labHours;
	private int actHours;
	
	public Course(String dept, int num, String name, double wtu, int lectHours, String notes, int labHours, int actHours)
	{
		this.department = dept;
		this.number = num;
		this.name = name;
		this.wtu = wtu;
		this.lectHours = lectHours;
		this.notes = notes;
		this.labHours = labHours;
		this.actHours = actHours;
	}
	
	public Course()
	{
		this.department = null;
		this.number = 0;
		this.name = null;
		this.wtu = 0;
		this.lectHours = 0;
		this.notes = null;
		this.labHours = 0;
		this.actHours = 0;
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

	public double getWtu() {
		return wtu;
	}

	public void setWtu(double wtu) {
		this.wtu = wtu;
	}
	
	public void addWtu(double wtu) {
		this.wtu += wtu;
	}

	public int getLectHours() {
		return lectHours;
	}

	public void setLectHours(int lectHours) {
		this.lectHours = lectHours;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getLabHours() {
		return labHours;
	}

	public void setLabHours(int labHours) {
		this.labHours = labHours;
	}
	public int getActHours() {
		return actHours;
	}

	public void setActHours(int actHours) {
		this.actHours = actHours;
	}

	public String getKeys() {
		return "department, number, name, wtu, lect_hours, notes, lab_hours, act_hours";
	}

	public String getValues() {
		return "'" + department + "', " + number + ", '" + name + "', " + wtu + ", " + 
				lectHours + ", " + notes + ", " + labHours + ", " + actHours;
	}
	
	public String getTable()
	{
		return "courses";
	}
	
	public void updateCourse()
	{
		DatabaseCommunicator.replaceDatabase(this);
	}

	public String getKeyIdentifier() {
		return "department='" + department + "' AND number=" + number;
	}
}
