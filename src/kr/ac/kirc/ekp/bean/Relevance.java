package kr.ac.kirc.ekp.bean;

public class Relevance {
	int query_id;
	String isbn;
	int passage_id;
	int relevance;
	String user;
	String time;
	public int getQuery_id() {
		return query_id;
	}
	public void setQuery_id(int query_id) {
		this.query_id = query_id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getPassage_id() {
		return passage_id;
	}
	public void setPassage_id(int passage_id) {
		this.passage_id = passage_id;
	}
	public int getRelevance() {
		return relevance;
	}
	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Relevance(int query_id, String isbn, int passage_id, int relevance, String user, String time) {
		super();
		this.query_id = query_id;
		this.isbn = isbn;
		this.passage_id = passage_id;
		this.relevance = relevance;
		this.user = user;
		this.time = time;
	}
	public Relevance() {
		// TODO Auto-generated constructor stub
	}
	
}
