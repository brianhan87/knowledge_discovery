package kr.ac.kirc.ekp.core.search;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.SearchResult;
import kr.ac.kirc.ekp.dao.ExperimentDao;

/**
 * Servlet implementation class tta_searcher
 */
@WebServlet("/tta_searcher")
public class tta_searcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public tta_searcher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html;charset=euc-kr");
		ExperimentDao dao = new ExperimentDao();
		Connection conn = dao.getEKPConnection();
		
		String queries = "";
		int query_id = Integer.parseInt(request.getParameter("query_id"));
		try {
			queries = dao.getQueryText(conn, query_id);
			//System.out.println(query_text);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null)
		{
			pageNum="1";
		}

		ArrayList<SearchResult> search_results = new ArrayList<SearchResult>();
		try {
			//ArrayList<String> json_results = dao.getSearchResult(conn, query_id);
			ArrayList<String> json_results = dao.getSearchResultFrom200SearchResults(conn, query_id);
			for(int i=0;i<json_results.size();i++)
			{
				JSONParser parser = new JSONParser();
				JSONArray passages = (JSONArray)parser.parse(json_results.get(i));
				Iterator iterator = passages.iterator();
				int size = 1;
				while(iterator.hasNext())
				 {
					 JSONObject obj = (JSONObject)iterator.next();
					 String isbn = (String)obj.get("isbn");
					 int number = (int)(long)obj.get("number");
					 String contents = (String)obj.get("contents");
					 int rank = (int)(long)obj.get("rank");
					 double score = (double)obj.get("score");
					 int method_num = (int)(long)obj.get("method_num");
					 
					 SearchResult result = new SearchResult(isbn,number,contents,rank,score,method_num);
					 boolean isDuplicated = false;
					 for(int j=0;j<search_results.size();j++)
					 {
						 
						 if(search_results.get(j).getIsbn().equals(isbn) && search_results.get(j).getNumber() ==number)
						 {
							 isDuplicated = true;
						 }
					 }
					 
					 if(isDuplicated == false)
					 {
						 search_results.add(result);
						
					 }
					 
				 }
				//System.out.println(" The number of retrieved passages is: " + search_results.size());
			}
			
			//Collections.shuffle(search_results);
			
			request.setAttribute("passages", search_results);
			request.setAttribute("query_id", query_id);
			request.setAttribute("TTAquery", dao.getTTAQuery(conn, query_id));
			
			System.out.println("ttaquery: _" + dao.getTTAQuery(conn, query_id));
			
			
			
			//request.setAttribute("pageNum", pageNum);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/tta_result.jsp?pageNum="+pageNum);	
			System.out.println("COMPLETED FOR HERE");
			dispatcher.forward(request, response);
		} catch (SQLException | org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
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
