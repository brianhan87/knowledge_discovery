package kr.ac.kirc.ekp.core.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.Journal;
import kr.ac.kirc.ekp.bean.Topic;
import kr.ac.kirc.ekp.config.Path;
import kr.ac.kirc.ekp.dao.JournalDao;

public class Search {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public ArrayList<EBook> search(String q) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException
	{
		//ArrayList<String> relevant_section = new ArrayList<String>();
		ArrayList<EBook> relevant_section = new ArrayList<EBook>();
		Path p = new Path();
		//String index_path = p.getIndex_path();
		String book1_index_path = p.getPara_book1_index_path();
		String book2_index_path = p.getPara_book2_index_path();
		String book3_index_path = p.getPara_book3_index_path();
		//String index_path = p.getPara_index_path();
		//String index_path = p.getSen_index_path();
		Directory book1_dir = FSDirectory.open(new File(book1_index_path));
		Directory book2_dir = FSDirectory.open(new File(book2_index_path));
		Directory book3_dir = FSDirectory.open(new File(book3_index_path));
		
		IndexSearcher[] searchers = new IndexSearcher[3];
		searchers[0] = new IndexSearcher(book2_dir);
		searchers[1] = new IndexSearcher(book3_dir);
		searchers[2] = new IndexSearcher(book1_dir);
		//IndexSearcher searcher = new IndexSearcher(dir);
		QueryParser parser = new QueryParser(Version.LUCENE_30,"contents",new StandardAnalyzer(Version.LUCENE_30));
		
		System.out.println(q);
		
		
		
		MultiSearcher searcher = new MultiSearcher(searchers);
		
		Query query = parser.parse(q);
		//System.out.println("CURRENT QUERY: " +query.toString());
		TopDocs docs = searcher.search(query, 1000);
		
		System.err.println("The number of documents retrieved: " + docs.totalHits);
		//System.err.println("The number of documents retrieved: " + docs.totalHits);
		
		BooleanQuery b_queries = new BooleanQuery();
		b_queries.add(query, BooleanClause.Occur.MUST);
		//System.out.println("Boolean QUERY: " + b_queries.toString());
		
		//TopDocs docs = searcher.search(b_queries, 1000);
		//System.out.println(docs.totalHits);
		int rank = 1;
		for(ScoreDoc scoreDoc: docs.scoreDocs)
		{
			
			Document doc = searcher.doc(scoreDoc.doc);
			String candidate_section = doc.get("contents");
			String[] the_number_of_words = candidate_section.split(" ");
			
			if(the_number_of_words.length >= 10)
			{
				double score = scoreDoc.score;
				score = Double.parseDouble(String.format("%.4f", score));
				//System.out.println("current score:" +score );
				
				
				String next_contents = doc.get("next_contents");
				String previous_contents = doc.get("previous_contents");
				String book_contents_tagged = doc.get("book_contents_tagged");
				String book_contents_stats =doc.get("book_contents_stats");
				String image_url = doc.get("image_url");
				String author =doc.get("author");
				String year = doc.get("year");
				String isbn = doc.get("isbn");
				String source_name = doc.get("source_name");
				int number = Integer.parseInt(doc.get("number"));
				int part_num = Integer.parseInt(doc.get("part_num"));
				int chap_num = Integer.parseInt(doc.get("chap_num"));
				int page_number = Integer.parseInt(doc.get("book_page_number"));
				
				
				
				//if(candidate_section.length()>=500)
				//{
					
					
					TokenStream tokenStream = new StandardAnalyzer(Version.LUCENE_30).tokenStream("contents", new StringReader(candidate_section));
					QueryScorer scorer = new QueryScorer(b_queries, "contents");
					
					SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<mark>","</mark>");
					Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
					Highlighter highlighter = new Highlighter(formatter,scorer);
					highlighter.setTextFragmenter(fragmenter);
					//System.out.println("DUBBUGING: " + candidate_section);
					String highlight_result = highlighter.getBestFragments(tokenStream,candidate_section,3," ... ");
					//highlight_result = " ... " + highlight_result + "...";
					//String highlight_result = highlighter.getBestFragment(tokenStream, candidate_section);
					//System.out.println("HERE: " + source_name + " | " + number + " | " + part_num + " | " + chap_num +" | " + highlight_result);
					//Section section = new Section(source_name,candidate_section,highlight_result);
					 
					EBook ebook = new EBook();
					ebook.setTitle(source_name);
					ebook.setHighlight_content(highlight_result);
					ebook.setContents(candidate_section);
					ebook.setNumber(number);
					ebook.setChap_num(chap_num);
					ebook.setPart_num(part_num);
					ebook.setNext_content(next_contents);
					ebook.setPrevious_content(previous_contents);
					ebook.setBook_contents_stats(book_contents_stats);
					ebook.setBook_contents_tagged(book_contents_tagged);
					ebook.setImage_url(image_url);
					ebook.setAuthor(author);
					ebook.setYear(year);
					ebook.setIsbn(isbn);
					ebook.setRank(rank);
					ebook.setScore(score);
					ebook.setPage_num(page_number);
					relevant_section.add(ebook);
					rank++;
			}
			
			//}
			/*
			if(candidate_section.length()>=80)
			{
				relevant_section.add(doc.get("contents"));
				System.out.println(doc.get("contents"));
			}
			*/			
		}
		
