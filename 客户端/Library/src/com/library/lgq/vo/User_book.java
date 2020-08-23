package com.library.lgq.vo;

public class User_book {


	
	private String username;
	private String book_name;
	private String book_num;
	private String date;
	private String end_date;
	public User_book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User_book(String username, String book_name, String book_num,
			String date, String end_date) {
		super();
		this.username = username;
		this.book_name = book_name;
		this.book_num = book_num;
		this.date = date;
		this.end_date = end_date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getBook_num() {
		return book_num;
	}
	public void setBook_num(String book_num) {
		this.book_num = book_num;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	

}
