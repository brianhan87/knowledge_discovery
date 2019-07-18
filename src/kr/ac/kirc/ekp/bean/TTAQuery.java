package kr.ac.kirc.ekp.bean;

public class TTAQuery {
	int query_id;
	String expanded;
	String answer;
	public int getQuery_id() {
		return query_id;
	}
	public void setQuery_id(int query_id) {
		this.query_id = query_id;
	}
	public String getExpanded() {
		return expanded;
	}
	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	public String getAnswer() {
		return answer;
	}
	@Override
	public String toString() {
		return "TTAQuery [query_id=" + query_id + ", expanded=" + expanded + ", answer=" + answer + "]";
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
