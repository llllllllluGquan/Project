package library.lgq.javabean;

public class User {

	private String username;
	private String password;
	private String usertype;
	private int can_borrow_book;
	private String sex;
	private String age;
	private String department;
	private String grade;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String username, String password, String usertype,
			int can_borrow_book, String sex, String age, String department,
			String grade) {
		super();
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.can_borrow_book = can_borrow_book;
		this.sex = sex;
		this.age = age;
		this.department = department;
		this.grade = grade;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public int getCan_borrow_book() {
		return can_borrow_book;
	}
	public void setCan_borrow_book(int can_borrow_book) {
		this.can_borrow_book = can_borrow_book;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
