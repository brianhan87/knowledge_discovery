package kr.ac.kirc.ekp.core.search;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import kr.ac.kirc.ekp.bean.Topic;
import kr.ac.kirc.ekp.dao.OntologyDao;
import kr.ac.kirc.ekp.restful.BioPortalAccessor;
import kr.co.shineware.nlp.komoran.core.analyzer.Komoran;

public class Query_Formulator {
	
	static KoreanAnalyzer anal = new KoreanAnalyzer();
	static Komoran komoran = anal.getAnalyzer();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*
		KoreanAnalyzer anal = new KoreanAnalyzer();
		Komoran komoran = anal.getAnalyzer();
		String s = "알부민이 감소하였습니다. 중증의 간질환, 영양실조, 신증후군, 염증성 질환, 침상안정, 수액과공급, 복수 등에 의해서 감소할 수 있습니다.";
		ArrayList<String> nouns = anal.getNouns(s,komoran);
		
		System.out.println("SIZE OF NOUNS BEFORE: " +nouns.size());
		System.out.println(nouns.toString());
		
		OntologyDao dao = new OntologyDao();
		
		Connection conn = dao.getConnection();
		
		ArrayList<String> ekp_codes = QueryDetector(nouns,dao,conn);
		
		System.out.println(ekp_codes.toString());
		
