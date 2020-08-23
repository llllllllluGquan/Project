package library.lgq.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.lgq.javabean.User;
import library.lgq.javabean.UserBean;

@SuppressWarnings("serial")
public class Login extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Login() {
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
		String password=request.getParameter("password");
		System.out.println(username+"   "+password);
		String isUser=null;
		String isPwd=null;
		if(username!=null&&password!=null){
			try {
				isUser=new UserBean().isHasUser(username);
				if(isUser.equals("OK")){
					isPwd=new UserBean().isPwdCorrect(username, password);
					if(isPwd.equals("Correct")){
						out.write(("恭喜"+username+"登陆成功").toString().getBytes("utf-8"));
					}else{
						out.write("密码错误".toString().getBytes("utf-8"));
					}
				}else{
					out.write("用户不存在".toString().getBytes("utf-8"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			out.write("用户名或者密码为空！".toString().getBytes("utf-8"));
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
