package library.lgq.javabean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.lgq.util.DbConnection;

public class BookBean {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private List<Book> list=new ArrayList<Book>();
	private Book book;

	/**
	 * 获取所有书目
	 * @return
	 * @throws SQLException
	 */
	public List<Book> getAllBook() throws SQLException{
		con=DbConnection.getConnect();
		String sql="select * from book ";
		try {

			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){

				book=new Book(rs.getString("bookname")
						,rs.getString("book_author")
						,rs.getString("book_margin")
						,rs.getString("book_num")
						,rs.getString("book_state")
						,rs.getString("book_public"));
				list.add(book);
			}
		}finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return list;
	}

	/**
	 * 是否满足借书的条件
	 * @param username
	 * @return
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	public String  IsCanborrowByUsername(String username) throws NumberFormatException, SQLException{
		String result="no";
		con=DbConnection.getConnect();
		String sql="select * from user where userName=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				if(Integer.parseInt(rs.getString("can_borrow_book"))>0){
					result="yes";
				}else{
					result="no";
				}
			}
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}

	/**
	 * 借阅时修改user借阅数量
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public int UpdateBorrowCount(String username) throws SQLException{
		int result=0;
		int count =0;
		con=DbConnection.getConnect();
		String sql="update user set can_borrow_book=? where userName=?";
		try {
			count=this.canBorrowBook(username);
			System.out.println("bean里面的count"+count);
			ps=con.prepareStatement(sql);
			ps.setInt(1, (count-1));
			ps.setString(2, username);
			result=ps.executeUpdate();
			System.out.println("bean里面的result"+result);
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}

	/**
	 * 数量
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public int canBorrowBook(String username) throws SQLException{
		int count=0;
		con=DbConnection.getConnect();
		String sql="select * from user where userName=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				count=Integer.parseInt(rs.getString("can_borrow_book"));
				System.out.println("当前可借阅的数量----------"+count);
			}
		}catch (Exception e) {
			// TODO: handle exception
		} 
		return count;
	}

	/**
	 * 修改图书的数量a
	 * @param booknum
	 * @return
	 * @throws SQLException 
	 */
	public int updateBookMargin(String booknum) throws SQLException{
		int result=0;
		int count=0;
		con=DbConnection.getConnect();
		String sql="update book set book_margin=? where book_num=?";
		try {
			count=this.getCurrBookMargin(booknum);
			ps=con.prepareStatement(sql);
			ps.setInt(1, count-1);
			ps.setString(2, booknum);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}

	/**
	 * 获得当前图书的数量
	 * @param booknum
	 * @return
	 * @throws SQLException 
	 */
	public int getCurrBookMargin(String booknum) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="select * from book where book_num=?";
		ps=con.prepareStatement(sql);
		ps.setString(1, booknum);
		rs=ps.executeQuery();
		if(rs.next()){
			result=Integer.parseInt(rs.getString("book_margin"));
			System.out.println("当前图书的数量----------"+result);
		}
		return result;
	}

	/**
	 * 更新user_book中的信息
	 * @param username
	 * @param bookname
	 * @param booknum
	 * @param date
	 * @return
	 * @throws SQLException 
	 */
	public int updateUser_bookInfo(String username,String bookname,String booknum,String date,String enddate) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="insert into user_book(username,book_name,book_num,date,end_date) values(?,?,?,?,?)";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, bookname);
			ps.setString(3, booknum);
			ps.setString(4, date);
			ps.setString(5, enddate);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}

	/**
	 * 删除user_book中的信息
	 * @param username
	 * @param booknum
	 * @return
	 * @throws SQLException 
	 */
	public int deleteUser_bookInfo(String username,String booknum) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="delete from user_book where username=? and book_num=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, booknum);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
	/**
	 * 退还时修改借阅的数量can_borrow_book
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	public int updateCountWhilereturn(String username) throws SQLException{
		int result=0;
		int count=0;
		con=DbConnection.getConnect();
		String sql="update user set can_borrow_book=? where userName=?";
		try {
			count=this.canBorrowBook(username);
			ps=con.prepareStatement(sql);
			ps.setInt(1, count+1);
			ps.setString(2, username);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	
	/**
	 * 退还时修改book里面的margin
	 * @param booknum
	 * @return
	 * @throws SQLException 
	 */
	public int updateMarginWhilereturn(String booknum) throws SQLException{
		int result=0;
		int count=0;
		con=DbConnection.getConnect();
		String sql="update book set book_margin=? where book_num=?";
		try {
			count=getCurrBookMargin(booknum);
			
			ps=con.prepareStatement(sql);
			ps.setInt(1, count+1);
			ps.setString(2, booknum);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
}
