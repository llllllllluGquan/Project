package library.lgq.javabean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.lgq.util.DbConnection;

public class User_seatBean {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	private List<User_seat> list=new ArrayList<User_seat>();
	private User_seat seat=new User_seat(); 
	
	/**
	 * 更新user_seat表的状态
	 * @param username
	 * @param position
	 * @param seatNum
	 * @return
	 * @throws SQLException 
	 */
	public int  InsertUserBookseatInfo(String username,String position,String seatNum) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="insert into user_seat(user,curr_seatNum,curr_position) values(?,?,?)";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, seatNum);
			ps.setString(3, position);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
	/**
	 *  查看个人的占座信息
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public List<User_seat> querySeatinfoByUser(String username) throws SQLException{
		con=DbConnection.getConnect();
		String sql="select * from user_seat where user=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				seat=new User_seat(
						rs.getString("user")
						,rs.getString("curr_seatNum")
						,rs.getString("curr_position")); 
				list.add(seat);
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return list;
	}
	
	/**
	 * 退订座位
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public int deleteSeatInfoByUsername(String username) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="DELETE FROM user_seat where user=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
	public String getseatNum(String username) throws SQLException{
		String result= null;
		con=DbConnection.getConnect();
		String sql="select * from user_seat where user=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getString("curr_seatNum");
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
}
