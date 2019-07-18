package kr.ac.kirc.ekp.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.ac.kirc.ekp.bean.Log;
import kr.ac.kirc.ekp.dao.LogDao;

public class LogControl {
	//static LogDao dao = new LogDao();
		
	public void writeLog(Log log) throws SQLException
	{
		LogDao dao = new LogDao();
		Connection conn = dao.getConnection();
		dao.writeLog(conn, log.getApi_name(),log.getRequested_id(),log.getTimestamp());
		conn.close();
	}
	public ArrayList<Log> getLogbytimestamp(String from, String to) throws Exception
	{
		LogDao dao = new LogDao();
		Connection conn = dao.getConnection();
		ArrayList<Log> results = dao.getLogbytimestamp(conn, from, to);
		conn.close();
		return results;
	}
}
