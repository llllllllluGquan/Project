package com.library.lgq.vo;

public class News {

	private String news_title;
	private String author;
	public News() {
		super();
		// TODO Auto-generated constructor stub
	}
	public News(String news_title, String author) {
		super();
		this.news_title = news_title;
		this.author = author;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
