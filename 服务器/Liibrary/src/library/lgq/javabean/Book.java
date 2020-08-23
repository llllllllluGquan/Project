package library.lgq.javabean;

public class Book {
	
	private String book_name;
	private String book_author;
	private String book_margin;
	private String book_num;
	private String book_state;
	private String book_public;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	public Book(String book_name, String book_author, String book_margin,
			String book_num, String book_state, String book_public) {
		super();
		this.book_name = book_name;
		this.book_author = book_author;
		this.book_margin = book_margin;
		this.book_num = book_num;
		this.book_state = book_state;
		this.book_public = book_public;
	}



	public String getBook_public() {
		return book_public;
	}



	public void setBook_public(String book_public) {
		this.book_public = book_public;
	}



	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getBook_author() {
		return book_author;
	}
	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}
	public String getBook_state() {
		return book_state;
	}
	public void setBook_state(String book_state) {
		this.book_state = book_state;
	}
	public String getBook_margin() {
		return book_margin;
	}
	public void setBook_margin(String book_margin) {
		this.book_margin = book_margin;
	}
	public String getBook_num() {
		return book_num;
	}
	public void setBook_num(String book_num) {
		this.book_num = book_num;
	}
	
}
