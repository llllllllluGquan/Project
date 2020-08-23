package library.lgq.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.lgq.javabean.BookBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateUser_bookInfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdateUser_bookInfo() {
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
		String type=request.getParameter("type");
		System.out.println("lgq===="+type);
		String username=request.getParameter("username");
		String booknum=request.getParameter("booknum");
		String bookname=request.getParameter("bookname");
		System.out.println(username+booknum+bookname);
		String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Date date1=new Date();
		String enddate=new SimpleDateFormat("yyyy-MM-dd").format(getDateAfter(date1, 90));
		if(type.equals("update")){
			try {
				int  res=new BookBean().updateUser_bookInfo(username, bookname, booknum, date,enddate);
				if(res!=0){
					System.out.println("½èÔÄ³É¹¦");
					out.write("success".toString().getBytes("utf-8"));
				}else{
					out.write("fail".toString().getBytes("utf-8"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(type.equals("delete")){
			try {
				int  res=new BookBean().deleteUser_bookInfo(username, booknum);
				if(res!=0){
					out.write("success".toString().getBytes("utf-8"));
				}else{
					out.write("fail".toString().getBytes("utf-8"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	/**
	 * 
	 */
	public Date getDateAfter(Date date,int day){
		
		Calendar af=Calendar.getInstance();
		af.setTime(date);
		af.set(Calendar.DATE, af.get(Calendar.DATE)+day);
		
		return af.getTime();
	}
}
