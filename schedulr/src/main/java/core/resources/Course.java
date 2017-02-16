package core.resources;

import core.database.DatabaseObject;

public class Course implements DatabaseObject{
	
	private String department;
	private int number;
	private String name;
	private double wtu;
	private int lect_hours;
	private String notes;
	private int lab_hours;
	private int act_hours;
	
	public Course(String dept, int num, String name, double wtu, int lect_hours, String notes, int lab_hours, int act_hours)
	{
		this.department = dept;
		this.number = num;
		this.name = name;
		this.wtu = wtu;
		this.lect_hours = lect_hours;
		this.notes = notes;
		this.lab_hours = lab_hours;
		this.act_hours = act_hours;
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
		this.act_hours = 0;
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
	public int getAct_hours() {
		return act_hours;
	}
	public void setAct_hours(int act_hours) {
		this.act_hours = act_hours;
	}

	public String getKeys() {
		return "department, number, name, wtu, lect_hours, notes, lab_hours, act_hours";
	}

	public String getValues() {
		return "'" + department + "', " + number + ", '" + name + "', " + wtu + ", " + 
				lect_hours + ", " + notes + ", " + lab_hours + ", " + act_hours;
	}
	
	public String getTable()
	{
		return "courses";
	}
}
