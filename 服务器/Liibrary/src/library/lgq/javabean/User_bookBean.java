package library.lgq.javabean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.lgq.util.DbConnection;

public class User_bookBean {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private List<User_book> list=new ArrayList<User_book>();
	private User_book book=new User_book();
	
	/**
	 * query���˽������Ŀ
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public List<User_book> queryBookinfoByuser(String username) throws SQLException{
		con=DbConnection.getConnect();
		String sql="select * from user_book where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			while(rs.next()){
				book=new User_book(
						rs.getString("username")
						,rs.getString("book_name")
						,rs.getString("book_num")
						,rs.getString("date")
						,rs.getString("end_date"));
				list.add(book);
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return list;
	}
	
	/**
	 * �黹ͼ��
	 * @param username
	 * @param bookname
	 * @param booknum
	 * @param date
	 * @return
	 * @throws SQLException 
	 */
	public int returnBook(String username,String bookname,String booknum,String date) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="delete from user_book where username=? and book_name=? and book_num=? and date=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, bookname);
			ps.setString(3, booknum);
			ps.setString(4, date);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return  result;
	}
	
	/**
	 * ����ͼ��
	 * @param username
	 * @param booknum
	 * @return
	 * @throws SQLException 
	 */
	public int extraBook(String username,String booknum,String endDate) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="update user_book set end_date=? where username=? and book_num=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, endDate);
			ps.setString(2, username);
			ps.setString(3, booknum);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
	/**
	 * �õ����ĵĵ���ʱ��
	 * @param username
	 * @param booknum
	 * @return
	 * @throws SQLException
	 */
	public String getendDate(String username,String booknum) throws SQLException{
		String result=null;
		con=DbConnection.getConnect();
		String sql="select * from user_book where username=? and book_num=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, booknum);
			rs=ps.executeQuery();
			if(rs.next()){
				result=rs.getString("end_date");
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
}
