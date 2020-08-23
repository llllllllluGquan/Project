package library.lgq.javabean;

public class User_seat {


	private String username;
	private String curr_seatNum;
	private String curr_position;
	public User_seat() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User_seat(String username, String curr_seatNum, String curr_position) {
		super();
		this.username = username;
		this.curr_seatNum = curr_seatNum;
		this.curr_position = curr_position;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCurr_seatNum() {
		return curr_seatNum;
	}
	public void setCurr_seatNum(String curr_seatNum) {
		this.curr_seatNum = curr_seatNum;
	}
	public String getCurr_position() {
		return curr_position;
	}
	public void setCurr_position(String curr_position) {
		this.curr_position = curr_position;
	}


}
