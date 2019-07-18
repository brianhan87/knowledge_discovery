package kr.ac.kirc.ekp.bean;

public class Log {
	private int id;
	private String api_name;
	private String requested_id;
	private String timestamp;
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApi_name() {
		return api_name;
	}
	public void setApi_name(String api_name) {
		this.api_name = api_name;
	}
	public String getRequested_id() {
		return requested_id;
	}
	public void setRequested_id(String requested_id) {
		this.requested_id = requested_id;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", api_name=" + api_name + ", requested_id=" + requested_id + ", timestamp="
				+ timestamp + "]";
	}
	public Log(String api_name, String requested_id, String timestamp) {
		super();
		this.api_name = api_name;
		this.requested_id = requested_id;
		this.timestamp = timestamp;
	}
	public Log()
	{
		
	}
	
	
}
