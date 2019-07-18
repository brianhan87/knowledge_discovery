package kr.ac.kirc.ekp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.ac.kirc.ekp.bean.User;
import kr.ac.kirc.ekp.config.Path;

public class userDao {
	
	public Connection getConnection()
	{
		
		//final String KSLAB_DB_ADDRESS = "jdbc:mysql://ekp.kaist.ac.kr:3306/kaist_munyi";
		//final String KSLAB_DB_USER = "admin";
		//final String KSLAB_DB_PW = "rudgjawltlr1!";
		
		Path path = new Path();
		final String KSLAB_DB_ADDRESS = path.getEKP_MUNYI_DB_ADDRESS();
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
	
	public boolean login(Connection conn, String id, String pw) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where id=? and pw=? ";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		boolean login =false;
		String task_ids ="none"; 
		while(rs.next())
		{
			login=true;
		}
		rs.close();
		pstmt.close();
		return login;
	}
	
	public String loginReturnsTaskIdswithoutPW(Connection conn, String id) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where id=?";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		String task_ids ="none"; 
		while(rs.next())
		{
			task_ids =rs.getString("task_id");
		}
		rs.close();
		pstmt.close();
		return task_ids;
	}
	
	
	public String loginReturnsTaskIds(Connection conn, String id, String pw) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where id=? and pw=? ";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		String task_ids ="none"; 
		while(rs.next())
		{
			task_ids =rs.getString("task_id");
		}
		rs.close();
		pstmt.close();
		return task_ids;
	}
	
	public String loginReturnsTaskIdsforTTA(Connection conn, String id, String pw) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where id=? and pw=? ";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		String task_ids ="none"; 
		while(rs.next())
		{
			task_ids =rs.getString("task_id");
		}
		
		if(!id.equals("tta_tester"))
		{
			task_ids = "none";
		}
		rs.close();
		pstmt.close();
		return task_ids;
	}
	
	public boolean checkforDuplicatedID(Connection conn, String id) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where id=?";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		boolean isExist = false; 
		while(rs.next())
		{
			isExist = true;
		}
		rs.close();
		pstmt.close();
		return isExist;
	}
	
	public User getAccount(Connection conn, String id) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where id=?";
		
		//String sql = "select * from 3_robbins_paragraph";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		//pstmt.setString(1, movie_id);
		rs = pstmt.executeQuery();
		
		User user = new User();
		while(rs.next())
		{
			user.setId(rs.getString("id"));
			user.setPw(rs.getString("pw"));
			user.setDepartment(rs.getString("department"));
			user.setName(rs.getString("name"));
			user.setChmod(rs.getString("chmod"));
		}
		rs.close();
		pstmt.close();
		return user;
	}
	
	public boolean createAccount(Connection conn, User user) throws SQLException
	{
		boolean success = false; 
		
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		String sql = "insert into user values (?,?,?,?,?) ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getId());
		pstmt.setString(2, user.getPw());
		pstmt.setString(3, user.getName());
		pstmt.setString(4, user.getDepartment());
		pstmt.setString(5, user.getChmod());
		
		int rs = pstmt.executeUpdate();
		
		if(rs != 0)
		{
			success = true;
		}
		
		return success;
	}
	
	public boolean updateStatus(Connection conn, String id) throws SQLException
	{
		boolean success = false; 
		
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		String update_sql = "update user set status = ? where id=?";
		pstmt = conn.prepareStatement(update_sql);
		pstmt.setInt(1,1);
		pstmt.setString(2, id);
		int rs = pstmt.executeUpdate();
		pstmt.close();
		
		if(rs != 0)
		{
			success = true;
		}
		
		return success;
	}
	
	public boolean updatePassword(Connection conn, String id, String pw) throws SQLException
	{
		boolean success = false; 
		
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		String update_sql = "update user set pw = ? where id=?";
		pstmt = conn.prepareStatement(update_sql);
		pstmt.setString(1, pw);
		pstmt.setString(2, id);
		int rs = pstmt.executeUpdate();
		pstmt.close();
		
		if(rs != 0)
		{
			success = true;
		}
		
		return success;
	}
	
	public boolean updateChmod(Connection conn, String id, String chmod) throws SQLException
	{
		boolean success = false; 
		
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		String update_sql = "update user set chmod = ? where id=?";
		pstmt = conn.prepareStatement(update_sql);
		pstmt.setString(1, chmod);
		pstmt.setString(2, id);
		int rs = pstmt.executeUpdate();
		pstmt.close();
		
		if(rs != 0)
		{
			success = true;
		}
		
		return success;
	}
	
	
	
}
