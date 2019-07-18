package kr.ac.kirc.ekp.tta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.ac.kirc.ekp.bean.SearchResult;
import kr.ac.kirc.ekp.dao.ExperimentDao;

public class generateCSV {

	public static void main(String[] args) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		
		 try {
		      ////////////////////////////////////////////////////////////////
		      BufferedWriter out = new BufferedWriter(new FileWriter("C://relevance_judgement.csv"));
		      
			  String header = "query_id,passage_id,isbn,rel";
			  out.write(header);
			  out.newLine();
		      
			  ExperimentDao dao = new ExperimentDao();
			  Connection conn = dao.getEKPConnection();
			  for (int k=15;k<45;k++)
			  {
				  int query_id = k;
				  ArrayList<SearchResult> search_results = new ArrayList<SearchResult>();
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
				
				String ISBN_1 = "978-1437709742";
				String ISBN_2 = "978-0071766258";
				String ISBN_3 = "978-1455726134";
				ArrayList<String> out_result_ISBN1 = new ArrayList<String>();
				ArrayList<String> out_result_ISBN2 = new ArrayList<String>();
				ArrayList<String> out_result_ISBN3 = new ArrayList<String>();
				
				for(int i=0;i<search_results.size();i++)
				{
					String pageNum = "0";
					if(i<10)
					{
						pageNum = "1";
					}
										
					
					String relevance_judgement = dao.isJudgedforTTA(conn, query_id, search_results.get(i).getNumber(), search_results.get(i).getIsbn(), pageNum);
					String s = query_id + "," + search_results.get(i).getNumber() +"," +search_results.get(i).getIsbn() +","+relevance_judgement;
					if(search_results.get(i).getIsbn().equals(ISBN_1))
					{
						out_result_ISBN1.add(s);
					}
					else if(search_results.get(i).getIsbn().equals(ISBN_2))
					{
						out_result_ISBN2.add(s);
					}
					else if(search_results.get(i).getIsbn().equals(ISBN_3))
					{
						out_result_ISBN3.add(s);
					}
					else
					{
						System.out.println("ERROR ERROR ERROR");
						System.exit(0);
					}
					
				}
				
				System.out.println("Total number of ISBNs: " + (out_result_ISBN1.size() + out_result_ISBN2.size() +out_result_ISBN3.size() ));
				 for(int i=0;i<out_result_ISBN1.size();i++)
				 {
					 out.write(out_result_ISBN1.get(i));
					 out.newLine();
				 }
				 for(int i=0;i<out_result_ISBN2.size();i++)
				 {
					 out.write(out_result_ISBN2.get(i));
					 out.newLine();
				 }
				 for(int i=0;i<out_result_ISBN3.size();i++)
				 {
					 out.write(out_result_ISBN3.get(i));
					 out.newLine();
				 }
				
				 
				
				
				System.out.println("writing done for " + query_id);
			  }
		      
			  out.close();
		      ////////////////////////////////////////////////////////////////
		    } catch (IOException e) {
		        System.err.println(e); // 에러가 있다면 메시지 출력
		        System.exit(1);
		    }
		
		
	}

}
