package com.example.yamilymart.dto;

public class StaffDTO {
	private int hr_code;
	private String hr_id;
	private String hr_pwd;
	private int hr_authority;
	private String hr_name;
	private String hr_joindate;
	private int hr_grade;
	private int hr_pdo;
	private String hr_sybname;
	private String hr_oriname;
	
	
	public int getHr_code() {
		return hr_code;
	}
	public void setHr_code(int hr_code) {
		this.hr_code = hr_code;
	}
	public String getHr_id() {
		return hr_id;
	}
	public void setHr_id(String hr_id) {
		this.hr_id = hr_id;
	}
	public String getHr_pwd() {
		return hr_pwd;
	}
	public void setHr_pwd(String hr_pwd) {
		this.hr_pwd = hr_pwd;
	}
	public int getHr_authority() {
		return hr_authority;
	}
	public void setHr_authority(int hr_authority) {
		this.hr_authority = hr_authority;
	}
	public String getHr_name() {
		return hr_name;
	}
	public void setHr_name(String hr_name) {
		this.hr_name = hr_name;
	}
	public String getHr_joindate() {
		return hr_joindate;
	}
	public void setHr_joindate(String hr_joindate) {
		this.hr_joindate = hr_joindate;
	}
	public int getHr_grade() {
		return hr_grade;
	}
	public void setHr_grade(int hr_grade) {
		this.hr_grade = hr_grade;
	}
	public int getHr_pdo() {
		return hr_pdo;
	}
	public void setHr_pdo(int hr_pdo) {
		this.hr_pdo = hr_pdo;
	}
	public String getHr_sybname() {
		return hr_sybname;
	}
	public void setHr_sybname(String hr_sybname) {
		this.hr_sybname = hr_sybname;
	}
	public String getHr_oriname() {
		return hr_oriname;
	}
	public void setHr_oriname(String hr_oriname) {
		this.hr_oriname = hr_oriname;
	}
	public StaffDTO(int hr_code, String hr_id, String hr_pwd, int hr_authority, String hr_name, String hr_joindate,
			int hr_grade, int hr_pdo, String hr_sybname, String hr_oriname) {
		super();
		this.hr_code = hr_code;
		this.hr_id = hr_id;
		this.hr_pwd = hr_pwd;
		this.hr_authority = hr_authority;
		this.hr_name = hr_name;
		this.hr_joindate = hr_joindate;
		this.hr_grade = hr_grade;
		this.hr_pdo = hr_pdo;
		this.hr_sybname = hr_sybname;
		this.hr_oriname = hr_oriname;
	}
	public StaffDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "StaffDTO [hr_code=" + hr_code + ", hr_id=" + hr_id + ", hr_pwd=" + hr_pwd + ", hr_authority="
				+ hr_authority + ", hr_name=" + hr_name + ", hr_joindate=" + hr_joindate + ", hr_grade=" + hr_grade
				+ ", hr_pdo=" + hr_pdo + ", hr_sybname=" + hr_sybname + ", hr_oriname=" + hr_oriname + "]";
	}
	
	
	
	
	
}
