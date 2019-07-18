package kr.ac.kirc.ekp.user;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.*;

import kr.ac.kirc.ekp.bean.Log;
import kr.ac.kirc.ekp.bean.User;
import kr.ac.kirc.ekp.dao.LogDao;
import kr.ac.kirc.ekp.dao.userDao;

public class UserControl {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		UserControl control = new UserControl();
		try {
			control.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	//static userDao dao = new userDao();
	//static Connection conn = dao.getConnection();
	public void run() throws Exception
	{
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		
		//user add test
		String id = "ham1212";
		String pw= "ham1212";
		String name = "한기준";
		String department = "지식서비스공학과";
		String chmod = "yyy";
		int s = this.addAccount(id, pw, name, department, chmod);
		System.out.println(s);
		
		//login test success
		s = this.login(id, pw);
		System.out.println(s);
		
		//login test fail
		s = this.login(id, "han1212");
		System.out.println(s);
		
		//get account success
		User user = dao.getAccount(conn, id);
		System.out.println("Current user: " + user.toString());
		
		//update pw
		s= this.changePassward(id, "keejun");
		
		//update chmod
		s= this.changeChmod( id, "nnn");
		
		//check ID test 
		int returned_val = this.checkId("hello");
		System.out.println(returned_val);
		
		//log test
		LogDao ldao = new LogDao();
		Connection conn2 = ldao.getConnection();
		ldao.writeLog(conn2, "no_api", "143.248.90.86", "20161011_157897");
		
		ArrayList<Log> log = ldao.getLogbytimestamp(conn2, "20161010_000000", "20161011_200000");
		for(int i=0;i<log.size();i++)
		{
			System.out.println(log.get(i).toString());
		}
		
		
	}
	// return 0 for success, 1 for too short id, 2 for duplicated id
	public int checkId(String id) throws Exception
	{
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		int val = 0;
		
		if(id.length() <= 6)
		{
			val = 1;
		}
		
		boolean isExist = dao.checkforDuplicatedID(conn, id);
		if(isExist)
		{
			val = 2; 
		}
		
		conn.close();
		return val;
		
	}
	
	// return 0 for success, 1 for invalid input
	public int checkPw(String input) throws SQLException
	{
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		int isValid = 0;
		//문자, 숫자, 특수문자의 조합인지 확인
		Pattern p = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])");
		Matcher m = p.matcher(input);
		if (m.find()){
		    System.err.println(input + " 은 패턴에 해당함!!!");
		}else{
		  System.err.println(input + " 은 패턴에 어긋남!!!");
		  isValid = 1;
		}
		conn.close();
		return isValid;
	}
	// return 0 for success, 1 for invalid input
	public int login(String id, String pw) throws Exception
	{
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		int isValid = 0;
		//String msg = id + " is now logged in.";
		if(!dao.login(conn, id, pw))
		{
			isValid = 1;
			//msg = id + " is falied to log in.";
		}
		conn.close();
		return isValid;		
	}
	
	public int changePassward(String id, String new_pw) throws SQLException
	{
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		int isValid = 0;
		//String msg = id + "'s passward is changed.";
		isValid = this.checkPw(new_pw);
		if(isValid !=1 && !dao.updatePassword(conn, id, new_pw))
		{
			//msg = id + "'s passward is failed to change.";
			isValid = 2;
		}
		conn.close();
		return isValid;		
	}
	
	
	public int changeChmod(String id, String chmod) throws SQLException
	{
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		int isValid = 0;
		//String msg = id + "'s authorization is changed.";
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("yyy");
		modes.add("yyn");
		modes.add("yny");
		modes.add("nyy");
		modes.add("ynn");
		modes.add("nyn");
		modes.add("nny");
		modes.add("nnn");
		
		if(!modes.contains(chmod))
		{
			isValid = 1;
		}
		
		if(isValid !=1 && !dao.updateChmod(conn, id, chmod))
		{
			isValid = 2;
			//msg = id + "'s authorization is failed to change.";
		}
		conn.close();
		return isValid;		
	}
	
	public int addAccount(String id, String pw, String name, String department, String chmod) throws SQLException
	{
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		int isValid = 0;
		User user = new User();
		user.setId(id);
		user.setPw(pw);
		user.setName(name);
		user.setDepartment(department);
		user.setChmod(chmod);
		
		String msg = "User is successfully created!";
		if(!dao.createAccount(conn, user))
		{
			msg = "User creation is unsuccessful. Contact the system administrator.";
			isValid = 1;
		}
		conn.close();
		return isValid;		
	}
	
	public User getAccount(String id) throws Exception
	{
		userDao dao = new userDao();
		Connection conn = dao.getConnection();
		User user = dao.getAccount(conn, id);
		conn.close();
		return user;
	}
	
}
