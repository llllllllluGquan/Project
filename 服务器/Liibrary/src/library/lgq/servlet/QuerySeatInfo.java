package library.lgq.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.lgq.javabean.User_seat;
import library.lgq.javabean.User_seatBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QuerySeatInfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public QuerySeatInfo() {
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
		JSONArray array=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		try {
			List<User_seat> list=new User_seatBean().querySeatinfoByUser(username);
			if(list.size()!=0){
				for(int i=0;i<list.size();i++){
					jsonObject.put("username", list.get(i).getUsername());
					jsonObject.put("curr_seatNum", list.get(i).getCurr_seatNum());
					jsonObject.put("curr_position", list.get(i).getCurr_position());
					array.add(jsonObject);
				}
				out.write(array.toString().getBytes("utf-8"));
			}else{
				out.write("fail".toString().getBytes("utf-8"));
			}
		} catch (SQLException e) {
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

}
