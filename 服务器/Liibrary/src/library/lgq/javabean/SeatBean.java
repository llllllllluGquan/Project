package library.lgq.javabean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.lgq.util.DbConnection;

public class SeatBean {

	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps;
	private List<Seat> list=new ArrayList<Seat>();
	private Seat seat;
	/**
	 * 查詢所有的座位
	 * @return
	 * @throws SQLException
	 */
	public List<Seat> getAllSeatInfo() throws SQLException{
		con=DbConnection.getConnect();
		String sql="select * from seat";
		try {
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				seat=new Seat(rs.getString("seatNum")
						,rs.getString("principal")
						,rs.getString("state")
						,rs.getString("position"));
				list.add(seat);
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		
		return list;
	}
	
	/**
	 * 訂座，更新seat表的state狀態
	 * @param seatNum
	 * @return
	 * @throws SQLException 
	 */
	public int BookSeat(String seatNum) throws SQLException{
		int result=0;
		try {
			con=DbConnection.getConnect();
			String sql="update seat set state=? where seatNum=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, "1");
			ps.setString(2, seatNum);
			result=ps.executeUpdate();
			
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 * @throws SQLException
	 */
	public List<Seat> selectSeatByPosition(String position) throws SQLException{
		con=DbConnection.getConnect();
		String sql="select * from seat  where position=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, position);
			rs=ps.executeQuery();
			while(rs.next()){
				seat=new Seat(rs.getString("seatNum")
						,rs.getString("principal")
						,rs.getString("state")
						,rs.getString("position"));
				list.add(seat);
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		
		return list;
	}
	
	/**
	 * 退座更新状态
	 * @param seatNum
	 * @return
	 * @throws SQLException
	 */
	public int updateSeatInfo(String seatNum) throws SQLException{
		int result=0;
		try {
			con=DbConnection.getConnect();
			String sql="update seat set state=? where seatNum=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, "0");
			ps.setString(2, seatNum);
			result=ps.executeUpdate();
			
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
}
