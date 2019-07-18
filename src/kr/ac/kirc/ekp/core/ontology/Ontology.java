package kr.ac.kirc.ekp.core.ontology;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Ontology{
	private boolean isselect =false;
	public String message = "";
	public int N=0;
	public String tableName="";
	public int sqlError=0;
	//public String sqlErrorMessage="";
	public String stat="";
	
	public String gen_graph_data(ArrayList<String> cols, ArrayList<ArrayList<String>> data){
		String str="";
		
		return str;
	}
	
	
	public void isselect(String sql){
		String[] terms=sql.split(" ");
		if(terms[0].toLowerCase().matches("select")) this.isselect = true;
	}

	public String getCommand(String sql){
		sql=sql.toLowerCase();
		String[] terms=sql.split(" ");
		return terms[0];
	}
	public String getTableName(String sql){
		sql=sql.replace(';',' ');
		String[] terms = sql.split(" ");
		String[] table_list=tableList("pmcc");
					
				for(int i=0;i<terms.length;i++)
					for(int j=0;j<table_list.length;j++)
						if(terms[i].matches("pmcc."+table_list[j])){
						
							return terms[i];
						}
		return null;
	}
	
	public String getStatus(String sql){
		String status;
		String[] terms=sql.split(" ");
		status=terms[0].toLowerCase();
		return status;
	}
	
	public String[] tableList(String db){
		String[] table_list = null;
		Connection con = null;
		java.sql.Statement st = null;

		ResultSet rs = null;
		ArrayList<String> resultSet = new ArrayList<String>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://kirc.kaist.ac.kr?characterEncoding=utf-8","root","kslab!1204");
			st = con.createStatement();
			st.executeQuery("show tables from "+db+";");
			rs = st.getResultSet();	
		}catch (SQLException sqex){
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
			System.out.println("SQLErrorCode: "+sqex.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} 
		
		try {
			while(rs.next()){
				resultSet.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table_list=resultSet.toArray(new String[resultSet.size()]);
		
		
		return table_list;
	}
	
	
	public ResultSet ontology(String sql){

		isselect(sql);
		this.tableName=getTableName(sql);

		String getStatus = getStatus(sql);
		Connection con = null;
		java.sql.Statement st = null;

		ResultSet rs = null;
		this.stat = getStatus(sql);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://kirc.kaist.ac.kr?characterEncoding=utf-8","root","kslab!1204");

			if(this.isselect){
				st = con.createStatement();
				st.executeQuery(sql);
				this.message = this.N+" rows have been "+getStatus(sql)+"ed";
				this.sqlError=0;
			}
			else{				//execute select once more to show the results
				st = con.createStatement();
				this.N=st.executeUpdate(sql);
				if(getStatus(sql).matches("delete")) this.message = this.N+" rows have been "+getStatus(sql)+"d";
				else this.message = this.N+" rows have been "+getStatus(sql)+"ed";
				if(getCommand(sql).matches("insert") || getCommand(sql).matches("delete") || getCommand(sql).matches("load")){
				st = con.createStatement();
				st.executeQuery("select * from "+this.tableName+";");
				}
				this.sqlError=0;
			}
				rs = st.getResultSet();	
		}catch (SQLException sqex){
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
			System.out.println("SQLErrorCode: "+sqex.getErrorCode());
			this.message=sqex.getMessage();
			this.sqlError=1;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			this.message="Unknown Error";
			this.sqlError=1;
		} 
		return rs;

}
}