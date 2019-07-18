package kr.ac.kirc.ekp.experiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.ac.kirc.ekp.bean.Relevance;
import kr.ac.kirc.ekp.bean.RelevancePair;
import kr.ac.kirc.ekp.bean.SearchResult;
import kr.ac.kirc.ekp.dao.ExperimentDao;
import kr.ac.kirc.ekp.dao.userDao;

public class ExperimentRun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExperimentRun ER = new ExperimentRun();
		int degree = 10;
		int query_id=18;
		/*
		ArrayList<Integer> query_ids = new ArrayList<Integer>();
		query_ids.add(new Integer(18));
		query_ids.add(new Integer(28));
		query_ids.add(new Integer(38));
		query_ids.add(new Integer(15));
		query_ids.add(new Integer(25));
		query_ids.add(new Integer(35));
		query_ids.add(new Integer(39));
		query_ids.add(new Integer(40));
		query_ids.add(new Integer(41));
		query_ids.add(new Integer(23));
		query_ids.add(new Integer(33));
		query_ids.add(new Integer(43));
		*/
		//ER.runKappa("rlatngus2480", "laftworld");
		//ER.runKappa("cats409", "elizabeth");
		//ER.runKappa("ggee2244", "dreaming");
		
		System.out.println("Experiment begins.");
		for(int i=1;i<5;i++)
		{
			System.out.println("METHOD " + (i+1) + " goes >>>>>>>>>>>>");
			
			ER.runStrictPrecision("rlatngus2480", "laftworld", i);
			ER.runStrictPrecision("yung5236", "reon1001", i);
			ER.runStrictPrecision("sinae373", "oneweek0701", i);			
			ER.runStrictPrecision("cats409", "elizabeth", i);
			ER.runStrictPrecision("jincristal11", "01234", i);
			ER.runStrictPrecision("yesom9886", "mkm6725", i);
			ER.runStrictPrecision("0minij0", "junu", i);
			ER.runStrictPrecision("gjtnwl98", "kyjk3", i);
			ER.runStrictPrecision("ggee2244", "dreaming", i);
			ER.runStrictPrecision("gjh408", "5041398", i);
			
			
		}
		System.out.println("Experiment ends.");
		/*
		 * 
		 * 
		ArrayList<String> users = new ArrayList<String>();
		users.add("rlatngus2480");
		users.add("cats409");
		users.add("ggee2244");
		users.add("jincristal11");
		users.add("laftworld");
		users.add("acoustic_u");
		users.add("elizabeth");
		users.add("junu");
		users.add("kyjk3");
		users.add("dreaming");
		

		//int method_num=2;
		for(int i =0;i<users.size();i++)
		{
			System.out.println("begin for query " + users.get(i));
			ER.runPrecision(users.get(i), new Integer(1));
			ER.runPrecision(users.get(i), new Integer(2));
			ER.runPrecision(users.get(i), new Integer(3));
			ER.runPrecision(users.get(i), new Integer(4));
			System.out.println("done for query " + users.get(i));
		}
		 */

	}
	
	public void runKappa(String user_a,String user_b)
	{
		String path = "C:/kappa_new.csv";
		
		ExperimentDao dao = new ExperimentDao();
		Connection conn = dao.getEKPConnection();
		try{
			ArrayList<RelevancePair> pairs = dao.getPairofRelevance(conn, user_a, user_b);
			BufferedWriter out = new BufferedWriter(new FileWriter(path,true));
			
			for(int i=0;i<pairs.size();i++)
			{
				int passage_id = pairs.get(i).getPassage_id();
				String isbn =pairs.get(i).getIsbn();
				int query_id = pairs.get(i).getQuery_id();
				int relevance_a = pairs.get(i).getRelevance_a();
				int relevance_b = pairs.get(i).getRelevance_b();
				
				String output = user_a+"_"+user_b + passage_id + "," + isbn + "," + query_id + "," + relevance_a +"," + relevance_b;
				out.write(output);
				out.newLine();
				
			}
			out.close();
			
		}catch(Exception e)
		{
			
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public double runStrictPrecision(String user_a,String user_b, int method_num)
	{
		
		String path = "C:/result171013.csv";
		
		userDao u_dao = new userDao();
		Connection u_conn = u_dao.getConnection();
		
		
		
		ExperimentDao dao = new ExperimentDao();
		Connection conn = dao.getEKPConnection();
		double score=0;
		try {
			String task_id = u_dao.loginReturnsTaskIdswithoutPW(u_conn, user_a); 
			String[] query_ids = task_id.split("&");
			for(int k=0;k<query_ids.length;k++)
			{
				int query_id = Integer.parseInt(query_ids[k]);
				System.out.println("Method num: " + method_num + " Begin for " + user_a + " and " + user_b + " on query id=" + query_id);
				String search_result = dao.getSearchResultforEachMethod(conn, query_id, method_num);
				ArrayList<SearchResult> parsed_results = this.parseJSONFormatofSearchResult(search_result);
				BufferedWriter out = new BufferedWriter(new FileWriter(path,true));
				//ArrayList<Relevance> relevance_results = dao.getRelevanceJudgementbyQueryIdandUser(conn, query_id, user);
				int count_dr = 0;
				int count_pr = 0;
				int count_nr = 0;
				ArrayList<Integer> relevance_list = new ArrayList<Integer>();
				//int r_prac = dao.getNumberofRelevanceforQuerybyUser(conn, query_id, user);
				int r_prac = dao.getStrictNumberofRelevance(conn, user_a, user_b,query_id);
				System.out.println("r_prac: " + r_prac);
				int count = 0;
				
				double pre1 = 0;
				double pre2 = 0;
				double pre3 = 0;
				double pre4 = 0; 
				double pre5 = 0;
				double pre6 = 0;
				double pre7 = 0;
				double pre8 = 0;
				double pre9 = 0;
				
			
				
				
				double pre10 = 0;
				double pre20 = 0;
				double pre30 = 0;
				double pre40 = 0;
				double pre50 = 0;
				
				
				int n=0;
				int index = 0;
				/*
				while(n<r_prac && index <parsed_results.size()){
					String isbn = parsed_results.get(index).getIsbn();
					int passage_id = parsed_results.get(index).getNumber();
					//Relevance relevance_for_this_passage = dao.getRelevanceJudgementbyQueryIdandISBNandPassage_id(conn, query_id, isbn, passage_id);
					Relevance relevance_for_this_passage = dao.getStrictRelevanceJudgementbyQueryIdandISBNandPassage_id(conn, query_id, isbn, passage_id, user_a, user_b);
					int relevance_score = relevance_for_this_passage.getRelevance();
					//System.out.println(relevance_score);
					if(relevance_score !=0)
					{
						//System.out.println("n value: " + n);
						if(relevance_score == 2 ||relevance_score == 3 )
						{
							count++;
						}
						if(relevance_score == 1)
						{
							count_nr = count_nr+1;
						}
						else if (relevance_score ==2)
						{
							count_pr = count_pr +1;
						}
						else if(relevance_score ==3)
						{
							count_dr = count_dr + 1;
						}
						
						if(n == 9)
						{
							pre10=count;
						}
						if(n == 19)
						{
							pre20 = count;
						}
						if(n ==29)
						{
							pre30 = count;
						}
						if(n ==39)
						{
							pre40 = count;
						}
						if(n ==49)
						{
							pre50 = count;
						}
						
						if(n<=49)
						{
							//System.out.println(relevance_score);
							relevance_list.add(relevance_score);
						}
						n++;
					}
					index++;
				}
				if (relevance_list.size() <= r_prac)
				{
					int old_r_prac = r_prac;
					r_prac = count;
					System.out.println("R prac has been changed from " + old_r_prac + " to " + count);
				}
				*/
				int max = 50;
				if(r_prac >= max)
				{
					max = r_prac;
					System.out.println("The number of relevance is greater than 50, so the max has changed to " + max);
				}
				for(int i=0;i<max;i++)
				{
					String isbn = parsed_results.get(i).getIsbn();
					int passage_id = parsed_results.get(i).getNumber();
					//Relevance relevance_for_this_passage = dao.getRelevanceJudgementbyQueryIdandISBNandPassage_id(conn, query_id, isbn, passage_id);
					Relevance relevance_for_this_passage = dao.getStrictRelevanceJudgementbyQueryIdandISBNandPassage_id(conn, query_id, isbn, passage_id, user_a, user_b);
					int relevance_score = relevance_for_this_passage.getRelevance();
					
					if(relevance_score == 2 ||relevance_score == 3 )
					{
						count++;
					}
					if(relevance_score == 1)
					{
						count_nr = count_nr+1;
					}
					else if (relevance_score ==2)
					{
						count_pr = count_pr +1;
					}
					else if(relevance_score ==3)
					{
						count_dr = count_dr + 1;
					}
					if(i==0)
					{
						pre1=(double)count/1;
					}
					if(i==1)
					{
						pre2=(double)count/2;
					}
					if(i==2)
					{
						pre3=(double)count/3;
					}
					if(i==3)
					{
						pre4=(double)count/4;
					}
					if(i==4)
					{
						pre5=(double)count/5;
					}
					if(i==5)
					{
						pre6=(double)count/6;
					}
					if(i==6)
					{
						pre7=(double)count/7;
					}
					if(i==7)
					{
						pre8=(double)count/8;
					}
					if(i==8)
					{
						pre9=(double)count/9;
					}

					if(i == 9)
					{
						pre10=(double)count/10;
					}
					if(i == 19)
					{
						pre20 = (double)count/20;
					}
					if(i ==29)
					{
						pre30 = (double)count/30;
					}
					if(i ==39)
					{
						pre40 = (double)count/40;
					}
					if(i ==49)
					{
						pre50 = (double)count/50;
					}
					
					if(i<=49)
					{
						//System.out.println(relevance_score);
						relevance_list.add(relevance_score);
					}
				}	
				
				System.out.println("why? " + relevance_list.toString());
				ArrayList<Integer> relevance_list_for_ndcg = new ArrayList<Integer>();
				relevance_list_for_ndcg.addAll(0, relevance_list);
				
				ArrayList<Double> ndcgs = this.calculateNDCG(relevance_list_for_ndcg);
				String ndcg_output=ndcgs.get(0) + "";
				for(int i=1;i<ndcgs.size();i++)
				{
					double ndcg = ndcgs.get(i);
					ndcg_output = ndcg_output + "," +ndcg;
				}
				System.out.println(ndcg_output);
				System.out.println("Original: " + relevance_list.toString());
				System.out.println(count_dr + " , " + count_pr + " , " + count_nr);
				
				double mrr = 0;
				for (int i=0;i<relevance_list.size();i++)
				{
					if(relevance_list.get(i) == 2 ||relevance_list.get(i) == 3 )
					{
						mrr = (i+1);
						break;
					}
				}
				mrr = 1/mrr;
				String user = user_a + "_"+user_b;
				String output = user+"," + query_id + "," + method_num + "," + pre1 + "," + pre2  +"," + pre3 + ","+pre4 +"," +pre5 + "," +pre6 + "," 
								+pre7+","+pre8+"," + pre9 + "," + pre10 + "," + pre20 + "," +pre30 + "," + pre40 + "," +pre50 + "," + count+"," +r_prac + " , "+mrr+"," + ndcg_output;
				out.write(output);
				out.newLine();
				out.close();				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
				u_conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return score;	
	}
	public double runPrecision(String user, int method_num)
	{
		
		String path = "C:/all_result.csv";
		
		userDao u_dao = new userDao();
		Connection u_conn = u_dao.getConnection();
		
		
		
		ExperimentDao dao = new ExperimentDao();
		Connection conn = dao.getEKPConnection();
		double score=0;
		try {
			String task_id = u_dao.loginReturnsTaskIdswithoutPW(u_conn, user); 
			String[] query_ids = task_id.split("&");
			for(int k=0;k<query_ids.length;k++)
			{
				int query_id = Integer.parseInt(query_ids[k]);
				String search_result = dao.getSearchResultforEachMethod(conn, query_id, method_num);
				ArrayList<SearchResult> parsed_results = this.parseJSONFormatofSearchResult(search_result);
				BufferedWriter out = new BufferedWriter(new FileWriter(path,true));
				//ArrayList<Relevance> relevance_results = dao.getRelevanceJudgementbyQueryIdandUser(conn, query_id, user);
				int count_dr = 0;
				int count_pr = 0;
				int count_nr = 0;
				ArrayList<Integer> relevance_list = new ArrayList<Integer>();
				int r_prac = dao.getNumberofRelevanceforQuerybyUser(conn, query_id, user);
				
				
				int count = 0;
				int pre10 = 0;
				int pre20 = 0;
				int pre30 = 0;
				int pre40 = 0;
				int pre50 = 0;
								
				for(int i=0;i<r_prac;i++)
				{
					String isbn = parsed_results.get(i).getIsbn();
					int passage_id = parsed_results.get(i).getNumber();
					Relevance relevance_for_this_passage = dao.getRelevanceJudgementbyQueryIdandISBNandPassage_id(conn, query_id, isbn, passage_id);
					int relevance_score = relevance_for_this_passage.getRelevance();
					
					if(relevance_score == 2 ||relevance_score == 3 )
					{
						count++;
					}
					if(relevance_score == 1)
					{
						count_nr = count_nr+1;
					}
					else if (relevance_score ==2)
					{
						count_pr = count_pr +1;
					}
					else if(relevance_score ==3)
					{
						count_dr = count_dr + 1;
					}
					
					if(i == 9)
					{
						pre10=count;
					}
					if(i == 19)
					{
						pre20 = count;
					}
					if(i ==29)
					{
						pre30 = count;
					}
					if(i ==39)
					{
						pre40 = count;
					}
					if(i ==49)
					{
						pre50 = count;
					}
					
					if(i<=49)
					{
						relevance_list.add(relevance_score);
					}
				}			
				ArrayList<Integer> relevance_list_for_ndcg = new ArrayList<Integer>();
				relevance_list_for_ndcg.addAll(0, relevance_list);
				
				ArrayList<Double> ndcgs = this.calculateNDCG(relevance_list_for_ndcg);
				System.out.println("Original: " + relevance_list);
				System.out.println(count_dr + " , " + count_pr + " , " + count_nr);
				

				for(int i=0;i<relevance_list.size();i++)
				{
					
				}
				double mrr = 0;
				for (int i=0;i<relevance_list.size();i++)
				{
					if(relevance_list.get(i) == 2 ||relevance_list.get(i) == 3 )
					{
						mrr = (i+1);
						break;
					}
				}
				mrr = 1/mrr;
				String output = user+"," + query_id + "," + method_num + "," + pre10 + "," + pre20 + "," +pre30 + "," + pre40 + "," +pre50 + "," + count+"," +r_prac + " , "+mrr+","+ndcgs.get(0)+","+ndcgs.get(1)+","+ndcgs.get(2)+","+ndcgs.get(3)+","+ndcgs.get(4);
				out.write(output);
				out.newLine();
				out.close();				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
				u_conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return score;	
	}
	
	public ArrayList<Double> calculateNDCG(ArrayList<Integer> relevance_list)
	{
		double dcg = 0;
		//System.out.println("Original" + relevance_list.toString());
		ArrayList<Integer> ideal_list = new ArrayList<Integer>();
		
		ideal_list.addAll(0, relevance_list);
		//ArrayList<Integer> new_ideal_list = new ArrayList<Integer>();
		Collections.sort(ideal_list,Collections.reverseOrder());
		System.out.println("Sorted:" + ideal_list.toString());
		System.out.println("Original:" + relevance_list.toString());
		for(int i=0;i<ideal_list.size();i++)
		{
			
		}
		/*
		for(int i=0;i<ideal_list.size();i++)
		{
			if(ideal_list.get(i) == 2 ||ideal_list.get(i) == 3 )
			{
				new_ideal_list.add(new Integer(3));
			}
			else
			{
				new_ideal_list.add(new Integer(0));
			}
		}
		*/
		//System.out.println(new_ideal_list.toString());

		ArrayList<Double> ndcgs = new ArrayList<Double>();
		
		double ndcg_1 = 0;
		double ndcg_2 = 0;
		double ndcg_3 = 0;
		double ndcg_4 = 0; 
		double ndcg_5 = 0;
		double ndcg_6 = 0;
		double ndcg_7 = 0;
		double ndcg_8 = 0;
		double ndcg_9 = 0;
		
		double ndcg_10 = 0;
		double ndcg_20 = 0;
		double ndcg_30 = 0;
		double ndcg_40 = 0;
		double ndcg_50 = 0;
		for(int i =0;i<relevance_list.size();i++)
		{
			double rel_at_i = 0;
			double base = Math.log10((i+1)+1)/Math.log10(2);
			rel_at_i=relevance_list.get(i);
			/*
			if(relevance_list.get(i) == 2 ||relevance_list.get(i) == 3 )
			{
				rel_at_i = 2;
			}
			*/
			double dcg_at_i = rel_at_i/base;
			dcg = dcg + dcg_at_i;
			if(i==0)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_1 = dcg/idcg;
			}
			if(i==1)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_2 = dcg/idcg;
			}
			if(i==2)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_3 = dcg/idcg;
			}
			if(i==3)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_4 = dcg/idcg;
			}
			if(i==4)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_5 = dcg/idcg;
			}
			if(i==5)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_6 = dcg/idcg;
			}
			if(i==6)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_7 = dcg/idcg;
			}
			if(i==7)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_8 = dcg/idcg;
			}
			if(i==8)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_9 = dcg/idcg;
			}
			
			
			
			if(i==9)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_10 = dcg/idcg;
			}
			if(i==19)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_20 = dcg/idcg;
			}
			if(i==29)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_30 = dcg/idcg;
			}
			if(i==39)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_40 = dcg/idcg;
			}
			if(i==49)
			{
				double idcg = 0;
				for(int j=0;j<=i;j++)
				{
					idcg = idcg +  ideal_list.get(j);
					 
				}
				ndcg_50 = dcg/idcg;
			}
		}
		
		ndcgs.add(ndcg_1);
		ndcgs.add(ndcg_2);
		ndcgs.add(ndcg_3);
		ndcgs.add(ndcg_4);
		ndcgs.add(ndcg_5);
		ndcgs.add(ndcg_6);
		ndcgs.add(ndcg_7);
		ndcgs.add(ndcg_8);
		ndcgs.add(ndcg_9);
		
		ndcgs.add(ndcg_10);
		ndcgs.add(ndcg_20);
		ndcgs.add(ndcg_30);
		ndcgs.add(ndcg_40);
		ndcgs.add(ndcg_50);
		
		return ndcgs;
	}
	
	public ArrayList<SearchResult> parseJSONFormatofSearchResult(String search_result)
	{
		JSONParser parser = new JSONParser();
		JSONArray passages;
		 ArrayList<SearchResult> results = new ArrayList<SearchResult>();
		try {
			passages = (JSONArray)parser.parse(search_result);
			Iterator iterator = passages.iterator();

			while(iterator.hasNext())
			 {
				 JSONObject obj = (JSONObject)iterator.next();
				 String isbn = (String)obj.get("isbn");
				 int number = (int)(long)obj.get("number");
				 String contents = (String)obj.get("contents");
				 int rank = (int)(long)obj.get("rank");
				 double score = (double)obj.get("score");
				 int method_num = (int)(long)obj.get("method_num");
				
				 
				 SearchResult passage = new SearchResult(isbn,number,contents,rank,score,method_num);
				 results.add(passage);
			 }

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return results;
	}

	
	
}
