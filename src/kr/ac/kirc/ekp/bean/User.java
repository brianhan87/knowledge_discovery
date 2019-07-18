package kr.ac.kirc.ekp.bean;

public class User {
	private String id;
	private String pw;
	private String name;
	private String department;
	private String chmod;
	private String task_id;
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getChmod() {
		return chmod;
	}
	public void setChmod(String chmod) {
		this.chmod = chmod;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", pw=" + pw + ", name=" + name + ", department=" + department + ", chmod=" + chmod
				+ "]";
	}
	
	
}
