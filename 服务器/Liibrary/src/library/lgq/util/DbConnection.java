package library.lgq.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbConnection {

	private static String driverName="com.mysql.jdbc.Driver"; 
	   private static String userName="root";  //数据库用户名                 
	   private static String password="lu136153";         //数据库密码     
	   public static Connection getConnect(){
		  String url1="jdbc:mysql://localhost:3306/library";//本机的IP 和数据库名字
		  String url2="?user="+userName+"&password="+password;
		  String url3="&userUnicode=true&characterEncoding=utf8";
		  String url=url1+url2+url3;
		  try {
			 Class.forName(driverName);
			Connection con = DriverManager.getConnection(url);
			return con; 
		  }
		  
		  catch(Exception ex){
		    ex.printStackTrace();  
		    }finally{
		    	
		    }
		    return null;
	  }
	  public static  void closeDb(ResultSet rs,PreparedStatement ps,Connection con) {
		  try{
		 
		  if(rs!=null)
			  rs.close();
		  if(ps!=null)
			  ps.close();
		  if(con!=null)
			  con.close();
		 
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();  
		  } 
	  }
}
