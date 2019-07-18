package kr.ac.kirc.ekp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.ac.kirc.ekp.bean.Log;
import kr.ac.kirc.ekp.bean.User;
import kr.ac.kirc.ekp.config.Path;

public class LogDao {
	public Connection getConnection()
	{
		
		//final String KSLAB_DB_ADDRESS = "jdbc:mysql://ekp.kaist.ac.kr:3306/kaist_hojinchoi";
		//final String KSLAB_DB_USER = "admin";
		//final String KSLAB_DB_PW = "rudgjawltlr1!";
		
		Path path = new Path();
		final String KSLAB_DB_ADDRESS = path.getEKP_HOJIN_DB_ADDRESS();
		final String KSLAB_DB_USER = path.getEKP_DB_USER();
		final String KSLAB_DB_PW = path.getEKP_DB_PW();
	

	
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
	public boolean writeLog(Connection conn, String api_name, String requested_ip, String timestamp) throws SQLException
	{
		boolean success = false; 
		
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		String sql = "insert into api_log (api_name,requested_ip,timestamp) values (?,?,?) ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, api_name);
		pstmt.setString(2, requested_ip);
		pstmt.setString(3, timestamp);
		
		int rs = pstmt.executeUpdate();
		
		if(rs != 0)
		{
			success = true;
		}
		
		return success;
	}
	
	public ArrayList<Log> getLogbytimestamp(Connection conn, String from, String to) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from api_log where timestamp >= ? and timestamp <= ? ";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, from);
		pstmt.setString(2, to);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		while(rs.next())
		{
			Log log = new Log();
			log.setApi_name(rs.getString("api_name"));
			log.setRequested_id(rs.getString("requested_ip"));
			log.setId(rs.getInt("id"));
			log.setTimestamp(rs.getString("timestamp"));
			logs.add(log);
		}
		rs.close();
		pstmt.close();
		return logs;
	}
	
	
	
}
