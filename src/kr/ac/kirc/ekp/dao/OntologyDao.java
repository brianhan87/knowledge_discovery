package kr.ac.kirc.ekp.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.ac.kirc.ekp.bean.EBook;
import kr.ac.kirc.ekp.bean.Topic;
import kr.ac.kirc.ekp.config.Path;



public class OntologyDao {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		OntologyDao dao = new OntologyDao();
		dao.run();
	}
	
	public void run() throws SQLException
	{
		Connection conn = this.getConnection();
		this.updateSCRIPT(conn);
	}
	public Connection getConnection()
	{
		
		//final String KSLAB_DB_ADDRESS = "jdbc:mysql://kirc.kaist.ac.kr:3306/ekp_db";
		//final String KSLAB_DB_USER = "root";
		//final String KSLAB_DB_PW = "kslab!1204";
		
		Path path = new Path();
		final String KSLAB_DB_ADDRESS = path.getONTOLOGY_DB_ADDRESS();
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
		//System.out.println("Connection closed.");
		conn.close();
	}
	
	public ArrayList<Topic> getTermsfromManualDictionary(Connection conn, ArrayList<String> nouns) throws Exception
	{
		ArrayList<Topic> topics = new ArrayList<Topic>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from (select distinct EKP_CODE, term from code_term_reportid where term=?) as A	inner join	(select * from keyword) as B on A.EKP_CODE = B.EKP_CODE";
		System.out.println("nouns: " + nouns.toString());
		for (int i=0;i<nouns.size();i++)
		{
			System.out.println("noun:" +nouns.get(i));
			//String sql = "select * from 3_robbins_paragraph";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nouns.get(i));
			//pstmt.setString(1, movie_id);
			rs = pstmt.executeQuery();
			
			
			while(rs.next())
			{
				System.out.println("Hello: " + rs.getString("KEYWORD"));
				Topic t  = new Topic();
				t.setTitle(rs.getString("KEYWORD"));
				t.setMust(false);
				topics.add(t);
				//System.out.println("Current nouns detected: " + noun);
				//codes.add(rs.getString("KEYWORD"));
			}
			
		}
		rs.close();
		pstmt.close();
		return topics;
	}
	
	public ArrayList<String> getAllScripts(Connection conn) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from medical_report";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<String> codes = new ArrayList<String>();
		while(rs.next())
		{
			codes.add(rs.getString("SCRIPT"));
		}
		rs.close();
		pstmt.close();
		return codes;
	}
	
	public ArrayList<String> getEKPCODE(Connection conn, String noun) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from (select * from keyword) as A left outer join (select * from kr_syn) as B on A.EKP_CODE = B.EKP_CODE_KR where KR_SYN=? or KEYWORD=?";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, noun);
		pstmt.setString(2, noun);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<String> codes = new ArrayList<String>();
		while(rs.next())
		{
			System.out.println("Current nouns detected: " + noun);
			codes.add(rs.getString("EKP_CODE"));
		}
		rs.close();
		pstmt.close();
		return codes;
	}
	public ArrayList<String> getEKPCODEofMUSTType(Connection conn, String noun) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//String sql = "select * from (select * from keyword) as A left outer join (select * from kr_syn) as B on A.EKP_CODE = B.EKP_CODE_KR where (KR_SYN=? or KEYWORD=?) and (TYPE=? or TYPE=?)";
		
		String sql = "select * from (select * from keyword) as C	inner join	(select * from (select * from en_syn) as A left outer join (select * from kr_syn) as B on A.EKP_CODE_EN = B.EKP_CODE_KR where (KR_SYN=? or en_syn=?)) as D	 on C.EKP_CODE = D.EKP_CODE_EN where C.TYPE=? or C.TYPE=?";
		
		
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, noun);
		pstmt.setString(2, noun);
		pstmt.setString(3, "System");
		pstmt.setString(4, "Component");
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<String> codes = new ArrayList<String>();
		while(rs.next())
		{
			//System.out.println("Current nouns detected: " + noun);
			codes.add(rs.getString("EKP_CODE"));
		}
		rs.close();
		pstmt.close();
		return codes;
	}
	public ArrayList<String> getEKPCODEofAdditionalType(Connection conn, String noun) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//String sql = "select * from (select * from keyword) as A left outer join (select * from kr_syn) as B on A.EKP_CODE = B.EKP_CODE_KR where (KR_SYN=? or KEYWORD=?) and (TYPE<>? and TYPE<>?)";
		
		String sql = "select * from (select * from keyword) as C	inner join	(select * from (select * from en_syn) as A left outer join (select * from kr_syn) as B on A.EKP_CODE_EN = B.EKP_CODE_KR where (KR_SYN=? or en_syn=?)) as D	 on C.EKP_CODE = D.EKP_CODE_EN where C.TYPE<>? and C.TYPE<>?";
		
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, noun);
		pstmt.setString(2, noun);
		pstmt.setString(3, "System");
		pstmt.setString(4, "Component");
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<String> codes = new ArrayList<String>();
		while(rs.next())
		{
			//System.out.println("Current nouns detected: " + noun);
			codes.add(rs.getString("EKP_CODE"));
		}
		rs.close();
		pstmt.close();
		return codes;
	}
	
	public ArrayList<String> getKeywords(Connection conn, String ekp_code) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from keyword where EKP_CODE=?";
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,ekp_code);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<String> queries = new ArrayList<String>();
		while(rs.next())
		{
			queries.add(rs.getString("keyword"));
			
			//text.add(title);
		}
		rs.close();
		pstmt.close();
		return queries;
	}
	public ArrayList<String> getQueries(Connection conn, String ekp_code) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from en_syn where EKP_CODE_EN=?";
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,ekp_code);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<String> queries = new ArrayList<String>();
		while(rs.next())
		{
			queries.add(rs.getString("EN_SYN"));
			
			//text.add(title);
		}
		rs.close();
		pstmt.close();
		
		return queries;
	}
	public int getExistingDiagnosis(Connection conn, String query) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//System.out.println("QUERY IS? : " +query);
		String sql = "select * from medical_report where SCRIPT like ?";
		String trimmed_query = query.replace("\"", "");
		trimmed_query = trimmed_query.trim();
		if(trimmed_query.equals(""))
		{
			return -1;
		}
		trimmed_query = "%" + trimmed_query + "%";
		//System.out.println("INPUTED QUERY: " +trimmed_query);
		//String sql = "select * from 3_rob bins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,trimmed_query);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		int report_id = -1; 
		while(rs.next())
		{
			report_id = Integer.parseInt(rs.getString("ID"));
			
			//text.add(title);
		}
		
		rs.close();
		pstmt.close();
		return report_id;
	}
	public ArrayList<String> getEKPCODEFromPre_madeDiagnosis(Connection conn, int report_id) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from code_term_reportid where REPORT_ID=?";
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,report_id);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<String> queries = new ArrayList<String>();
		while(rs.next())
		{
			queries.add(rs.getString("EKP_CODE"));
			System.out.println("REPORT_ID: " + report_id);
			//text.add(title);
		}
		rs.close();
		pstmt.close();
		return queries;
	}
	
	public void updateSCRIPT(Connection conn) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from medical_report";
		String update_sql = "update medical_report set script_trimmed = ? where ID=?";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		ArrayList<String> id = new ArrayList<String>();
		while(rs.next())
		{
			id.add(rs.getString("ID"));
			
			String text = rs.getString("SCRIPT");
			text = text.trim();
			text = text.replace(" ", "");
			text = text.replace("\"", "");
			
			PreparedStatement pstmt2 = null;
			pstmt2 = conn.prepareStatement(update_sql);
			pstmt2.setString(1, text);
			pstmt2.setString(2, rs.getString("ID"));
			pstmt2.executeUpdate();
			pstmt2.close();
			//System.out.println("REPORT_ID: " + report_id);
			//text.add(title);
		}
		rs.close();
		pstmt.close();
		
	}
	
	
}
