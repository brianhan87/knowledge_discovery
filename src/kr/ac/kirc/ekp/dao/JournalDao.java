package kr.ac.kirc.ekp.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.Journal;
import kr.ac.kirc.ekp.config.Path;



public class JournalDao {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public Connection getConnection()
	{
		
		//final String KSLAB_DB_ADDRESS = "jdbc:mysql://kirc.kaist.ac.kr:3306/journal";
		//final String KSLAB_DB_USER = "root";
		//final String KSLAB_DB_PW = "kslab!1204";
		Path path = new Path();
		final String KSLAB_DB_ADDRESS = path.getKSLAB_JOURNAL_DB_ADDRESS();
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
	public Connection getEKPConnection()
	{
		
		final String KSLAB_DB_ADDRESS = "jdbc:mysql://ekp.kaist.ac.kr:3306/kaist_munyi";
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
	
	public Journal getMetaJournalInfo(Connection conn,int pmc_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from journal where pmc_id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pmc_id);
		rs = pstmt.executeQuery();
		
		Journal journal = new Journal();
		while(rs.next())
		{	
			
			journal.setPmc_id(pmc_id);
			journal.setJournal_title(rs.getString("journal"));
			journal.setAuthor(rs.getString("Author"));
			journal.setTitle(rs.getString("title"));
			journal.setDate(rs.getString("date"));
			journal.setAbstract_text(rs.getString("abstract"));
			journal.setContents(rs.getString("contents"));
			journal.setReferece(rs.getString("reference"));
		}
		
		rs.close();
		pstmt.close();
		
		return journal;
	}
	/*
	public ArrayList<String> getKeywords(Connection conn, int pmc_id)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from tfidf_dictionary where pmc_id=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pmc_id);
		rs = pstmt.executeQuery();
		
		Journal journal = new Journal();
		while(rs.next())
		{	
			
			journal.setPmc_id(pmc_id);
			journal.setJournal_title(rs.getString("journal"));
			journal.setAuthor(rs.getString("Author"));
			journal.setTitle(rs.getString("title"));
			journal.setDate(rs.getString("date"));
			journal.setAbstract_text(rs.getString("abstract"));
			journal.setContents(rs.getString("contents"));
			journal.setReferece(rs.getString("reference"));
		}
		
		rs.close();
		pstmt.close();
		
		return journal;
	}*/
	
	public ArrayList<Integer> getPmcId(Connection conn) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select pmc_id from journal";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		ArrayList<Integer> pmcid = new ArrayList<Integer>();
		while(rs.next())
		{
			pmcid.add(rs.getInt("pmc_id"));
		}
		
		rs.close();
		pstmt.close();
		
		return pmcid;
	}
	public void updateClickInfo(Connection conn, int pmc_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		String select_sql = "select num_of_click from journal where pmc_id = ?";
		pstmt = conn.prepareStatement(select_sql);
		pstmt.setInt(1, pmc_id);
		ResultSet rs = null;
		rs = pstmt.executeQuery();
		int click = -1;
		while(rs.next())
		{
			click = rs.getInt("num_of_click");
			System.out.println(rs.getInt("num_of_click"));
		}
		String sql = "update journal set num_of_click = 1 where pmc_id=?"; 
		if(click > 0)
		{
			sql = "update journal set num_of_click = num_of_click +1 where pmc_id = ?";
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pmc_id);
		pstmt.executeUpdate();
		rs.close();
		pstmt.close();
	}
	public int getClickInfo(Connection conn, int pmc_id) throws SQLException
	{
		PreparedStatement pstmt = null;
		String select_sql = "select num_of_click from journal where pmc_id = ?";
		pstmt = conn.prepareStatement(select_sql);
		pstmt.setInt(1, pmc_id);
		ResultSet rs = null;
		rs = pstmt.executeQuery();
		int click = 0;
		while(rs.next())
		{
			click = rs.getInt("num_of_click");
			System.out.println(rs.getInt("num_of_click"));
		}
		
		rs.close();
		pstmt.close();
		return click;
	}
	
}
