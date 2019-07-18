package kr.ac.kirc.ekp.experiment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kr.ac.kirc.ekp.bean.SearchResult;
import kr.ac.kirc.ekp.dao.ExperimentDao;

/**
 * Servlet implementation class DeterminingRelevance
 */
@WebServlet("/DeterminingRelevance")
public class DeterminingRelevance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeterminingRelevance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		int relevance = Integer.parseInt(request.getParameter("relevance"));
		String isbn_tmp = request.getParameter("isbn");
		int query_id = Integer.parseInt(request.getParameter("query_id"));
		int passage_id = Integer.parseInt(request.getParameter("passage_id"));
		String pageNum = request.getParameter("pageNum");
		
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute("user");
		
		if(user==null||user.equals("")){
			
			//RequestDispatcher dispatcher = request.getRequestDispatcher("/authentification.jsp");	
			//dispatcher.forward(request, response);
			System.out.println("Session is over. login required.");
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('Session is over. login required.');");
			out.println("location.href=\"./authentification.jsp\"");
			out.println("</script>");
			out.close();
			
		}
		else
		{
			ExperimentDao dao = new ExperimentDao();
			Connection conn = dao.getEKPConnection();
			
			long time = System.currentTimeMillis(); 

			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

			String time_log = dayTime.format(new Date(time));

			
			
			try {
				if(dao.isJudged(conn, query_id, passage_id, isbn_tmp, user).equals("Not Done"))
				{
					dao.writeRelevanceJudgement(conn, query_id, isbn_tmp, passage_id, relevance,user,time_log);
				}
				else
				{
					dao.updateRelevanceJudgement(conn, query_id, isbn_tmp, passage_id, relevance,user,time_log);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{	
				RequestDispatcher dispatcher = request.getRequestDispatcher("searcher?pageNum="+pageNum+"&query_id="+query_id);	
				dispatcher.forward(request, response);
						
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
