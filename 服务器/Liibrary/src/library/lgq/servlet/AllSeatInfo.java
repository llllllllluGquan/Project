package library.lgq.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import library.lgq.javabean.Seat;
import library.lgq.javabean.SeatBean;

public class AllSeatInfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AllSeatInfo() {
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
		JSONArray array=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List<Seat> list=new ArrayList<Seat>();
		try {
			list=new SeatBean().getAllSeatInfo();
			if(list!=null){
				for(int i=0 ;i<list.size();i++){
					jsonObject.put("seatNum", list.get(i).getSeatNum());
					jsonObject.put("principal", list.get(i).getPrincipal());
					jsonObject.put("state", list.get(i).getState());
					jsonObject.put("position", list.get(i).getPosition());
					array.add(jsonObject);
				}
				out.write(array.toString().getBytes("utf-8"));
			}else{
				jsonObject.put("info", "²éÔƒÊ§”¡");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
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
