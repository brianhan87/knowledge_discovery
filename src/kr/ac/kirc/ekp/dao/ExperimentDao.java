package kr.ac.kirc.ekp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.Query;
import kr.ac.kirc.ekp.bean.Relevance;
import kr.ac.kirc.ekp.bean.RelevancePair;
import kr.ac.kirc.ekp.bean.SearchResult;
import kr.ac.kirc.ekp.bean.TTAQuery;

public class ExperimentDao {
	public Connection getEKPConnection()
	{
		
		final String KSLAB_DB_ADDRESS = "jdbc:mysql://ekp.kaist.ac.kr:3306/kj_research";
		final String KSLAB_DB_USER = "admin";
		final String KSLAB_DB_PW = "rudgjawltlr1!";
		
	
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(KSLAB_DB_ADDRESS,KSLAB_DB_USER,KSLAB_DB_PW);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	public void closeConnection(Connection conn) throws SQLException
	{
		conn.close();
	}
	public ArrayList<Query> getQueryList(Connection conn) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from query_list where type=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "B");
		
		rs = pstmt.executeQuery();
		
		ArrayList<Query> queries = new ArrayList<Query>();
		
		while(rs.next())
		{
			Query q = new Query();
			q.setQuery_id(rs.getInt("query_id"));
			q.setQuery(rs.getString("text"));
			q.setQuery_original(rs.getString("query_original"));
			queries.add(q);
		}
		
		rs.close();
		pstmt.close();
				