		ArrayList<String> initial_queries = QueryTranslator(ekp_codes,dao,conn);
		System.out.println(initial_queries.toString());
		*/
		
		
		Query_Formulator qf = new Query_Formulator();
		qf.run();
		
	}
	
	public void run() throws Exception
	{
		//String s = "This is an impaired fasting glucose. The Korean Diabetes Association has designated fasting blood glucose level 100-125 mg / dL as a fasting blood sugar disorder. We recommend continuous exercise and weight management and regular blood glucose testing to prevent diabetes.";
		String s = "Hematology test result is normal";
		ArrayList<Topic> topics = this.executewithBioPortal(s);
		ArrayList<String> must_topic = new ArrayList<String>();
		ArrayList<String> sub_topic = new ArrayList<String>();
		for(int i=0;i<topics.size();i++)
		{
			if(topics.get(i).isMust())
			{
				must_topic.add(topics.get(i).getTitle());
			}
			else
			{
				sub_topic.add(topics.get(i).getTitle());
			}
		}
		
		System.out.println(must_topic.size() + " : " + must_topic.toString());
		System.out.println(sub_topic.size() + " : " + sub_topic.toString());
		
	}
	public ArrayList<Topic> execute(String s) throws Exception
	{
		
		//String s = "알부민이 감소하였습니다. 중증의 간질환, 영양실조, 신증후군, 염증성 질환, 침상안정, 수액과공급, 복수 등에 의해서 감소할 수 있습니다.";
		
		
	
		long start = System.currentTimeMillis();
		OntologyDao dao = new OntologyDao();
		
		Connection conn = dao.getConnection();
		
		ArrayList<String> ekp_codes_must = new ArrayList<String>();
		ArrayList<String> ekp_codes_additional = new ArrayList<String>();
		//System.out.println("Analyzing Begins.. ");

		System.out.println("KOMORAN GOES");
		ArrayList<String> nouns = anal.getNouns(s,komoran);
		//System.out.println("SIZE OF NOUNS BEFORE: " +nouns.size());
		System.out.println(nouns.toString());
		
		ekp_codes_must = this.QueryDetector(nouns, true, dao, conn);
		ekp_codes_additional = this.QueryDetector(nouns, false, dao, conn);
		
		ArrayList<String> initial_queries_must = this.QueryTranslatorforMUST(ekp_codes_must,dao,conn);
		
		for(int i=0;i<ekp_codes_must.size();i++)
		{
			ekp_codes_additional.add(ekp_codes_must.get(i));
		}
		
		//ArrayList<String> ekp_codes_additional = this.QueryDetector(nouns, false, dao, conn);
		ArrayList<String> initial_queries_additional_tmp = this.QueryTranslatorforAdditional(ekp_codes_additional,dao,conn);
		ArrayList<String> initial_queries_additional = new ArrayList<String>();
		for(int i=0;i<initial_queries_additional_tmp.size();i++)
		{
			if(!initial_queries_must.contains(initial_queries_additional_tmp.get(i)))
			{
				initial_queries_additional.add(initial_queries_additional_tmp.get(i));
			}
		}

		// ArrayList 형태로 다시 생성
		
		//System.out.println(ekp_codes_must.toString());
		//System.out.println(ekp_codes_additional.toString());
		
		ArrayList<Topic> topics = new ArrayList<Topic>();
		
		for(int i=0;i<initial_queries_must.size();i++)
		{
			Topic t = new Topic();
			t.setTitle(initial_queries_must.get(i));
			t.setMust(true);
			topics.add(t);
		}
		
		for(int i=0;i<initial_queries_additional.size();i++)
		{
			Topic t = new Topic();
			t.setTitle(initial_queries_additional.get(i));
			t.setMust(false);
			topics.add(t);
		}
		boolean ismust = true;
		if(topics.size() == 0)
		{
			//Random random = new Random();
			for(int i =0;i<nouns.size();i++)
			{
				
				Topic t = new Topic();
				t.setTitle(nouns.get(i));
				ismust = !ismust;
				t.setMust(ismust);
				topics.add(t);
			}
		}
				
		dao.closeConnection(conn);
		
		long end = System.currentTimeMillis();
		System.out.println(((end-start)/1000) + " seconds was executed for query formulating.");
		//return initial_queries;
		return topics;
	}
	public ArrayList<Topic> executewithBioPortal(String s) throws Exception
	{
		
		//String s = "알부민이 감소하였습니다. 중증의 간질환, 영양실조, 신증후군, 염증성 질환, 침상안정, 수액과공급, 복수 등에 의해서 감소할 수 있습니다.";
		
		BioPortalAccessor bpa = new BioPortalAccessor();
		String path = "D:/downloads/UMLS_SemanticType.txt";
		ArrayList<String> available_umls_codes = bpa.getAvailableUMLSCodes(path);
		//ArrayList<String> nouns = anal.getNouns(s,komoran);
		
		ArrayList<String> nouns = bpa.getNouns(s);
		System.out.println(nouns.toString());
		ArrayList<Topic> final_nouns = new ArrayList<Topic>();
		//System.out.println(nouns.size() + " : " + nouns.toString());
		for(int i=0;i<nouns.size();i++)
		{
			String xml = bpa.getSemanticsTypes(nouns.get(i));
			ArrayList<String> umls_codes_for_the_noun = bpa.getUMLSTypes(xml);
			System.out.println(umls_codes_for_the_noun.toString());
			//if(umls_codes_for_the_noun.contains("T033")||umls_codes_for_the_noun.contains("T034")||umls_codes_for_the_noun.contains("T047")||umls_codes_for_the_noun.contains("T184")||umls_codes_for_the_noun.contains("T039")||umls_codes_for_the_noun.contains("T060")||umls_codes_for_the_noun.contains("T190")||umls_codes_for_the_noun.contains("T059")||umls_codes_for_the_noun.contains("T121")||umls_codes_for_the_noun.contains("T191")||umls_codes_for_the_noun.contains("T061"))
			//if(umls_codes_for_the_noun.contains("T047"))
			for(int k=0;k<available_umls_codes.size();k++)
			{
				if(umls_codes_for_the_noun.contains(available_umls_codes.get(k)))
				{
					Topic topic = new Topic();
					topic.setTitle(nouns.get(i));
					topic.setMust(true);
					
					
					//System.out.println((i+1) + " : " + nouns.get(i) + " , " + bpa.getUMLSTypes(nouns.get(i)));
					System.out.println(nouns.get(i)+ " : " + available_umls_codes.get(k));
					final_nouns.add(topic);
					ArrayList<String> synonyms = bpa.getSynonyms(xml);
					for(int j=0;j<synonyms.size();j++)
					{
						Topic sub_topic = new Topic();
						sub_topic.setTitle(synonyms.get(j));
						sub_topic.setMust(false);
						
						//System.out.println((i+1) + " : " + nouns.get(i) + " , " + bpa.getUMLSTypes(nouns.get(i)));
						
						final_nouns.add(sub_topic);
					}
					break;
				}
			}
		}		
		return final_nouns;
	}
	
	
	
	
	public ArrayList<String> QueryTranslatorforAdditional(ArrayList<String> ekp_codes, OntologyDao dao, Connection conn) throws Exception
	{
		ArrayList<String> initial_query_set = new ArrayList<String>();
		
		for (int i=0;i<ekp_codes.size();i++)
		{
			ArrayList<String> current_syn= dao.getQueries(conn, ekp_codes.get(i));
			for(int j=0;j<current_syn.size();j++)
			{
				initial_query_set.add(current_syn.get(j));
			}
			
		}
		
		return initial_query_set;
		
	}
	public ArrayList<String> QueryTranslatorforMUST(ArrayList<String> ekp_codes, OntologyDao dao, Connection conn) throws Exception
	{
		ArrayList<String> initial_query_set = new ArrayList<String>();
		
		for (int i=0;i<ekp_codes.size();i++)
		{
			ArrayList<String> current_syn= dao.getKeywords(conn, ekp_codes.get(i));
			for(int j=0;j<current_syn.size();j++)
			{
				initial_query_set.add(current_syn.get(j));
			}
			
		}
		
		return initial_query_set;
		
	}
	
	public ArrayList<String> QueryDetector(ArrayList<String> nouns, boolean isMust, OntologyDao dao, Connection conn) throws Exception
	{
		ArrayList<String> ekp_codes = new ArrayList<String>();
		
		
		for (int i=0;i<nouns.size();i++)
		{
			ArrayList<String> current_ekp_codes = new ArrayList<String>();
			if(isMust)
			{
				current_ekp_codes = dao.getEKPCODEofMUSTType(conn, nouns.get(i));
			}
			else
			{
				current_ekp_codes = dao.getEKPCODEofAdditionalType(conn, nouns.get(i));
			}
			//= dao.getEKPCODE(conn, nouns.get(i));
			for(int j=0;j<current_ekp_codes.size();j++)
			{
				if(!ekp_codes.contains(current_ekp_codes.get(j)))
				{
					ekp_codes.add(current_ekp_codes.get(j));
				}
			}
			
		}
		
		return ekp_codes;
		
	}
	
	

}
