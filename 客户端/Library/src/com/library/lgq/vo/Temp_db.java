package com.library.lgq.vo;

public class Temp_db {

	private String date;
	private String history;
	public Temp_db() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Temp_db(String date, String history) {
		super();
		this.date = date;
		this.history = history;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	
}
