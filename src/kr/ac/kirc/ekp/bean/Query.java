package kr.ac.kirc.ekp.bean;

public class Query {
	int query_id;
	public int getQuery_id() {
		return query_id;
	}
	public void setQuery_id(int query_id) {
		this.query_id = query_id;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getQuery_original() {
		return query_original;
	}
	public void setQuery_original(String query_original) {
		this.query_original = query_original;
	}
	String query;
	String query_original;
	
	
}
