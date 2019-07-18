package kr.ac.kirc.ekp.core.ontology;

import kr.ac.kirc.ekp.config.Path;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryParser.ParseException;

/**
 * Servlet implementation class searcher
 */
@WebServlet("/ontology")
public class Ontology_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ontology_servlet() {super();}

    static String convert(String str, String encoding) throws IOException {
    	  ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
    	  requestOutputStream.write(str.getBytes(encoding));
    	  return requestOutputStream.toString(encoding);
    	 }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html;charset=utf-8");
		String queries = request.getParameter("allQuery_ont");
		ResultSet rs=null;//new ResultSet();
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		ArrayList<String> columnName = new ArrayList<String>();	

		
		Ontology o = new Ontology();
		rs = o.ontology(queries);
		
		if(queries.isEmpty())	//empty query
		{	
			request.setAttribute("message", o.message);
			request.setAttribute("errcode", o.sqlError);
			request.setAttribute("stat", o.stat);
			
		}
		else if(o.sqlError!=0){		//sql error
			request.setAttribute("queries", queries);
			request.setAttribute("message", o.message);
			request.setAttribute("errcode", o.sqlError);
			request.setAttribute("stat", o.stat);
		
		}
		else
		{
			
			try {
				
				
			
				for(int i=1;i<=rs.getMetaData().getColumnCount();i++) columnName.add(rs.getMetaData().getColumnName(i));
				
				while(rs.next()){
					ArrayList<String> tempal=new ArrayList<String>();
					for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
						String euckrStr=convert(rs.getString(rs.getMetaData().getColumnName(i)),"utf-8");		
						tempal.add(euckrStr);
						}
					results.add(tempal);
				}
				
				
				
				
				request.setAttribute("stat", o.stat);
				request.setAttribute("fieldNames", columnName);
				request.setAttribute("tableName",o.tableName);
				request.setAttribute("nOfchanges", o.N);
				request.setAttribute("message", o.message);
				request.setAttribute("numDoc", results.size());
				request.setAttribute("queries", queries);
				request.setAttribute("sections", results);		
				request.setAttribute("errcode", o.sqlError);
				
				
				//put data into graph data
				Path pt=new Path();
				BufferedWriter out = new BufferedWriter(new FileWriter(pt.getGraph_data_path()+"data.js"));
				String s = o.gen_graph_data(columnName, results);
				out.write(s);; out.newLine();
				out.close();

				
			} catch (SQLException e1) {
				e1.printStackTrace();
				request.setAttribute("stat", o.stat);
				request.setAttribute("message", o.message);
				request.setAttribute("errcode", o.sqlError);
			}	
		}
		
		
		
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ontology_index.jsp");	
		dispatcher.forward(request, response);
		
	
	
	
	
	}
	
	
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
