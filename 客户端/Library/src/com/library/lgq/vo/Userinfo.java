package com.library.lgq.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Userinfo implements Serializable{

	private String username;
	private String another_name;
	private String signature;
	private String area;
	private String sex;
	private String address;
	public Userinfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Userinfo(String username, String another_name, String signature,
			String area, String sex, String address) {
		super();
		this.username = username;
		this.another_name = another_name;
		this.signature = signature;
		this.area = area;
		this.sex = sex;
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAnother_name() {
		return another_name;
	}
	public void setAnother_name(String another_name) {
		this.another_name = another_name;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
}
