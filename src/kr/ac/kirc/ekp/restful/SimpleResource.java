package kr.ac.kirc.ekp.restful;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.sun.jersey.api.client.ClientResponse.Status;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.Journal;
import kr.ac.kirc.ekp.bean.Log;
import kr.ac.kirc.ekp.bean.Topic;
import kr.ac.kirc.ekp.bean.User;
import kr.ac.kirc.ekp.config.EBookInfo;
import kr.ac.kirc.ekp.core.search.Query_Formulator;
import kr.ac.kirc.ekp.core.search.Search;
import kr.ac.kirc.ekp.dao.EBDao;
import kr.ac.kirc.ekp.dao.JournalDao;
import kr.ac.kirc.ekp.user.LogControl;
import kr.ac.kirc.ekp.user.UserControl;

@Path("/")
public class SimpleResource {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		SimpleResource r = new SimpleResource();
		r.makeLog("hi", "requested_ip");
		System.out.println("API resource open...");
	}
	
	static LogControl lc = new LogControl();

	
	public void makeLog(String api_name, String requested_ip) throws SQLException
	{ 
		Date dt = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String date = format.format(dt);
		
		Log log = new Log(api_name,requested_ip,date);
		lc.writeLog(log);
		//System.out.println(getName());
		
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/hello")
	public String getHelloMessage(@Context HttpServletRequest request) throws SQLException
	{
		System.out.println(request.getRequestURL());
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return "hello, REST"; 
	}
	
	@PUT
	@Path("/account/id={id}&pw={pw}&name={name}&department={department}&chmod={chmod}")
	public String addUser(@Context HttpServletRequest request, @PathParam("id")final String id, @PathParam("pw")final String pw, @PathParam("name")final String name, @PathParam("department")final String department, @PathParam("chmod")final String chmod) throws SQLException
	{
		//String s = user_id + " : " + user_pw + " : " + department + " : " + chmod;
		//System.out.println(s);
	
		UserControl control = new UserControl();
		int s = control.addAccount(id, pw, name, department, chmod);
		
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return String.valueOf(s);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN+";charset=utf-8")
	@Path("/account/id={id}&pw={pw}")
	public String attempttoLogin(@Context HttpServletRequest request, @PathParam("id")final String id, @PathParam("pw")final String pw) throws Exception
	{
		//String s = user_id + " : " + user_pw + " : " + department + " : " + chmod;
		//System.out.println(s);
		UserControl control = new UserControl();
		int s = control.login(id, pw);
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return String.valueOf(s);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN+";charset=utf-8")
	@Path("/account/id={id}/user")
	public String getAccount(@Context HttpServletRequest request, @PathParam("id")final String id) throws Exception
	{
		UserControl control = new UserControl();
		User user = control.getAccount(id);
		
		//System.out.println("HIHI");
		SimpleJSON json = new SimpleJSON();
		String s = json.writeUser(user);
		//System.out.println("HIHI2");
		//System.out.println(user.toString());
		
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return s;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN+";charset=utf-8")
	@Path("/log/from={from}&to={to}")
	public String getLogs(@Context HttpServletRequest request, @PathParam("from")final String from, @PathParam("to")final String to) throws Exception
	{
		//UserControl control = new UserControl();
		//User user = control.getAccount(id);
		
		LogControl control = new LogControl();
		ArrayList<Log> logs = control.getLogbytimestamp(from, to);
		
		
		SimpleJSON json = new SimpleJSON();
		String s = json.writeLog(logs);
		
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return s;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN+";charset=utf-8")
	@Path("/account/id={id}")
	public String CheckIdValidity(@Context HttpServletRequest request, @PathParam("id")final String id) throws Exception
	{
		//String s = user_id + " : " + user_pw + " : " + department + " : " + chmod;
		//System.out.println(s);
		UserControl control = new UserControl();
		int val = control.checkId(id);
		
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return val + "";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN+";charset=utf-8")
	@Path("/account/pw={pw}")
	public String CheckPasswardValidity(@Context HttpServletRequest request, @PathParam("pw")final String pw) throws Exception
	{
		//String s = user_id + " : " + user_pw + " : " + department + " : " + chmod;
		//System.out.println(s);
		UserControl control = new UserControl();
		int s = control.checkPw(pw);
		
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return String.valueOf(s);
	}
	
	@PUT
	@Path("/account/id={id}&pw={pw}")
	public String updatePassword(@Context HttpServletRequest request, @PathParam("id")final String id, @PathParam("pw")final String pw) throws Exception
	{
		//String s = user_id + " : " + user_pw + " : " + department + " : " + chmod;
		//System.out.println(s);
		UserControl control = new UserControl();
		int s = control.changePassward(id, pw);
		//System.out.println(request.getRequestURL());
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return String.valueOf(s);
	}
	
	@PUT
	@Path("/account/id={id}&chmod={chmod}")
	public String updateChmod(@Context HttpServletRequest request, @PathParam("id")final String id, @PathParam("chmod")final String chmod) throws Exception
	{
		//String s = user_id + " : " + user_pw + " : " + department + " : " + chmod;
		//System.out.println(s);
		UserControl control = new UserControl();
		int s = control.changeChmod(id, chmod);
		
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return String.valueOf(s);
	}
	
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllBooks")
	public String printAllBooks(@QueryParam("q")final String queries, @Context HttpServletRequest request) throws Exception
	{
		System.out.println("Book search begins");
		System.out.println("Requested from " + request.getRemoteAddr());
		Date dt = new Date();
		System.out.println(dt.toString());
		if(queries == null)
		{
			String err = "Please specify the query parameters 'q'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		Search s = new Search(); 
		Query_Formulator formulator = new Query_Formulator();
		ArrayList<Topic> topics = formulator.execute(queries);
		String query_text = "";
		if(topics.size() == 0 )
		{
			String err = "The current version of Query Detector detects nothings from " + queries + "at this stage. Begin search with a natural query";
			System.err.println(err);
			//return err;
			query_text = queries;
		}
		else
		{
			System.out.println("NOW!!" + topics.toString());
			
			for(int i=0;i<topics.size();i++)
			{
				if(topics.get(i).isMust())
				{
					query_text =  topics.get(i).getTitle() + "^10.0 , " + query_text;
				}
				else
				{
					query_text = topics.get(i).getTitle() + " , " + query_text;
				}
			}	
		}
		System.out.println("FINAL QUERY TEXT IS: " + query_text);
		ArrayList<EBook> books = s.search(query_text);
		System.out.println("NUMBER OF BOOKS IS: " +books.size());
	
		
		
		int total_retrieved_document = books.size();
		SimpleJSON sj = new SimpleJSON();
		String json = sj.writeBookJSON(topics, books,total_retrieved_document);
		
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return json;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	@Path("/getBooks")
	public String printBooks(@QueryParam("q")final String queries, @QueryParam("prev")final int prev, @QueryParam("next")final int next, @Context HttpServletRequest request) throws Exception
	{
		System.out.println("Book search begins");
		System.out.println("Requested from " + request.getRemoteAddr());
		Date dt = new Date();
		System.out.println(dt.toString());
		System.out.println("Natural Query: " + queries);
		if( prev == 0 || next ==0)
		{
			String err = "Please specify the query parameters 'prev' and 'next'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		
		if(queries == null)
		{
			String err = "Please specify the query parameters 'q'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
				
		
		Search s = new Search(); 
		Query_Formulator formulator = new Query_Formulator();
		
		
		ArrayList<Topic> topics = formulator.execute(queries);
		

		//System.out.println("Forumated Queries: " + topics.toString());
		String json ;
		String query_text = "";
		if(topics.size() == 0 )
		{
			String err = "The current version of Query Detector detects nothings from " + queries + "at this stage. Begin search with a natural query";
			System.err.println(err);
			//return err;
			query_text = queries;
		}
		else
		{
			ArrayList<String> must_topics = new ArrayList<String>();
			for (int i=0;i<topics.size();i++)
			{
				if(topics.get(i).isMust())
				{
					query_text = topics.get(i).getTitle() + "^10.0 , " + query_text;
					must_topics.add(topics.get(i).getTitle());
				}
			}
			
			for(int i=0;i<topics.size();i++)
			{
					if(!topics.get(i).isMust())
					{
						//query_text = topics.get(i).getTitle() + "^10.0 , " + query_text;
						if(!must_topics.contains(topics.get(i).getTitle() ))
						{
							query_text = topics.get(i).getTitle() + " , " + query_text;
						}
					}
					//else
					//{
						
						//query_text = topics.get(i).getTitle() + " , " + query_text;
					//}
			}
		}
		
		System.out.println("FINAL QUERY TEXT IS: " + query_text);
		long start_search = System.currentTimeMillis();	
		ArrayList<EBook> books = s.search(query_text);
		//System.out.println("NUMBER OF BOOKS IS: " +books.size());
		long end_search = System.currentTimeMillis();	
		
		System.out.println(((end_search-start_search)) + " seconds was executed for search.");
		long end2 = System.currentTimeMillis();	
		SimpleJSON sj = new SimpleJSON();
		
		
		ArrayList<EBook> tmp_books = new ArrayList<EBook>();
		
		int final_prev = prev; 
		int final_next = next;
				
		if (books.size() < final_prev)
		{
			String err = "The total number of the retrieved document is " + books.size() + ", it is less than requested prev: " + prev;
			return err;
		}
		
		if (books.size() < final_next)
		{
			final_next = books.size();
		}	
				
		int range = final_next-final_prev + 1;
		
		int i = 0;
		while(i<range)
		{
			//System.out.println(final_next + " and " + final_prev);
			tmp_books.add(books.get((final_prev-1)+i));
			i++;
		}
		
		int total_retrieved_document = books.size();
		long start = System.currentTimeMillis();
		books.clear();			
		json = sj.writeBookJSON(topics, tmp_books,total_retrieved_document);
		long end = System.currentTimeMillis();	
		
		System.out.println(((end-start)/1000) + " seconds was executed for formatting json.");
		//long end2 = System.currentTimeMillis();	
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return json;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	@Path("/getBookswithQE")
	public String printBookswithBioPortal(@QueryParam("q")final String queries, @QueryParam("prev")final int prev, @QueryParam("next")final int next, @Context HttpServletRequest request) throws Exception
	{
		System.out.println("Book search begins");
		System.out.println("Requested from " + request.getRemoteAddr());
		Date dt = new Date();
		System.out.println(dt.toString());
		System.out.println("Natural Query: " + queries);
		if( prev == 0 || next ==0)
		{
			String err = "Please specify the query parameters 'prev' and 'next'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		
		if(queries == null)
		{
			String err = "Please specify the query parameters 'q'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
				
		
		Search s = new Search(); 
		Query_Formulator formulator = new Query_Formulator();
		
		
		ArrayList<Topic> topics = formulator.executewithBioPortal(queries);
		

		//System.out.println("Forumated Queries: " + topics.toString());
		String json ;
		String query_text = "";
		if(topics.size() == 0 )
		{
			String err = "The current version of Query Detector detects nothings from " + queries + "at this stage. Begin search with a natural query";
			System.err.println(err);
			//return err;
			query_text = queries;
		}
		else
		{
			ArrayList<String> must_topics = new ArrayList<String>();
			for (int i=0;i<topics.size();i++)
			{
				if(topics.get(i).isMust())
				{
					query_text = topics.get(i).getTitle() + "^10.0 , " + query_text;
					must_topics.add(topics.get(i).getTitle());
				}
			}
			
			for(int i=0;i<topics.size();i++)
			{
					if(!topics.get(i).isMust())
					{
						if(!must_topics.contains(topics.get(i).getTitle() ))
						{
							query_text = topics.get(i).getTitle() + " , " + query_text;
						}
					}
			}
		}
		
		System.out.println("FINAL QUERY TEXT IS: " + query_text);
		long start_search = System.currentTimeMillis();	
		ArrayList<EBook> books = s.search(query_text);
		//System.out.println("NUMBER OF BOOKS IS: " +books.size());
		long end_search = System.currentTimeMillis();	
		
		System.out.println(((end_search-start_search)) + " seconds was executed for search.");
		long end2 = System.currentTimeMillis();	
		SimpleJSON sj = new SimpleJSON();
		
		
		ArrayList<EBook> tmp_books = new ArrayList<EBook>();
		
		int final_prev = prev; 
		int final_next = next;
				
		if (books.size() < final_prev)
		{
			String err = "The total number of the retrieved document is " + books.size() + ", it is less than requested prev: " + prev;
			return err;
		}
		
		if (books.size() < final_next)
		{
			final_next = books.size();
		}	
				
		int range = final_next-final_prev + 1;
		
		int i = 0;
		while(i<range)
		{
			//System.out.println(final_next + " and " + final_prev);
			tmp_books.add(books.get((final_prev-1)+i));
			i++;
		}
		
		int total_retrieved_document = books.size();
		long start = System.currentTimeMillis();
		books.clear();			
		json = sj.writeBookJSON(topics, tmp_books,total_retrieved_document);
		long end = System.currentTimeMillis();	
		
		System.out.println(((end-start)/1000) + " seconds was executed for formatting json.");
		//long end2 = System.currentTimeMillis();	
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return json;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	@Path("/getPaperswithQE")
	public String printPaperswithQE(@QueryParam("q")final String queries, @QueryParam("prev")final int prev, @QueryParam("next")final int next, @Context HttpServletRequest request) throws Exception
	{
		System.out.println("Journal search begins..");
		System.out.println("Requested from " + request.getRemoteAddr());
		Date dt = new Date();
		System.out.println(dt.toString());
		System.out.println("Natural Query: " + queries);
		if( prev == 0 || next ==0)
		{
			String err = "Please specify the query parameters 'prev' and 'next'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		
		if(queries == null)
		{
			String err = "Please specify the query parameters 'q'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		
		
		Search s = new Search(); 
		Query_Formulator formulator = new Query_Formulator();
		ArrayList<Topic> topics = formulator.executewithBioPortal(queries);
		//ArrayList<Topic> topics = new ArrayList<Topic>();
		
		
		
		/*
		for(int i=0;i<topics_tmp.size();i++)
		{
			System.out.println("CURRENT: " +  topics_tmp.get(i).getTitle() + " " + topics_tmp.get(i).getTitle().length());
			String tmp = topics_tmp.get(i).getTitle();
			tmp = tmp.trim();
			
			if(!topics.contains(tmp))
			{
				topics.add(topics_tmp.get(i));
			}
		}
		*/
		for(int i=0;i<topics.size();i++)
		{
			System.out.println("after: " +  topics.get(i).getTitle() + " " + topics.get(i).getTitle().length());
		}
		
		//System.out.println("Initial_query_set: " + topics.toString());
		String query_text = "";
		if(topics.size() == 0 )
		{
			String err = "The current version of Query Detector detects nothings from " + queries + "at this stage. Begin search with a natural query";
			System.err.println(err);
			//return err;
			query_text = queries;
		}
		else
		{			
			for(int i=0;i<topics.size();i++)
			{
				if(topics.get(i).isMust())
				{
					query_text = topics.get(i).getTitle() + "^10.0 , " + query_text;
				}
				else
				{
					query_text = topics.get(i).getTitle() + " , " + query_text;
				}
			}
		}
		//System.out.println("After Query Parsing: " + query_text);
		System.out.println("FINAL QUERY TEXT IS: " + query_text);
		long start_search = System.currentTimeMillis();	
		ArrayList<Journal> journals = s.searchJournal(query_text,next);
		long end_search = System.currentTimeMillis();
		ArrayList<Journal> tmp_journals = new ArrayList<Journal>();
		System.out.println(((end_search-start_search)/1000) + " seconds was executed for search.");
		int final_prev = prev; 
		int final_next = next;
		if (journals.size() < final_prev)
		{
			String err = "The total number of the retrieved document is " + journals.size() + ", it is less than requested prev: " + prev;
			return err;
		}
		
		if (journals.size() < final_next)
		{
			final_next = journals.size();
		}	
		int range = final_next-final_prev + 1;
				
		
		int i = 0;
		while(i<range)
		{
			tmp_journals.add(journals.get((final_prev-1)+i));
			i++;
		}
		
		int total_retrieved_document = journals.size();
		
		journals.clear();
		
		
		
		
		//System.out.println("The requested number of retrieved journals is: " +tmp_journals.size());
		
		SimpleJSON sj = new SimpleJSON();
		start_search = System.currentTimeMillis();		
		String json = sj.writeJournalJSON(topics, tmp_journals,total_retrieved_document);
		//String json = sj.writeJSON(topics, journals);
		end_search = System.currentTimeMillis();	
		System.out.println(((end_search-start_search)/1000) + " seconds was executed for JSON formatting.");
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return json;		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	@Path("/getAllPapers")
	public String printAllPapers(@QueryParam("q")final String queries, @Context HttpServletRequest request) throws Exception
	{
		System.out.println("Journal search begins..");
		System.out.println("Requested from " + request.getRemoteAddr());
		Date dt = new Date();
		System.out.println(dt.toString());
		if(queries == null)
		{
			String err = "Please specify the query parameters 'q'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		Search s = new Search(); 
		Query_Formulator formulator = new Query_Formulator();
		ArrayList<Topic> topics = formulator.execute(queries);
		String query_text = "";
		
		if(topics.size() == 0 )
		{
			String err = "The current version of Query Detector detects nothings from " + queries + "at this stage. Begin search with a natural query";
			System.err.println(err);
			//return err;
			query_text = queries;
		}
		else
		{
			System.out.println("Initial_query_set: " + topics.toString());
			
			for(int i=0;i<topics.size();i++)
			{
				if(topics.get(i).isMust())
				{
					query_text = topics.get(i).getTitle() + "^10.0 , " + query_text;
				}
				else
				{
					query_text = topics.get(i).getTitle() + " , " + query_text;
				}
			}
		}
		System.out.println("After Query Parsing: " + query_text);
		ArrayList<Journal> journals = s.searchJournal(query_text,1000);
		int total_retrieved_document = journals.size();
		
		System.out.println("The number of retrieved journals is: " +journals.size());
		
		SimpleJSON sj = new SimpleJSON();
		String json = sj.writeJournalJSON(topics, journals,total_retrieved_document);
		//String json = sj.writeJSON(topics, journals);
		
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return json;		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	@Path("/getPapers")
	public String printPapers(@QueryParam("q")final String queries, @QueryParam("prev")final int prev, @QueryParam("next")final int next, @Context HttpServletRequest request) throws Exception
	{
		System.out.println("Journal search begins..");
		System.out.println("Requested from " + request.getRemoteAddr());
		Date dt = new Date();
		System.out.println(dt.toString());
		System.out.println("Natural Query: " + queries);
		if( prev == 0 || next ==0)
		{
			String err = "Please specify the query parameters 'prev' and 'next'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		
		if(queries == null)
		{
			String err = "Please specify the query parameters 'q'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		
		
		Search s = new Search(); 
		Query_Formulator formulator = new Query_Formulator();
		ArrayList<Topic> topics = formulator.execute(queries);
		//ArrayList<Topic> topics = new ArrayList<Topic>();
		
		
		
		/*
		for(int i=0;i<topics_tmp.size();i++)
		{
			System.out.println("CURRENT: " +  topics_tmp.get(i).getTitle() + " " + topics_tmp.get(i).getTitle().length());
			String tmp = topics_tmp.get(i).getTitle();
			tmp = tmp.trim();
			
			if(!topics.contains(tmp))
			{
				topics.add(topics_tmp.get(i));
			}
		}
		*/
		for(int i=0;i<topics.size();i++)
		{
			System.out.println("after: " +  topics.get(i).getTitle() + " " + topics.get(i).getTitle().length());
		}
		
		//System.out.println("Initial_query_set: " + topics.toString());
		String query_text = "";
		if(topics.size() == 0 )
		{
			String err = "The current version of Query Detector detects nothings from " + queries + "at this stage. Begin search with a natural query";
			System.err.println(err);
			//return err;
			query_text = queries;
		}
		else
		{			
			for(int i=0;i<topics.size();i++)
			{
				if(topics.get(i).isMust())
				{
					query_text = topics.get(i).getTitle() + "^10.0 , " + query_text;
				}
				else
				{
					query_text = topics.get(i).getTitle() + " , " + query_text;
				}
			}
		}
		//System.out.println("After Query Parsing: " + query_text);
		System.out.println("FINAL QUERY TEXT IS: " + query_text);
		long start_search = System.currentTimeMillis();	
		ArrayList<Journal> journals = s.searchJournal(query_text,next);
		long end_search = System.currentTimeMillis();
		ArrayList<Journal> tmp_journals = new ArrayList<Journal>();
		System.out.println(((end_search-start_search)/1000) + " seconds was executed for search.");
		int final_prev = prev; 
		int final_next = next;
		if (journals.size() < final_prev)
		{
			String err = "The total number of the retrieved document is " + journals.size() + ", it is less than requested prev: " + prev;
			return err;
		}
		
		if (journals.size() < final_next)
		{
			final_next = journals.size();
		}	
		int range = final_next-final_prev + 1;
				
		
		int i = 0;
		while(i<range)
		{
			tmp_journals.add(journals.get((final_prev-1)+i));
			i++;
		}
		
		int total_retrieved_document = journals.size();
		
		journals.clear();
		
		
		
		
		//System.out.println("The requested number of retrieved journals is: " +tmp_journals.size());
		
		SimpleJSON sj = new SimpleJSON();
		start_search = System.currentTimeMillis();		
		String json = sj.writeJournalJSON(topics, tmp_journals,total_retrieved_document);
		//String json = sj.writeJSON(topics, journals);
		end_search = System.currentTimeMillis();	
		System.out.println(((end_search-start_search)/1000) + " seconds was executed for JSON formatting.");
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return json;		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	@Path("/getBooksbyCLI")
	public String printBooksbyCLI(@QueryParam("q")final String queries, @QueryParam("prev")final int prev, @QueryParam("next")final int next, @QueryParam("rel")final String relation, @Context HttpServletRequest request) throws Exception
	{
		System.out.println("Book search begins");
		System.out.println("Requested from " + request.getRemoteAddr());
		Date dt = new Date();
		System.out.println(dt.toString());
		System.out.println("Natural Query: " + queries);
		if( prev == 0 || next ==0)
		{
			String err = "Please specify the query parameters 'prev' and 'next'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
		
		if(queries == null)
		{
			String err = "Please specify the query parameters 'q'";
			err = err + "\n + the usage is as follows: " ;
			err = err + "\n ekp.kaist.ac.kr/apis/getBooks?q=알부민이 증가하였습니다&prev=10&next=20";
			return err;
		}
				
		
		Search s = new Search(); 
		Query_Formulator formulator = new Query_Formulator();
		ArrayList<Topic> topics = formulator.execute(queries);
		//System.out.println("Forumated Queries: " + topics.toString());
		String json ;
		String query_text = "";
		if(topics.size() == 0 )
		{
			String err = "The current version of Query Detector detects nothings from " + queries + "at this stage. Begin search with a natural query";
			System.err.println(err);
			//return err;
			query_text = queries;
		}
		else
		{
			ArrayList<String> must_topics = new ArrayList<String>();
			for (int i=0;i<topics.size();i++)
			{
				if(topics.get(i).isMust())
				{
					query_text = topics.get(i).getTitle() + "^10.0 , " + query_text;
					must_topics.add(topics.get(i).getTitle());
				}
			}
			
			for(int i=0;i<topics.size();i++)
			{
					if(!topics.get(i).isMust())
					{
						//query_text = topics.get(i).getTitle() + "^10.0 , " + query_text;
						if(!must_topics.contains(topics.get(i).getTitle() ))
						{
							query_text = topics.get(i).getTitle() + " , " + query_text;
						}
					}
					//else
					//{
						
						//query_text = topics.get(i).getTitle() + " , " + query_text;
					//}
			}
		}
		System.out.println("FINAL QUERY TEXT IS: " + query_text);
		long start_search = System.currentTimeMillis();	
		ArrayList<EBook> books = s.search(query_text);
		//System.out.println("NUMBER OF BOOKS IS: " +books.size());
		long end_search = System.currentTimeMillis();	
		
		System.out.println(((end_search-start_search)/1000) + " seconds was executed for search.");
		long end2 = System.currentTimeMillis();	
		SimpleJSON sj = new SimpleJSON();
		
		
		ArrayList<EBook> tmp_books = new ArrayList<EBook>();
		
		int final_prev = prev; 
		int final_next = next;
				
		if (books.size() < final_prev)
		{
			String err = "The total number of the retrieved document is " + books.size() + ", it is less than requested prev: " + prev;
			return err;
		}
		
		if (books.size() < final_next)
		{
			final_next = books.size();
		}	
				
		int range = final_next-final_prev + 1;
		
		int i = 0;
		while(i<range)
		{
			//System.out.println(final_next + " and " + final_prev);
			tmp_books.add(books.get((final_prev-1)+i));
			i++;
		}
		
		
		
		int total_retrieved_document = books.size();
		long start = System.currentTimeMillis();
		books.clear();
		ArrayList<EBook> ordered_book_by_cli= sj.sortbyCLI(tmp_books, relation);
		json = sj.writeBookJSON(topics, ordered_book_by_cli,total_retrieved_document);
		long end = System.currentTimeMillis();	
		
		System.out.println(((end-start)/1000) + " seconds was executed for formatting json.");
		//long end2 = System.currentTimeMillis();	
		//this.sortbyCLI("설명");
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		return json;
	}	
	
	@PUT
	@Path("/putClicksonDoc/{doc_id}")
	public void addClick(@Context HttpServletRequest request, @PathParam("doc_id") final int doc_id) throws SQLException
	{
		System.out.println(doc_id);
		JournalDao dao = new JournalDao();
		Connection conn = dao.getEKPConnection();
		
		dao.updateClickInfo(conn, doc_id);
		
		dao.closeConnection(conn);
		//String s = doc_id + " increseas.";
		//System.out.println(s);
		this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
		//return Response.status(Status.OK).build();
		//return s;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	@Path("/getdetails")
	public String printBooks_before_details(@QueryParam("id")final String book_id,@QueryParam("isbn")final String isbn,@Context HttpServletRequest request) throws Exception
	{
	
		
	int book_pointer =0;
	if (isbn.equals("978-1437709742")){
		book_pointer=1;
	}
	else if (isbn.equals("978-0071766258")){
		book_pointer=2;
	}
	else{
		book_pointer=3;
	}
	 
	
	int id = Integer.parseInt(book_id);
	
	EBookInfo info = new EBookInfo();
	EBDao dao = new EBDao();
	Connection conn = dao.getConnection();
	
	double score = 0;
	String book_source = info.getTitle(book_pointer);
	String book_author = info.getAuthor(book_pointer);
	String book_year = info.getYear(book_pointer);
	
	String book_image_url = dao.getBookImageURL(book_pointer, id, conn);
	EBook current_paragraph = dao.getParagraphDetail(conn, book_pointer, id);
	

	SimpleJSON sj = new SimpleJSON();
	JSONObject book = new JSONObject();
	
	book.put("score",score);
	book.put("book_id",book_id);
	book.put("book_source", book_source);
	book.put("book_author",book_author);
	book.put("book_year", book_year);
	book.put("book_isbn", isbn);
	book.put("book_chapter", current_paragraph.getChap_num());
	book.put("book_part","Not available");
	book.put("book_contents", current_paragraph.getContents());
	book.put("book_image_url",book_image_url);
	book.put("book_previous_contents","Not available");
	book.put("book_next_contents", "Not available");
	/*
	if(scoreDoc.length == 0)
	{
		book.put("score",score);
		book.put("book_id",book_id);
		book.put("book_source", book_source);
		book.put("book_author",book_author);
		book.put("book_year", book_year);
		book.put("book_isbn", isbn);
		book.put("book_chapter", current_paragraph.getChap_num());
		book.put("book_part",current_paragraph.getPart_num());
		book.put("book_contents", current_paragraph.getContents());
		book.put("book_image_url",book_image_url);
		book.put("book_previous_contents","Not available");
		book.put("book_next_contents", "Not available");
	}
	else
	{
		doc = searcher.doc(scoreDoc[0].doc);
		double score = scoreDoc[0].score;
		score = Double.parseDouble(String.format("%.4f", score));
		 
		 
		book.put("score",score);
		book.put("book_id",book_id);
		book.put("book_source", doc.get("source_name"));
		book.put("book_author",doc.get("author"));
		book.put("book_year", doc.get("year"));
		book.put("book_isbn", isbn);
		book.put("book_chapter", doc.get("chap_num"));
		book.put("book_part",doc.get("part_num"));
		book.put("book_contents", doc.get("contents"));
		book.put("book_image_url",doc.get("image_url"));
		book.put("book_previous_contents",doc.get("previous_contents"));
		book.put("book_next_contents", doc.get("next_contents"));
	}
		 
	//String json = sj.writeJSON(topics, journals);
	searcher.close();
	*/
	conn.close();
	
	this.makeLog(request.getRequestURL().toString(), request.getRemoteAddr());
	return book.toJSONString();
	}
	
	
}
