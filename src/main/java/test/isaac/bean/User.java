package test.isaac.bean;

import java.sql.Date;

public class User {
	public User(String uname, Date ubday, Integer usex, String umail, String upwd, String uacct) {
		super();
		this.uname = uname;
		this.ubday = ubday;
		this.usex = usex;
		this.umail = umail;
		this.upwd = upwd;
		this.uacct = uacct;
	}
	
	public User() {
		super();
	}
	
	private Integer uno;
	private String uname;
	private Date ubday;
	private Integer usex;
	private String umail;
	private String upwd;
	private String uacct;
	
	
	public Integer getUno() {
		return uno;
	}
	public void setUno(Integer uno) {
		this.uno = uno;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Date getUbday() {
		return ubday;
	}
	public void setUbday(Date ubday) {
		this.ubday = ubday;
	}
	public Integer getUsex() {
		return usex;
	}
	public void setUsex(Integer usex) {
		this.usex = usex;
	}
	public String getUmail() {
		return umail;
	}
	public void setUmail(String umail) {
		this.umail = umail;
	}
	public String getUpwd() {
		return upwd;
	}
	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}
	
	public String getUacct() {
		return uacct;
	}
	public void setUacct(String uacct) {
		this.uacct = uacct;
	}

	@Override
	public String toString() {
		return "User [uno=" + uno + ", uname=" + uname + ", ubday=" + ubday + ", usex=" + usex + ", umail=" + umail
				+ ", upwd=" + upwd + ", uacct=" + uacct + "]";
	}
}
