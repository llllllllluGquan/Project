package library.lgq.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.lgq.javabean.User_bookBean;

public class ExtraBook extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExtraBook() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		OutputStream out=response.getOutputStream();
		String username=request.getParameter("username");
		String booknum=request.getParameter("booknum");
		System.out.println("续借");
		try {
			String endDate=new User_bookBean().getendDate(username, booknum);
			if(endDate!=null){
				System.out.println("enddate"+endDate);
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				Date date2=dateFormat.parse(endDate);
				Date date3=getDateAfter(date2,90);
				SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd");
				String str=dateFormat1.format(date3);
				int res=new User_bookBean().extraBook(username, booknum,str);
				if(res==1){
					out.write("续借成功".toString().getBytes("utf-8"));
				}else{
					out.write("续借失败".toString().getBytes("utf-8"));
				}
			}else{
				out.write("续借失败".toString().getBytes("utf-8"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	public static Date getDateAfter(Date date,int day){

		Calendar af=Calendar.getInstance();
		af.setTime(date);
		af.set(Calendar.DATE, af.get(Calendar.DATE)+day);

		return af.getTime();
	}
}
