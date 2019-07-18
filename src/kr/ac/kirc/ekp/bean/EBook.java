package kr.ac.kirc.ekp.bean;

public class EBook {
	private String title;
	private String author; 
	private String isbn;
	private String year;
	private int number = 0;
	private int part_num = 0;
	private int chap_num = 0;
	private int rank;
	private String highlight_content;
	private String contents;
	private String previous_content;
	private String next_content;
	private double score = 0;

	private String image_url; 
	private String book_contents_tagged;
	private String book_contents_stats;
	private int page_num;
	
	
	
	
	public int getPage_num() {
		return page_num;
	}

	public void setPage_num(int page_num) {
		this.page_num = page_num;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getBook_contents_tagged() {
		return book_contents_tagged;
	}

	public void setBook_contents_tagged(String book_contents_tagged) {
		this.book_contents_tagged = book_contents_tagged;
	}

	public String getBook_contents_stats() {
		return book_contents_stats;
	}

	public void setBook_contents_stats(String book_contents_stats) {
		this.book_contents_stats = book_contents_stats;
	}

	public String getPrevious_content() {
		return previous_content;
	}

	public void setPrevious_content(String previous_content) {
		this.previous_content = previous_content;
	}

	public String getNext_content() {
		return next_content;
	}

	public void setNext_content(String next_content) {
		this.next_content = next_content;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getHighlight_content() {
		return highlight_content;
	}

	public void setHighlight_content(String highlight_content) {
		this.highlight_content = highlight_content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPart_num() {
		return part_num;
	}

	public void setPart_num(int part_num) {
		this.part_num = part_num;
	}

	public int getChap_num() {
		return chap_num;
	}

	public void setChap_num(int chap_num) {
		this.chap_num = chap_num;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
