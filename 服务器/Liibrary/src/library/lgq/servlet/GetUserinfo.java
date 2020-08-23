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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import library.lgq.javabean.Userinfo;
import library.lgq.javabean.UserinfoBean;

public class GetUserinfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetUserinfo() {
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
		List<Userinfo> list=null;
		JSONArray array=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		try {
			list= new UserinfoBean().getUserinfoByUsername(username);
			if(list!=null){
				for(int i=0;i<list.size();i++){
					jsonObject.put("username", list.get(i).getUsername());
					jsonObject.put("another_name", list.get(i).getAnother_name());
					jsonObject.put("signature", list.get(i).getSignature());
					jsonObject.put("area", list.get(i).getArea());
					jsonObject.put("sex", list.get(i).getSex());
					jsonObject.put("address", list.get(i).getAddress());
					array.add(jsonObject);
				}
				out.write(array.toString().getBytes("utf-8"));
			}else{
				out.write("get userinfo fail".getBytes("utf-8"));
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
