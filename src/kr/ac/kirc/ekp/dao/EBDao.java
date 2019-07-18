package kr.ac.kirc.ekp.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.config.Path;



public class EBDao {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public Connection getConnection()
	{
		
		//final String KSLAB_DB_ADDRESS = "jdbc:mysql://kirc.kaist.ac.kr:3306/pmcc";
		//final String KSLAB_DB_USER = "root";
		//final String KSLAB_DB_PW = "kslab!1204";
		
		Path path = new Path();
		final String KSLAB_DB_ADDRESS = path.getKSLAB_DB_ADDRESS();
		final String KSLAB_DB_USER = path.getKSLAB_DB_USER();
		final String KSLAB_DB_PW = path.getKSLAB_DB_PW();
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
	
	
	public ArrayList<String> getFullText(Connection conn, int ebook_id) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from " + ebook_id + "_robbins_paragraph";
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
			
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<String> text = new ArrayList<String>();
		String title = "";
		while(rs.next())
		{
			title = rs.getString("PARAGRAPH");
			//System.out.println(title);
			text.add(title);
		}
		return text;
	}
	
	public String getBookImageURL(int book_pointer, int paragraph_id, Connection conn) throws SQLException
	{
		String url="";
		if(book_pointer ==1)
		{
			url = this.getBook1ImageUrl(paragraph_id, conn);
		}
		else if(book_pointer ==2)
		{
			url = this.getBook2ImageUrl(paragraph_id, conn);
		}
		else if (book_pointer ==3)
		{
			url = this.getBook3ImageUrl(paragraph_id, conn);
		}
		return url;
	}
	
	public String getBook1ImageUrl(int paragraph_id, Connection conn) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from (select * from 1_matching_para_image where PARA_NUM=?) as A inner join (select * from 1_extracted_image) as B on A.IMAGE_NUM=B.NUMBER";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, paragraph_id);
		rs = pstmt.executeQuery();
		
		String url = "";
		while(rs.next())
		{
			url = rs.getString("Path");
		}
		
		rs.close();
		pstmt.close();
		
		return url;
		
	}
	
	public String getBook2ImageUrl( int paragraph_id,Connection conn) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from 2_page_paragraph_matching";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		String url = "";
		while(rs.next())
		{
			int PARA_NUM_START = rs.getInt("PARA_NUM_START");
			int PARA_NUM_END = rs.getInt("PARA_NUM_END");
			
			if(paragraph_id>=PARA_NUM_START && paragraph_id <= PARA_NUM_END)
			{
				url = rs.getString("Path");
			}
		}
		
		rs.close();
		pstmt.close();
		
		return url;
		
	}
	
	public String getBook3ImageUrl(int paragraph_id, Connection conn) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from (select * from 3_matching_para_image where PARA_NUM=?) as A inner join (select * from 3_extracted_image) as B on A.IMAGE_NUM=B.NUMBER";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, paragraph_id);
		rs = pstmt.executeQuery();
		
		String url = "";
		while(rs.next())
		{
			url = rs.getString("Path");
		}
		
		rs.close();
		pstmt.close();
		
		return url;
		
	}
	public EBook getParagraphDetail(Connection conn, int ebook_id, int paragraph_id) throws SQLException
	{
		EBook book = new EBook();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from " + ebook_id + "_paragraph where NUMBER=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, paragraph_id);
		rs = pstmt.executeQuery();
		
		
		String contents = "N/A";
		while(rs.next())
		{
			book.setContents(rs.getString("PARAGRAPH"));
			book.setChap_num(rs.getInt("Chap_num"));
			//book.setPart_num(rs.getInt("Part_num"));
			
		}
		
		rs.close();
		pstmt.close();
		
		return book;
	}
	public String getContents(Connection conn, int ebook_id, int paragraph_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from " + ebook_id + "_paragraph where NUMBER=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, paragraph_id);
		rs = pstmt.executeQuery();
		
		
		String contents = "N/A";
		while(rs.next())
		{
			contents = rs.getString("PARAGRAPH");
		}
		
		rs.close();
		pstmt.close();
		
		return contents;
	}
	
	public ArrayList<EBook> getBookMetadata(Connection conn, int ebook_id) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from (select * from " +ebook_id + "_paragraph) as A left join (select * from " + ebook_id + "_paragraph_knowledge) as B on A.number = B.book_id";
		System.out.println(sql);
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
			
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		System.out.println("=============JOIN PROCESS IS COMPLETE===============");
		ArrayList<EBook> current_book = new ArrayList<EBook>();
		String title = "";
		int number = 0;
		int part_num = 0;
		int chap_num = 0;
		while(rs.next())
		{
			title = rs.getString("PARAGRAPH");
			number = rs.getInt("NUMBER");
			//part_num = rs.getInt("Part_num");
			chap_num = rs.getInt("Chap_num");
			EBook book = new EBook();
			book.setTitle(title);
			book.setNumber(number);
			book.setPart_num(part_num);
			book.setChap_num(chap_num);
			book.setPrevious_content(this.getContents(conn, ebook_id, number-1));
			book.setNext_content(this.getContents(conn, ebook_id, number+1));
			
			if(rs.getString("book_contents_stats") == null)
			{
				book.setBook_contents_stats("");
			}
			else
			{
				book.setBook_contents_stats(rs.getString("book_contents_stats"));
			}
			if(rs.getString("book_contents_tagged") == null)
			{
				book.setBook_contents_tagged("");
			}
			else
			{
				book.setBook_contents_tagged(rs.getString("book_contents_tagged"));
			}
			book.setPage_num(rs.getInt("Real_page_num"));
			//System.out.println(rs.getInt("Real_page_num"));
			//book.setPrevious_content(previous_content);
			//book.setNext_content(next_content);
			//book.setImage_url(image_url);
			//System.out.println(title);
			current_book.add(book);
			
			//text.add(title);
		}
		rs.close();
		pstmt.close();
		return current_book;
	}
}
