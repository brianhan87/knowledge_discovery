package kr.ac.kirc.ekp.restful;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.Journal;
import kr.ac.kirc.ekp.bean.Log;
import kr.ac.kirc.ekp.bean.Topic;
import kr.ac.kirc.ekp.bean.User;

public class SimpleJSON {

	class ValueComparator3<K, V extends Comparable<V>> implements Comparator<K>{
		 
		HashMap<K, V> map = new HashMap<K, V>();
	 
		public ValueComparator3(HashMap<K, V> map){
			this.map.putAll(map);
		}
	 
		@Override
		public int compare(K s1, K s2) {
			return -map.get(s1).compareTo(map.get(s2));//descending order	
		}
	}	
	
	
	public Map<EBook, Integer> sortByValue(Map<EBook, Integer> map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			String relation = "설명";
			String str = "[{\"concepts\":[{\"article\":2},{\"This\":2}]},{\"relations\":[{\"개체간영향\":1},{\"설명\":1}]},{\"attributes\":[]}]";
			System.out.println(str);
			Object obj = JSONValue.parse(str);
			//JSONObject object = (JSONObject)obj;
			
			JSONArray array = (JSONArray)obj;
			JSONObject j = (JSONObject)array.get(1);
			
			System.out.println(j.toString());
			
			Object obj2 = JSONValue.parse(j.toString());
			JSONObject object2 = (JSONObject)obj2;
			
			JSONArray array2 = (JSONArray)object2.get("relations");
			int s = array2.size();
			for(int i=0;i<s;i++)
			{
				JSONObject k = (JSONObject)array2.get(i);
				System.out.println(k.toString());
				System.out.println(k.get(relation));
			}		
	}
	
	public ArrayList<EBook> sortbyCLI(ArrayList<EBook> books, String relation)
	{
		HashMap<EBook,Integer> map = new HashMap<EBook,Integer>();
				
		for(int a=0;a<books.size();a++)
		{
			String str = books.get(a).getBook_contents_stats();
			Object obj = JSONValue.parse(str);
			//JSONObject object = (JSONObject)obj;
			
			JSONArray array = (JSONArray)obj;
			JSONObject j = (JSONObject)array.get(1);
			
			System.out.println(j.toString());
			
			Object obj2 = JSONValue.parse(j.toString());
			JSONObject object2 = (JSONObject)obj2;
			
			JSONArray array2 = (JSONArray)object2.get("relations");
			int s = array2.size();
			int value = 0;	
			for(int i=0;i<s;i++)
			{
				JSONObject k = (JSONObject)array2.get(i);
				System.out.println(k.toString());
				System.out.println(k.get(relation));
					
	
				if(k.get(relation) != null)
				{
					value = ((Long)k.get(relation)).intValue();
				}
							
			}
			map.put(books.get(a), value);	
		}
		
		Map<EBook,Integer> sortedMap = this.sortByValue(map);
		ArrayList<EBook> sorted_books = new ArrayList<EBook>();
		for (Map.Entry<EBook, Integer> entry : sortedMap.entrySet()) {
            //System.out.println("Item is:" + entry.getKey().getNumber() + " with value:"
              //      + entry.getValue());
            
            sorted_books.add(entry.getKey());
        }
		
		return sorted_books;
		
	}
	
	public ArrayList<String> getDummyKeywords()
	{
		ArrayList<String> dummy_keywords = new ArrayList<String>();
		
		dummy_keywords.add("bacillus");
		dummy_keywords.add("BCG");
		dummy_keywords.add("behavior disorder");
		dummy_keywords.add("benign");
		dummy_keywords.add("benzedrine");
		dummy_keywords.add("beta ray");
		dummy_keywords.add("berylliosis");
		dummy_keywords.add("bezoar");
		dummy_keywords.add("betatron");
		dummy_keywords.add("biopsy");
		dummy_keywords.add("billrubin");
		dummy_keywords.add("blast");
		dummy_keywords.add("blood");
		dummy_keywords.add("blood sugar");
		dummy_keywords.add("blooad pressure");
		dummy_keywords.add("blood test");
		dummy_keywords.add("breast cancer");
		dummy_keywords.add("bronchiectasis");
		
		return dummy_keywords;
		
	}
	public String writeBookJSON(ArrayList<Topic> queries, ArrayList<EBook> ebooks, int total)
	{ 
		JSONObject obj = new JSONObject();
		
		JSONArray main_topics = new JSONArray();
		
		System.out.println("QUERY:" + queries.size());
		/*
		for(int i=0;i<queries.size();i++)
		{
			System.out.println(queries.get(i).getTitle() + " , " + queries.get(i).isMust());
		}
		*/
		int id = 1;
		for(int i=0;i<queries.size();i++)
		{
			JSONObject main_topic = new JSONObject();
			
			if(queries.get(i).isMust())
			{
				main_topic.put("topic_title", queries.get(i).getTitle());
				main_topic.put("topic_id", id);
				main_topics.add(main_topic);
				id++;
			}
		}
		
		JSONArray sub_topics = new JSONArray();
		
		id=1;
		for(int i=0;i<queries.size();i++)
		{
			JSONObject sub_topic = new JSONObject();
			//System.out.println("ISMUST: " + queries.get(i).getTitle() + " , " + queries.get(i).isMust());
			if(!queries.get(i).isMust())
			{
				sub_topic.put("topic_title", queries.get(i).getTitle());
				sub_topic.put("topic_id", id);
				sub_topics.add(sub_topic);
				id++;
			}
		}
		
		JSONArray books = new JSONArray();
		for (int i=0;i<ebooks.size();i++)
		{
			JSONObject book = new JSONObject();
			book.put("book_rank", ebooks.get(i).getRank());
			book.put("book_id", ebooks.get(i).getNumber());
			book.put("book_source", ebooks.get(i).getTitle());
			book.put("book_author", ebooks.get(i).getAuthor());
			book.put("book_year", ebooks.get(i).getYear());
			book.put("book_isbn", ebooks.get(i).getIsbn());
			book.put("book_chapter", ebooks.get(i).getChap_num());
			book.put("book_part", ebooks.get(i).getPart_num());
			book.put("book_contents", ebooks.get(i).getContents());
			book.put("book_highlighted_contents", ebooks.get(i).getHighlight_content());
			book.put("book_image_url", ebooks.get(i).getImage_url());
			book.put("book_previous_contents", ebooks.get(i).getPrevious_content());
			book.put("book_next_contents", ebooks.get(i).getNext_content());
			book.put("score", ebooks.get(i).getScore());
			book.put("book_page_number", ebooks.get(i).getPage_num());
			//System.out.println(ebooks.get(i).getPage_num());
			//book.put("book_contents_stats", ebooks.get(i).getBook_contents_stats());
			//book.put("book_contents_tagged", ebooks.get(i).getBook_contents_tagged());
			
			books.add(book);
		}
		
		JSONArray keywords = new JSONArray();
		ArrayList<String> dummy_keywords = this.getDummyKeywords();
		for (int i=0;i<dummy_keywords.size();i++)
		{
			JSONObject keyword = new JSONObject();
			keyword.put("keyword_id", new Integer(i+1));
			keyword.put("keyword_title", dummy_keywords.get(i));
			
			keywords.add(keyword);
		}
		
		obj.put("total_doc", total);
		obj.put("books", books);
		obj.put("main_topics", main_topics);
		obj.put("sub_topics", sub_topics);
		obj.put("keywords", keywords);
		//System.out.println(obj.toJSONString());
		
		return obj.toJSONString();
	}
	
