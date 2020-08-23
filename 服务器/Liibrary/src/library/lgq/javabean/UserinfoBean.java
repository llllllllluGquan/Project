package library.lgq.javabean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.lgq.util.DbConnection;

public class UserinfoBean {

	private Connection  con;
	private PreparedStatement ps;
	private ResultSet rs;
	private List<Userinfo> list=new ArrayList<Userinfo>();
	private Userinfo userinfo;

	/**
	 * 查询个人信息by username
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public List<Userinfo> getUserinfoByUsername(String username) throws SQLException{
		con=DbConnection.getConnect();
		String sql="select * from userinfo where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				userinfo=new Userinfo(rs.getString("username")
						,rs.getString("another_name")
						,rs.getString("signature")
						,rs.getString("area")
						,rs.getString("sex")
						,rs.getString("address"));
				list.add(userinfo);
			}
		}finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return list;
	}
	/**
	 * 修改昵称
	 * @param username
	 * @param nickname
	 * @return
	 * @throws SQLException 
	 */
	public int updateNickname(String username,String nickname) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="update userinfo set another_name=? where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, nickname);
			ps.setString(2, username);
			result=ps.executeUpdate();
		}finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	/**
	 * 修改家庭住址
	 * @param username
	 * @param address
	 * @return
	 * @throws SQLException 
	 */
	public int updateAddress(String username,String address) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="update userinfo set address=? where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, address);
			ps.setString(2, username);
			result=ps.executeUpdate();
			
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	/**
	 * 修改性别
	 * @param username
	 * @param sex
	 * @return
	 * @throws SQLException
	 */
	public int updateSex(String username,String sex) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="update userinfo set sex=? where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, sex);
			ps.setString(2, username);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
	/**
	 * 修改个性签名
	 * @param username
	 * @param sign
	 * @return
	 * @throws SQLException 
	 */
	public int updateSignature(String username,String sign) throws SQLException{
		int result=0;
		con=DbConnection.getConnect();
		String sql="update userinfo set signature=? where username=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, sign);
			ps.setString(2, username);
			result=ps.executeUpdate();
		} finally{
			DbConnection.closeDb(rs, ps, con);
		}
		return result;
	}
}
