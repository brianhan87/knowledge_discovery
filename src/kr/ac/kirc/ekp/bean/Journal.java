package kr.ac.kirc.ekp.bean;

import java.util.ArrayList;

public class Journal {

	private int pmc_id;
	private String journal_title;
	private String author;
	private String title;
	private String date;
	private String abstract_text;
	private String contents; 
	private String referece;
	private String highlight_content;
	private int rank;
	private ArrayList<String> keywords;
	private double score;
	
	
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getHighlight_content() {
		return highlight_content;
	}
	public void setHighlight_content(String highlight_content) {
		this.highlight_content = highlight_content;
	}
	public int getPmc_id() {
		return pmc_id;
	}
	public void setPmc_id(int pmc_id) {
		this.pmc_id = pmc_id;
	}
	public String getJournal_title() {
		return journal_title;
	}
	public void setJournal_title(String journal_title) {
		this.journal_title = journal_title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAbstract_text() {
		return abstract_text;
	}
	public void setAbstract_text(String abstract_text) {
		this.abstract_text = abstract_text;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getReferece() {
		return referece;
	}
	public void setReferece(String referece) {
		this.referece = referece;
	} 
	
	public void setAll(int pmc_id, String journal_title, String author, String title, String date, String abstract_text, String content, String reference, String highlight_content,int rank, double score)
	{
		this.pmc_id = pmc_id;
		this.journal_title = journal_title;
		this.author = author; 
		this.title = title; 
		this.date = date; 
		this.abstract_text = abstract_text; 
		this.contents = content; 
		this.referece = reference; 
		this.highlight_content = highlight_content;
		this.rank = rank;
		this.score = score;
	}
	
}