		searcher.close();
		book1_dir.close();
		book2_dir.close();
		book3_dir.close();
		
		return relevant_section;
		
	}
	
	public ArrayList<Journal> searchJournal(String q, int next) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException
	{
		//ArrayList<String> relevant_section = new ArrayList<String>();
		ArrayList<Journal> relevant_journal = new ArrayList<Journal>();
		Path p = new Path();
		String index_path = p.getJournal_index_path();
		String index_journal_path1 = p.getJournal_index_path1();
		String index_journal_path2 = p.getJournal_index_path2();
		Directory journal_dir1 = FSDirectory.open(new File(index_journal_path1));
		Directory journal_dir2 = FSDirectory.open(new File(index_journal_path2));
		//IndexSearcher searcher = new IndexSearcher(journal_dir);
		
		IndexSearcher[] searchers = new IndexSearcher[2];
		searchers[0] = new IndexSearcher(journal_dir1);
		searchers[1] = new IndexSearcher(journal_dir2);
		MultiSearcher searcher = new MultiSearcher(searchers);
		
		//QueryParser parser = new QueryParser(Version.LUCENE_30,"abstract",new StandardAnalyzer(Version.LUCENE_30));
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		
		//Query query = parser.parse(q);
		
		Query query = new MultiFieldQueryParser(Version.LUCENE_CURRENT,new String[]{"title","abstract","contents"},analyzer).parse(q);
		//System.out.println("CURRENT QUERY: " +query.toString());
		TopDocs docs = searcher.search(query, next);
		System.err.println("The number of documents retrieved: " + docs.totalHits);
				
		BooleanQuery b_queries = new BooleanQuery();
		b_queries.add(query, BooleanClause.Occur.MUST);
		int rank = 1;
		for(ScoreDoc scoreDoc: docs.scoreDocs)
		{
			Document doc = searcher.doc(scoreDoc.doc);
			double score = scoreDoc.score;
			score = Double.parseDouble(String.format("%.4f", score));			
			Journal journal = new Journal(); 			
			TokenStream tokenStream = new StandardAnalyzer(Version.LUCENE_30).tokenStream("abstract", new StringReader(doc.get("abstract")));
			QueryScorer scorer = new QueryScorer(b_queries, "abstract");
			
			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<mark>","</mark>");
			Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
			Highlighter highlighter = new Highlighter(formatter,scorer);
			highlighter.setTextFragmenter(fragmenter);
			String highlight_result = highlighter.getBestFragments(tokenStream,doc.get("abstract"),3," ... ");
			
			//System.out.println("ABSTRACT:" + doc.get("abstract"));
			//System.out.println("Highlighted: " + highlight_result);
			
			//String highlight_result = "this is highlight";
			//highlight_result = " ... " + highlight_result + "...";
			//String highlight_result = highlighter.getBestFragment(tokenStream, candidate_section);
			//Section section = new Section(source_name,candidate_section,highlight_result);
			String author = doc.get("author");
			//System.out.println(author);
			author = author.replaceAll("([A-Z])", " $1");
			//System.out.println("AFTER:" + author);
			
			journal.setAll(Integer.parseInt(doc.get("pmc_id")), doc.get("journal_title"), author, doc.get("title"), doc.get("date"), doc.get("abstract"), doc.get("contents"), doc.get("reference"), highlight_result, rank, score);		
			relevant_journal.add(journal);	
			rank++;
		}
		
		searcher.close();
		//journal_dir.close();
		journal_dir1.close();
		journal_dir2.close();
		
		return relevant_journal;
		
	}	
}
