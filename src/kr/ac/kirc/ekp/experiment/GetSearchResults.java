package kr.ac.kirc.ekp.experiment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.Query;
import kr.ac.kirc.ekp.bean.SearchResult;
import kr.ac.kirc.ekp.bean.Topic;
import kr.ac.kirc.ekp.core.search.Query_Formulator;
import kr.ac.kirc.ekp.core.search.Search;
import kr.ac.kirc.ekp.dao.ExperimentDao;

public class GetSearchResults {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		GetSearchResults gsr = new GetSearchResults();
		gsr.run();
		//gsr.runToMakeJSONofTop200ListsfromFourMethod();
	}
	public void run() throws Exception
	{
		ExperimentDao dao = new ExperimentDao();
		Connection conn = dao.getEKPConnection();
		ArrayList<Query> queries = dao.getQueryList(conn);
		Query_Formulator qf = new Query_Formulator();
		Search search = new Search();
		
		//int method_num = 1; //baseline 1 (no QE)
		int method_num = 2; //baseline 2 (QE with Synonyns)
		
		for (int i=0;i<queries.size();i++)
		{
			String s = queries.get(i).getQuery();
			s ="Although not approved for screening for hepatocellular carcinoma in the United States, AFP has been used to screen for primary hepatocellular carcinoma in China because of the high incidence of liver cancer in that country. The diagnosis of ovarian cancer has traditionally relied on imaging and discovery at surgery (e.g., exploratory laparotomy). However, in most cases, at the time of detection, the tumor has advanced to a stage at which the possibility of cure is low. The feasibility of screening for ovarian cancer in women by measuring serum CA 125 is being investigated.";
			System.out.println("s is " + s);
			ArrayList<Topic> topics = qf.executewithBioPortal(s);
			ArrayList<String> must_topic = new ArrayList<String>();
			ArrayList<String> sub_topic = new ArrayList<String>();
			for(int j=0;j<topics.size();j++)
			{
				if(topics.get(j).isMust())
				{
					must_topic.add(topics.get(j).getTitle());
				}
				else
				{
					sub_topic.add(topics.get(j).getTitle());
				}
			}
			System.out.println(s);
			System.out.println(must_topic.size() + " : " + must_topic.toString());
			
			String final_query = this.getBaselineTwoQuery(must_topic, sub_topic);
			//System.out.println(this.getBaselineTwoQuery(must_topic, sub_topic));
			System.out.println(s +" has been transformed to" + final_query);
			ArrayList<EBook> retrieved_passages = search.search(final_query);
			//ArrayList<EBook> retrieved_passages = search.search(final_query);
			System.err.println(final_query + " made " + retrieved_passages.size() + " is retreived...");
			//dao.writeSearchResults(conn, queries.get(i).getQuery_id(), retrieved_passages,method_num);
			System.out.println("insert done for "+queries.get(i).getQuery());
			
		}		
	}
	public void runToMakeJSONofTop200ListsfromFourMethod()
	{
		ExperimentDao dao = new ExperimentDao();
		Connection conn = dao.getEKPConnection();
		
		ArrayList<Query> query_list;
		try {
			query_list = dao.getQueryList(conn);
			for(int k=0;k<query_list.size();k++)
			{
				ArrayList<SearchResult> search_results = new ArrayList<SearchResult>();
				ArrayList<String> json_results = dao.getSearchResult(conn, query_list.get(k).getQuery_id());
				for(int i=0;i<json_results.size();i++)
				{
					JSONParser parser = new JSONParser();
					JSONArray passages = (JSONArray)parser.parse(json_results.get(i));
					Iterator iterator = passages.iterator();
					int size = 1;
					while(iterator.hasNext() && size <=50)
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
							 size++;
						 }
						 
					 }
					System.out.println(query_list.get(k).getQuery_id() + ": The number of retrieved passages is: " + search_results.size());
				}
				System.out.println("BEFORE shuffle");
				for(int p=0;p<search_results.size();p++)
				{
					
					System.out.print(search_results.get(p).getMethod_num() + " , ");
				}
				System.out.println();
				Collections.shuffle(search_results);
				System.out.println("After shuffle");
				for(int p=0;p<search_results.size();p++)
				{
					
					System.out.print(search_results.get(p).getMethod_num() + " , ");
				}
				System.out.println();
				dao.write200SearchResultsforAnnotation(conn, query_list.get(k).getQuery_id(), search_results);
				//System.exit(0);
			}
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
	}
	public String getBaselineTwoQuery(ArrayList<String> must_topic, ArrayList<String> sub_topic)
	{
		String final_query = "";
		for(int j=0;j<must_topic.size();j++)
		{
			String term = must_topic.get(j);
			if(term.matches("[a-zA-Z0-9]*"))
			{
				final_query = final_query + term+"^10 ";
			}
		}
		for(int j=0;j<sub_topic.size();j++)
		{
			String term = sub_topic.get(j);
			if(term.matches("[a-zA-Z0-9]*"))
			{
				final_query = final_query + term+"^5 ";
			}
		} 
		return final_query;
	}
	public String getBaselineOneQuery(ArrayList<String> must_topic, ArrayList<String> sub_topic)
	{
		String final_query = "";
		for(int j=0;j<must_topic.size();j++)
		{
			String term = must_topic.get(j);
			if(term.matches("[a-zA-Z0-9]*"))
			{
				final_query = final_query + term+"^10 ";
			}
		}
		return final_query;
	}
	
	
	
	
	

}
