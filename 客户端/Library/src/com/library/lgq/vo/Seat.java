package com.library.lgq.vo;

public class Seat {

	private String seatNum;
	private String principal;
	private String state;
	private String position;
	public Seat() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Seat(String seatNum, String principal, String state, String position) {
		super();
		this.seatNum = seatNum;
		this.principal = principal;
		this.state = state;
		this.position = position;
	}
	public String getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
