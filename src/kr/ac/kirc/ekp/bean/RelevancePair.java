package kr.ac.kirc.ekp.bean;

public class RelevancePair {
	int passage_id;
	String isbn;
	int query_id;
	int relevance_a;
	int relevance_b;
	public int getPassage_id() {
		return passage_id;
	}
	public void setPassage_id(int passage_id) {
		this.passage_id = passage_id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getQuery_id() {
		return query_id;
	}
	public void setQuery_id(int query_id) {
		this.query_id = query_id;
	}
	public int getRelevance_a() {
		return relevance_a;
	}
	public void setRelevance_a(int relevance_a) {
		this.relevance_a = relevance_a;
	}
	public int getRelevance_b() {
		return relevance_b;
	}
	public void setRelevance_b(int relevance_b) {
		this.relevance_b = relevance_b;
	}
	public RelevancePair(int passage_id, String isbn, int query_id, int relevance_a, int relevance_b) {
		super();
		this.passage_id = passage_id;
		this.isbn = isbn;
		this.query_id = query_id;
		this.relevance_a = relevance_a;
		this.relevance_b = relevance_b;
	}
	
}
