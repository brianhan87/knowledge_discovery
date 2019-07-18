package kr.ac.kirc.ekp.core.search;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.xml.sax.ContentHandler;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.Journal;
import kr.ac.kirc.ekp.config.EBookInfo;
import kr.ac.kirc.ekp.config.Path;
import kr.ac.kirc.ekp.dao.EBDao;
import kr.ac.kirc.ekp.dao.JournalDao;


	public class Indexer {

		/**
		 * @param args
		 */
		public static void main(String[] args) throws Exception {
			// TODO Auto-generated method stub
			indexing_book();
			//add_journal();
			//run_add_journal();
			//indexing_journal();
			//indexing_pdf();
			
			/*
			PathSetup s = new PathSetup(); 
			
			String indexDir = s.getIndexDir();
			String dataDir = s.getMovieDir();
			
			//String indexDir = "D:/development/samsung_prototype/indexDir/";
			//String dataDir = "D:/development/samsung_prototype/dataDir/";
			
			long start = System.currentTimeMillis();
			Indexer indexer = new Indexer(indexDir);
			int numIndexed;
			
			try
			{
				numIndexed = indexer.index(dataDir, new TextFilesFilter());
			}
			finally
			{
				indexer.close();
			}
			
			long end = System.currentTimeMillis();
			
			System.out.println("Indexing " +numIndexed + " files took "+(end-start)+" mileseconds");
			*/
		}
				
	public static void indexing_book() throws Exception {
		EBDao dao = new EBDao();
		Connection conn = dao.getConnection();

		Path path = new Path();
		ArrayList<EBook> ebooks = new ArrayList<EBook>();
		// CHANGE FOR BOOK INDEXING..
		for (int k = 1; k < 2; k++) {
			ebooks = dao.getBookMetadata(conn, k);

			// int source = 3;
			// ArrayList<EBook> ebooks = dao.getBookMetadata(conn, source);

			EBookInfo info = new EBookInfo();
			String source_name = info.getTitle(k);
			String author = info.getAuthor(k);
			String isbn = info.getISBN(k);
			String year = info.getYear(k);
			String dataDir = path.getPara_book_index_path(k);
			// Up to here.

			System.out.println(ebooks.size());

			// String dataDir = "C:/ekp/book2_pdf/";

			Directory dir = FSDirectory.open(new File(dataDir));
			System.out.println("DONE1");
			IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), true,
					IndexWriter.MaxFieldLength.UNLIMITED);

			for (int i = 0; i < ebooks.size(); i++) {
				String text = ebooks.get(i).getTitle();

				Document doc = new Document();
				doc.add(new Field("contents", text, Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("previous_contents", ebooks.get(i).getPrevious_content(), Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("next_contents", ebooks.get(i).getNext_content(), Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("book_contents_tagged", ebooks.get(i).getBook_contents_tagged(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("book_contents_stats", ebooks.get(i).getBook_contents_stats(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				System.out.println(ebooks.get(i).getPage_num());
				doc.add(new Field("book_page_number", Integer.toString(ebooks.get(i).getPage_num()), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("source_name", source_name, Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("number", Integer.toString(ebooks.get(i).getNumber()), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("part_num", Integer.toString(ebooks.get(i).getPart_num()), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("chap_num", Integer.toString(ebooks.get(i).getChap_num()), Field.Store.YES,
						Field.Index.NOT_ANALYZED));

				doc.add(new Field("author", author, Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("year", year, Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("isbn", isbn, Field.Store.YES, Field.Index.NOT_ANALYZED));

				// System.out.println(author + " , " + year + " , " + isbn);

				// HERE NEEDS TO CHANGE FOR BOOK INDEXING..
				if(k == 1)
				{
					doc.add(new Field("image_url", dao.getBook1ImageUrl(ebooks.get(i).getNumber(), conn), Field.Store.YES,
							Field.Index.NOT_ANALYZED));
					System.out.println("URL: " + dao.getBook1ImageUrl(ebooks.get(i).getNumber(), conn));
				}
				else if(k ==2)
				{
					doc.add(new Field("image_url", dao.getBook2ImageUrl(ebooks.get(i).getNumber(), conn), Field.Store.YES,
							Field.Index.NOT_ANALYZED));
					System.out.println("URL: " + dao.getBook2ImageUrl(ebooks.get(i).getNumber(), conn));
				}
				else if(k ==3)
				{
					doc.add(new Field("image_url", dao.getBook3ImageUrl(ebooks.get(i).getNumber(), conn), Field.Store.YES,
							Field.Index.NOT_ANALYZED));
					System.out.println("URL: " + dao.getBook3ImageUrl(ebooks.get(i).getNumber(), conn));
				}
				System.out.println("URL: " + dao.getBook3ImageUrl(ebooks.get(i).getNumber(), conn));
				// Up to here

				writer.addDocument(doc);
				System.out.println((i + 1) + "th Done for indexing: " + text);
			}

			writer.close();

		}
		dao.closeConnection(conn);
	}

		public static void indexing_journal() throws Exception 
		{
			JournalDao dao = new JournalDao();
			Connection conn = dao.getConnection();
			
			Path path = new Path();
			
		    //CHANGE FOR BOOK INDEXING.. 
			
			
			ArrayList<Integer> pmc_id = dao.getPmcId(conn);
			System.out.println("Total documents is: " + pmc_id.size() );
			//String dataDir = path.getJournal_index_path();
			String dataDir = path.getJournal_index_path2();
			//String dataDir = path.getPara_book3_index_path();
			//Up to here. 	
			
						
			Directory dir = FSDirectory.open(new File(dataDir));
			System.out.println("DONE1");
			IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), true,IndexWriter.MaxFieldLength.UNLIMITED);
			
			//System.out.println(pmc_id.get(572350));
			
			for (int i=500000;i<pmc_id.size();i++)
			{
				Journal journal = dao.getMetaJournalInfo(conn, pmc_id.get(i));
				
				Document doc = new Document();
				doc.add(new Field("pmc_id",Integer.toString(journal.getPmc_id()),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				doc.add(new Field("journal_title",journal.getJournal_title(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				doc.add(new Field("author",journal.getAuthor(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				doc.add(new Field("title",journal.getTitle(),Field.Store.YES,Field.Index.ANALYZED ));
				doc.add(new Field("date",journal.getDate(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				doc.add(new Field("abstract",journal.getAbstract_text(),Field.Store.YES,Field.Index.ANALYZED ));
				doc.add(new Field("contents",journal.getContents(),Field.Store.YES,Field.Index.ANALYZED ));
				doc.add(new Field("reference",journal.getReferece(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				
				/*
				 *  The below three lines boost the documents if those are frequently clicked by the user. 
				 * 
				 */
				int click = dao.getClickInfo(conn, journal.getPmc_id());
				double boost = Math.log(Math.exp(click))/Math.log(2);
				float f = (float)boost;
				doc.setBoost(f);
				writer.addDocument(doc);
				System.out.println( (i+1) + "th out of " + pmc_id.size() + " is done for indexing: " );
			}

			writer.close();
			dao.closeConnection(conn);
		}
		public static IndexWriter getWriter(Directory dir) throws CorruptIndexException, LockObtainFailedException, IOException{
			return new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30),true,IndexWriter.MaxFieldLength.UNLIMITED);
		}
		public static void run_add_journal() throws Exception 
		{
			JournalDao dao = new JournalDao();
			Connection conn = dao.getConnection();
			ArrayList<Integer> pmc_id = dao.getPmcId(conn);
			System.out.println("START INDEXING");
			Path path = new Path();
			//String dataDir1 = path.getJournal_index_path1();
			//add_journal(0,500000,dataDir1);
			//System.out.println("first indexing done");
			String dataDir2 = path.getJournal_index_path2();
			add_journal(500000,pmc_id.size(),dataDir2);
			System.out.println("second indexing done");
		}
		public static void add_journal(int start, int end,String dataDir) throws Exception 
		{
			JournalDao dao = new JournalDao();
			Connection conn = dao.getConnection();
			
			Path path = new Path();
			
		    //CHANGE FOR BOOK INDEXING.. 
			
			
			ArrayList<Integer> pmc_id = dao.getPmcId(conn);
			System.out.println("Total documents is: " + pmc_id.size() );
			//String dataDir = path.getJournal_index_path();
			dataDir = path.getJournal_index_path1();
			//String dataDir = path.getPara_book3_index_path();
			//Up to here. 	
			
			
			Directory dir = FSDirectory.open(new File(dataDir));
			System.out.println("DONE1");
			IndexWriter writer = getWriter(dir);
			
			System.out.println(pmc_id.get(572350));
			
			for (int i=start;i<end;i++)
			{
				Journal journal = dao.getMetaJournalInfo(conn, pmc_id.get(i));
				System.out.println("STOPPOINT 1");
				Document doc = new Document();
				doc.add(new Field("pmc_id",Integer.toString(journal.getPmc_id()),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				doc.add(new Field("journal_title",journal.getJournal_title(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				doc.add(new Field("author",journal.getAuthor(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				doc.add(new Field("title",journal.getTitle(),Field.Store.YES,Field.Index.ANALYZED ));
				doc.add(new Field("date",journal.getDate(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				doc.add(new Field("abstract",journal.getAbstract_text(),Field.Store.YES,Field.Index.ANALYZED ));
				doc.add(new Field("contents",journal.getContents(),Field.Store.YES,Field.Index.ANALYZED ));
				doc.add(new Field("reference",journal.getReferece(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
				System.out.println("Stoppint 2");			
				writer.addDocument(doc);
				System.out.println("Stoppint 3");
				System.out.println( (i+1) + "th out of " + pmc_id.size() + " is done for indexing: " );
			}

			writer.close();
			dao.closeConnection(conn);
		}
		
		
	}

