package library.lgq.javabean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.lgq.util.DbConnection;

public class UserBean {

	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps;
	private List<User> list=new ArrayList<User>();
	private User user;
	
	/**
	 * 核对账户
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public String isHasUser(String username) throws SQLException{
		String result=null;
		con=DbConnection.getConnect();
		String sql="select * from user where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				result="OK";
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	/**
	 * 核对密码
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public String isPwdCorrect(String username,String password) throws SQLException{
		String result=null;
		try {
			con=DbConnection.getConnect();
			String sql="select * from user where username=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				if(rs.getString("password").equals(password)){
					result="Correct";
				}else{
					result="Wrong";
				}
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	/**
	 * 核对是否可以站位，一人占一个
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public String IsBookTheSeat(String username) throws SQLException{
		String result=null;
		con=DbConnection.getConnect();
		String sql="select can_book_seat from user where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getString(1);
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	/**
	 * 核对可以占座以后 ，进行订座修改can_book_seat状态
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public int updatecanBookSeat(String username) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="update user set can_book_seat=? where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, "no");
			ps.setString(2, username);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
	/**
	 * 退座的时候 更改can_book_seat
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public int updatecanBookSeatWhiledebook(String username) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="update user set can_book_seat=? where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, "yes");
			ps.setString(2, username);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
}
