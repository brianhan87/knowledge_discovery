package kr.ac.kirc.ekp.config;

public class EBookInfo {
	
	private String title_1 = "Henry's Clinical Diagnosis and Management by Laboratory Methods";
	private String author_1 = "Richard A. McPherson and Matthew R. Pincus";
	private String ISBN_1 = "978-1437709742";
	private String year_1 = "August 16, 2011";
	
	private String title_2 = "Pocket Guide to Diagnostic Tests";
	private String author_2 = "Diana Nicoll, Chuanyi Mark Lu, Michael Pignone, and Stephen J. McPhee";
	private String ISBN_2 = "978-0071766258";
	private String year_2 = "July 10, 2012";
	
	private String title_3 = "Robbins & Cotran Pathologic Basis of Disease";
	private String author_3 = "Vinay Kuma, Abul K. Abbas, and Jon C. Aster";
	private String ISBN_3 = "978-1455726134";
	
	public String getTitle(int book_id)
	{
		String title = "";
		if(book_id == 1)
		{
			title = title_1;
		}
		else if(book_id == 2)
		{
			title = title_2;
		}
		else if(book_id == 3)
		{
			title = title_3;
		}
		return title;
	}
	public String getAuthor(int book_id)
	{
		String author = "";
		if(book_id == 1)
		{
			author = author_1;
		}
		else if(book_id == 2)
		{
			author = author_2;
		}
		else if(book_id == 3)
		{
			author =author_3;
		}
		return author;
	}
	public String getISBN(int book_id)
	{
		String isbn = "";
		if(book_id == 1)
		{
			isbn = ISBN_1;
		}
		else if(book_id == 2)
		{
			isbn = ISBN_2;
		}
		else if(book_id == 3)
		{
			isbn = ISBN_3;
		}
		return isbn;
	}
	public String getYear(int book_id)
	{
		String year = "";
		if(book_id == 1)
		{
			year = year_1;
		}
		else if(book_id == 2)
		{
			year = year_2;
		}
		else if(book_id == 3)
		{
			year = year_3;
		}
		return year;
	}
	
	public String getAuthor_1() {
		return author_1;
	}
	public String getTitle_1() {
		return title_1;
	}
	public void setTitle_1(String title_1) {
		this.title_1 = title_1;
	}
	public String getTitle_2() {
		return title_2;
	}
	public void setTitle_2(String title_2) {
		this.title_2 = title_2;
	}
	public String getTitle_3() {
		return title_3;
	}
	public void setTitle_3(String title_3) {
		this.title_3 = title_3;
	}
	public void setAuthor_1(String author_1) {
		this.author_1 = author_1;
	}
	public String getISBN_1() {
		return ISBN_1;
	}
	public void setISBN_1(String iSBN_1) {
		ISBN_1 = iSBN_1;
	}
	public String getYear_1() {
		return year_1;
	}
	public void setYear_1(String year_1) {
		this.year_1 = year_1;
	}
	public String getAuthor_2() {
		return author_2;
	}
	public void setAuthor_2(String author_2) {
		this.author_2 = author_2;
	}
	public String getISBN_2() {
		return ISBN_2;
	}
	public void setISBN_2(String iSBN_2) {
		ISBN_2 = iSBN_2;
	}
	public String getYear_2() {
		return year_2;
	}
	public void setYear_2(String year_2) {
		this.year_2 = year_2;
	}
	public String getAuthor_3() {
		return author_3;
	}
	public void setAuthor_3(String author_3) {
		this.author_3 = author_3;
	}
	public String getISBN_3() {
		return ISBN_3;
	}
	public void setISBN_3(String iSBN_3) {
		ISBN_3 = iSBN_3;
	}
	public String getYear_3() {
		return year_3;
	}
	public void setYear_3(String year_3) {
		this.year_3 = year_3;
	}
	private String year_3 ="July 28, 2014";
	
}