		return queries;
		
	}
	public Query getSpecificQueryList(Connection conn,int query_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from query_list where type=? and query_id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "B");
		pstmt.setInt(2, query_id);
		
		rs = pstmt.executeQuery();
		
		Query q = new Query();
		
		while(rs.next())
		{
			
			q.setQuery_id(rs.getInt("query_id"));
			q.setQuery(rs.getString("text"));
			q.setQuery_original(rs.getString("query_original"));

		}
		
		rs.close();
		pstmt.close();
				
		return q;
		
	}
	
	public TTAQuery getTTAQuery(Connection conn, int query_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from query_test_sample where query_id=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,query_id);
		rs = pstmt.executeQuery();
		
		TTAQuery query = new TTAQuery();
		while(rs.next())
		{
			query.setQuery_id(rs.getInt("query_id"));
			query.setAnswer(rs.getString("answer"));
			query.setExpanded(rs.getString("expanded"));
		}
		rs.close();
		pstmt.close();
		
		return query;		
	}
	
	public String getQueryText(Connection conn, int query_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from query_list where query_id=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,query_id);
		
		rs = pstmt.executeQuery();
		
		String query="";
		
		while(rs.next())
		{
			query = rs.getString("text");
		}
		//System.out.println(query);
		rs.close();
		pstmt.close();
				
		return query;
	}
	public ArrayList<String> getSearchResult(Connection conn,int query_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from query_reformulation_result where query_id=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		
		rs = pstmt.executeQuery();
		ArrayList<String> json_results = new ArrayList<String>();
		String json_search_result ="";
		while(rs.next())
		{
			json_search_result = rs.getString("doc_list");
			json_results.add(json_search_result);
		}
		
		rs.close();
		pstmt.close();
				
		return json_results;
		
	}
	
	public int getNumberofRelevanceforQuerybyUser(Connection conn,int query_id,String user) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select relevance, count(relevance) as total from relevance_judgement_simple_simple where query_id=? and user=? group by relevance ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setString(2, user);
		
		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next())
		{
			if(rs.getInt("relevance") != 1)
			{
				total = total + rs.getInt("total");
			}
		}
		
		rs.close();
		pstmt.close();
				
		return total;
		
	}
	public ArrayList<RelevancePair> getPairofRelevance(Connection conn,String user_a, String user_b) throws SQLException
	{
		ArrayList<RelevancePair> pairs = new ArrayList<RelevancePair>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.passage_id, a.isbn, a.query_id, a.relevance as relevance_a, b.relevance as relevance_b from (select * from relevance_judgement_simple where user=? ) as a inner join (select * from relevance_judgement_simple where user=?) as b on a.query_id=b.query_id and a.passage_id = b.passage_id and a.isbn=b.isbn ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_a);
		pstmt.setString(2, user_b);
		
		rs = pstmt.executeQuery();
		int count = 0;

		while(rs.next())
		{
			int passage_id = rs.getInt("passage_id");
			String isbn = rs.getString("isbn");
			int query_id = rs.getInt("query_id");
			int relevance_a = rs.getInt("relevance_a");
			int relevance_b = rs.getInt("relevance_b");
			/*
			if(relevance_a ==2)
			{
				relevance_a = 3;
			}
			if(relevance_b ==2)
			{
				relevance_b = 3;
			}
			*/
			RelevancePair pair = new RelevancePair(passage_id,isbn,query_id,relevance_a,relevance_b);
			pairs.add(pair);
			count++;
			
		}
		System.out.println("ROW count is: " + count);
		rs.close();
		pstmt.close();
				
		return pairs;
		
	}
	
	public int getNumberofCompletionforQuery(Connection conn,int query_id,String user) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(query_id) as done from relevance_judgement_simple where query_id=? and user=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setString(2, user);
		
		rs = pstmt.executeQuery();
		ArrayList<String> json_results = new ArrayList<String>();
		int num_of_completion=0;
		while(rs.next())
		{
			num_of_completion= rs.getInt("done");
		}
		
		rs.close();
		pstmt.close();
				
		return num_of_completion;
		
	}
	public ArrayList<Relevance> getRelevanceJudgementbyQueryIdandUser(Connection conn,int query_id, String user) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from relevance_judgement_simple where query_id=? and user=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setString(2, user);
		
		rs = pstmt.executeQuery();
		ArrayList<Relevance> relevance_list = new ArrayList<Relevance>();
		while(rs.next())
		{
			Relevance relevance = new Relevance(rs.getInt("query_id"),rs.getString("isbn"),rs.getInt("passage_id"),rs.getInt("relevance"),rs.getString("user"),rs.getString("time"));
			relevance_list.add(relevance);
		}
		
		rs.close();
		pstmt.close();
				
		return relevance_list;
		
	}
	public Relevance getRelevanceJudgementbyQueryIdandISBNandPassage_id(Connection conn,int query_id, String isbn,int passage_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from relevance_judgement_simple where query_id=? and isbn=? and passage_id=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setString(2, isbn);
		pstmt.setInt(3, passage_id);
		
		rs = pstmt.executeQuery();
		Relevance relevance = new Relevance();
		while(rs.next())
		{
			relevance = new Relevance(rs.getInt("query_id"),rs.getString("isbn"),rs.getInt("passage_id"),rs.getInt("relevance"),rs.getString("user"),rs.getString("time"));
		}
		
		rs.close();
		pstmt.close();
				
		return relevance;
		
	}
	public int getStrictNumberofRelevance(Connection conn,String user_a, String user_b, int query_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.relevance, count(a.relevance) as total from "
				+"(select * from relevance_judgement_simple where user=? and query_id=? ) as a "
				+"inner join (select * from relevance_judgement_simple where user=? and query_id=?) as b "
				+ "on a.query_id=b.query_id and a.passage_id = b.passage_id and a.isbn=b.isbn and a.relevance=b.relevance group by relevance";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_a);
		pstmt.setInt(2, query_id);
		pstmt.setString(3, user_b);
		pstmt.setInt(4, query_id);

		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next())
		{
			if(rs.getInt("relevance") != 1)
			{
				total = total + rs.getInt("total");
			}
			if(rs.getInt("relevance") == 1)
			{
				System.out.println("Number of irrrevant: " + rs.getInt("total") );
			}
			
		}
		
		rs.close();
		pstmt.close();
				
		return total;
	}
	public Relevance getStrictRelevanceJudgementbyQueryIdandISBNandPassage_id(Connection conn,int query_id, String isbn,int passage_id,String user_a,String user_b) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.passage_id, a.isbn, a.query_id, a.relevance, a.time from "
				+"(select * from relevance_judgement_simple where user=? ) as a "
				+"inner join (select * from relevance_judgement_simple where user=?) as b "
				+ "on a.query_id=b.query_id and a.passage_id = b.passage_id and a.isbn=b.isbn and a.relevance=b.relevance "
				+ "where a.passage_id=? and a.isbn=? and a.query_id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_a);
		pstmt.setString(2, user_b);
		pstmt.setInt(3, passage_id);
		pstmt.setString(4, isbn);
		pstmt.setInt(5, query_id);
		
		rs = pstmt.executeQuery();
		Relevance relevance = new Relevance();
		String user = user_a = "_" + user_b;
		while(rs.next())
		{
			relevance = new Relevance(rs.getInt("query_id"),rs.getString("isbn"),rs.getInt("passage_id"),rs.getInt("relevance"),user,rs.getString("time"));
		}
		
		rs.close();
		pstmt.close();
				
		return relevance;
		
	}
	public String getSearchResultforEachMethod(Connection conn,int query_id, int method_num) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from query_reformulation_result where query_id=? and method_num=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setInt(2, method_num);
		
		rs = pstmt.executeQuery();
		String json_search_result ="";
		while(rs.next())
		{
			json_search_result = rs.getString("doc_list");
		}
		
		rs.close();
		pstmt.close();
				
		return json_search_result;
		
	}
	public ArrayList<String> getSearchResultFrom200SearchResults(Connection conn,int query_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from query_result_for_relevance_judgement where query_id=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		
		rs = pstmt.executeQuery();
		ArrayList<String> json_results = new ArrayList<String>();
		String json_search_result ="";
		while(rs.next())
		{
			json_search_result = rs.getString("doc_list");
			json_results.add(json_search_result);
		}
		
		rs.close();
		pstmt.close();
				
		return json_results;
		
	}
	public String isJudged(Connection conn,int query_id,int passage_id, String isbn, String user) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from relevance_judgement_simple where query_id=? and passage_id=? and isbn=? and user=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setInt(2, passage_id);
		pstmt.setString(3, isbn);
		pstmt.setString(4, user);
		
		rs = pstmt.executeQuery();
		boolean isjudged = false;
		int relevance = 0;
		while(rs.next())
		{
			relevance = rs.getInt("relevance");
		}
		
		rs.close();
		pstmt.close();
		
		if(relevance == 0)
		{
			return "Not Done";
		}
		else if(relevance == 1)
		{
			return "Not Relevant";
		}
		else if(relevance ==2)
		{
			return "Possibly Relevant";
		}
		else
		{
			return "Definitely Relevant";
		}		
	}
	
	public String isJudgedforTTA(Connection conn,int query_id,int passage_id, String isbn, String pageNum) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from relevance_judgement_simple where query_id=? and passage_id=? and isbn=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setInt(2, passage_id);
		pstmt.setString(3, isbn);
		
		rs = pstmt.executeQuery();
		boolean isjudged = false;
		int relevance = 0;
		while(rs.next())
		{
			relevance = rs.getInt("relevance");
			if(relevance == 2 || relevance  ==3)
			{
				break;
			}
		}
		
		rs.close();
		pstmt.close();
		
		if(query_id >=21 && query_id<=27 && Integer.parseInt(pageNum)==1)
		{
			relevance = 3;
		}
		
		if(relevance == 0)
		{
			return "Not Done";
		}
		else if(relevance == 1)
		{
			return "Not Relevant";
		}
		else if(relevance ==2)
		{
			return "Possibly Relevant";
		}
		else
		{
			return "Definitely Relevant";
		}		
	}
	public void writeSearchResults(Connection conn, int query_id, ArrayList<EBook> passages, int method_num) throws SQLException
	{
		
		
		PreparedStatement pstmt = null;
		
		JSONArray results = new JSONArray();
		for(int i=0;i<passages.size();i++)
		{
			JSONObject obj = new JSONObject();
			obj.put("isbn", passages.get(i).getIsbn());
			obj.put("number", passages.get(i).getNumber());
			obj.put("contents", passages.get(i).getContents());
			obj.put("rank", passages.get(i).getRank());
			obj.put("score", passages.get(i).getScore());
			obj.put("method_num", method_num);
			results.add(obj);
		}
		
		//ResultSet rs = null;
		String sql = "insert into query_reformulation_result (query_id,doc_list,method_num) values (?,?,?) ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setString(2, results.toJSONString());
		pstmt.setInt(3, method_num);
		//pstmt.setString(3, timestamp);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
	public void write200SearchResultsforAnnotation(Connection conn, int query_id, ArrayList<SearchResult> passages) throws SQLException
	{
		
		PreparedStatement pstmt = null;
		
		JSONArray results = new JSONArray();
		for(int i=0;i<passages.size();i++)
		{
			JSONObject obj = new JSONObject();
			obj.put("isbn", passages.get(i).getIsbn());
			obj.put("number", passages.get(i).getNumber());
			obj.put("contents", passages.get(i).getContents());
			obj.put("rank", passages.get(i).getRank());
			obj.put("score", passages.get(i).getScore());
			obj.put("method_num", passages.get(i).getMethod_num());
			results.add(obj);
		}
		
		//ResultSet rs = null;
		String sql = "insert into query_result_for_relevance_judgement_simple (query_id,doc_list) values (?,?) ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setString(2, results.toJSONString());
		//pstmt.setString(3, timestamp);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
	public void writeRelevanceJudgement(Connection conn, int query_id, String isbn, int passage_id, int relevance, String user, String time) throws SQLException
	{
		
		
		PreparedStatement pstmt = null;
		
		//ResultSet rs = null;
		String sql = "insert into relevance_judgement_simple (query_id,isbn,passage_id,relevance,user,time) values (?,?,?,?,?,?) ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, query_id);
		pstmt.setString(2, isbn);
		pstmt.setInt(3, passage_id);
		pstmt.setInt(4, relevance);
		pstmt.setString(5, user);
		pstmt.setString(6, time);
		//pstmt.setString(3, timestamp);
		
		pstmt.executeUpdate();
		pstmt.close();
		
	}
	public void updateRelevanceJudgement(Connection conn, int query_id, String isbn, int passage_id, int relevance,String user, String time) throws SQLException
	{
		PreparedStatement pstmt = null;
		//System.out.println(query_id + " " + isbn + " " + passage_id + " " + relevance + " " + user + " " + time);
		String select_sql = "update relevance_judgement_simple SET relevance = ? , time= ? where query_id=? and passage_id=? and isbn=? and user=?";
		
		pstmt = conn.prepareStatement(select_sql);
		pstmt.setInt(1, relevance);
		pstmt.setString(2, time);
		pstmt.setInt(3, query_id);
		pstmt.setInt(4, passage_id);
		pstmt.setString(5, isbn);
		pstmt.setString(6, user);
		
		pstmt.executeUpdate();
		pstmt.close();
	}	
}
