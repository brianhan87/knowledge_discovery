package kr.ac.kirc.ekp.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.ac.kirc.ekp.dao.userDao;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		HttpSession session = request.getSession();
		System.out.println("Login attempt by " + id + " with " + pw);
		try {
			//String task_ids = dao.loginReturnsTaskIds(conn, id, pw);
			String task_ids = dao.loginReturnsTaskIdsforTTA(conn, id, pw);
			//System.out.println("current_task ids: " +  task_ids);
			if(!task_ids.equals("none"))
			{
				dao.updateStatus(conn, id);
				session.setAttribute("user",id);
				session.setAttribute("task_ids", task_ids);
				System.out.println("login was successful for " + id);
				String context_path = request.getContextPath();
				System.out.println(id + " has been accessed to " + context_path+"/tta_index.jsp");
				response.sendRedirect(context_path+"/tta_index.jsp");
				
				//RequestDispatcher dispatcher = request.getRequestDispatcher("/annotation_index.jsp");	
				//System.out.println("COMPLETED FOR HERE");
				//dispatcher.forward(request, response);
				//response.sendRedirect("http://ekp.kaist.ac.kr:8080/kirc_ekp/annotation_index.jsp");
				
			}
			else
			{
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('invalid login');");
				out.println("history.back(-1);");
				out.println("</script>");
				  
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				dao.closeConnection(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