	public String writeJournalJSON(ArrayList<Topic> queries, ArrayList<Journal> papers, int total)
	{ 
		JSONObject obj = new JSONObject();
		
		JSONArray main_topics = new JSONArray();
		
		int id = 1;
		for(int i=0;i<queries.size();i++)
		{
			JSONObject main_topic = new JSONObject();
			
			if(queries.get(i).isMust())
			{
				main_topic.put("topic_title", queries.get(i).getTitle());
				main_topic.put("topic_id", id);
				main_topics.add(main_topic);
				id++;
			}
		}
		
		JSONArray sub_topics = new JSONArray();
		
		id = 1;
		for(int i=0;i<queries.size();i++)
		{
			JSONObject sub_topic = new JSONObject();
			
			if(!queries.get(i).isMust())
			{
				sub_topic.put("topic_title", queries.get(i).getTitle());
				sub_topic.put("topic_id", id);
				sub_topics.add(sub_topic);
				id++;
			}
		}
		
		JSONArray journals = new JSONArray();
		for (int i=0;i<papers.size();i++)
		{
			JSONObject journal = new JSONObject();
			journal.put("journal_rank", papers.get(i).getRank());
			journal.put("pmc_id",  papers.get(i).getPmc_id());
			journal.put("pmc_url", "http://www.ncbi.nlm.nih.gov/pmc/articles/PMC" + papers.get(i).getPmc_id()+"/");
			journal.put("journal_title", papers.get(i).getJournal_title());
			journal.put("author", papers.get(i).getAuthor());
			journal.put("title", papers.get(i).getTitle());
			journal.put("date", papers.get(i).getDate());
			journal.put("abstract", papers.get(i).getAbstract_text());
			//journal.put("contents", papers.get(i).getContents());
			//journal.put("referece", papers.get(i).getReferece());
			journal.put("highlighted_contents",papers.get(i).getHighlight_content());
			journal.put("score",papers.get(i).getScore());
			
			journals.add(journal);
		}
		
		JSONArray keywords = new JSONArray();
		ArrayList<String> dummy_keywords = this.getDummyKeywords();
		for (int i=0;i<dummy_keywords.size();i++)
		{
			JSONObject keyword = new JSONObject();
			keyword.put("keyword_id", new Integer(i+1));
			keyword.put("keyword_title", dummy_keywords.get(i));
			
			keywords.add(keyword);
		}
		
		obj.put("total_doc", total);
		obj.put("journals", journals);
		obj.put("main_topics", main_topics);
		obj.put("sub_topics", sub_topics);
		obj.put("keywords", keywords);
		
		
		//System.out.println(obj.toJSONString());
		
		return obj.toJSONString();
	}
	public String writeUser(User user)
	{ 
		JSONObject obj = new JSONObject();
		obj.put("id", user.getId());
		obj.put("pw", user.getPw());
		obj.put("name", user.getName());
		obj.put("department", user.getDepartment());
		obj.put("chmod", user.getChmod());
		
		
		return obj.toJSONString();
	}
	public String writeLog(ArrayList<Log> logs)
	{ 
		JSONObject obj = new JSONObject();
		
		JSONArray logs2 = new JSONArray();
		for (int i=0;i<logs.size();i++)
		{
			JSONObject current_log = new JSONObject();
			current_log.put("api_name", logs.get(i).getApi_name());
			current_log.put("id", logs.get(i).getId());
			current_log.put("requested_ip", logs.get(i).getRequested_id());
			current_log.put("timestamp", logs.get(i).getTimestamp());
			logs2.add(current_log);
		}
		
		obj.put("logs",logs2); 
		
		return obj.toJSONString();
	}
	

}
