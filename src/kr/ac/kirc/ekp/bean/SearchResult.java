package kr.ac.kirc.ekp.bean;

public class SearchResult {
	String isbn;
	int number;
	String contents;
	int rank;
	double score;
	int method_num;
	public int getMethod_num() {
		return method_num;
	}
	public void setMethod_num(int method_num) {
		this.method_num = method_num;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public SearchResult(String isbn, int number, String contents, int rank, double score,int method_num) {
		super();
		this.isbn = isbn;
		this.number = number;
		this.contents = contents;
		this.rank = rank;
		this.score = score;
		this.method_num = method_num;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
}
