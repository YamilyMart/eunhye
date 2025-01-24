package com.example.yamilymart.dto;

public class ApprovalDTO {
	private int app_id;
	private int hr_code;
	private String app_date;
	private String app_title;
	private int app_cat;
	private String app_info;
	private String app_file;
	private int boss_code;
	private int boss_code_second;
	private int app_status;
	private int app_view;
	private String app_datestart;
	private String app_dateend;
	private String app_rejection;
	
	private String writer;
	private String boss;
	
	private StaffDTO staffDTO;

	public int getApp_id() {
		return app_id;
	}

	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}

	public int getHr_code() {
		return hr_code;
	}

	public void setHr_code(int hr_code) {
		this.hr_code = hr_code;
	}

	public String getApp_date() {
		return app_date;
	}

	public void setApp_date(String app_date) {
		this.app_date = app_date;
	}

	public String getApp_title() {
		return app_title;
	}

	public void setApp_title(String app_title) {
		this.app_title = app_title;
	}

	public int getApp_cat() {
		return app_cat;
	}

	public void setApp_cat(int app_cat) {
		this.app_cat = app_cat;
	}

	public String getApp_info() {
		return app_info;
	}

	public void setApp_info(String app_info) {
		this.app_info = app_info;
	}

	public String getApp_file() {
		return app_file;
	}

	public void setApp_file(String app_file) {
		this.app_file = app_file;
	}

	public int getBoss_code() {
		return boss_code;
	}

	public void setBoss_code(int boss_code) {
		this.boss_code = boss_code;
	}

	public int getBoss_code_second() {
		return boss_code_second;
	}

	public void setBoss_code_second(int boss_code_second) {
		this.boss_code_second = boss_code_second;
	}

	public int getApp_status() {
		return app_status;
	}

	public void setApp_status(int app_status) {
		this.app_status = app_status;
	}

	public int getApp_view() {
		return app_view;
	}

	public void setApp_view(int app_view) {
		this.app_view = app_view;
	}

	public String getApp_datestart() {
		return app_datestart;
	}

	public void setApp_datestart(String app_datestart) {
		this.app_datestart = app_datestart;
	}

	public String getApp_dateend() {
		return app_dateend;
	}

	public void setApp_dateend(String app_dateend) {
		this.app_dateend = app_dateend;
	}

	public String getApp_rejection() {
		return app_rejection;
	}

	public void setApp_rejection(String app_rejection) {
		this.app_rejection = app_rejection;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	public StaffDTO getStaffDTO() {
		return staffDTO;
	}

	public void setStaffDTO(StaffDTO staffDTO) {
		this.staffDTO = staffDTO;
	}

	public ApprovalDTO(int app_id, int hr_code, String app_date, String app_title, int app_cat, String app_info,
			String app_file, int boss_code, int boss_code_second, int app_status, int app_view, String app_datestart,
			String app_dateend, String app_rejection, String writer, String boss, StaffDTO staffDTO) {
		super();
		this.app_id = app_id;
		this.hr_code = hr_code;
		this.app_date = app_date;
		this.app_title = app_title;
		this.app_cat = app_cat;
		this.app_info = app_info;
		this.app_file = app_file;
		this.boss_code = boss_code;
		this.boss_code_second = boss_code_second;
		this.app_status = app_status;
		this.app_view = app_view;
		this.app_datestart = app_datestart;
		this.app_dateend = app_dateend;
		this.app_rejection = app_rejection;
		this.writer = writer;
		this.boss = boss;
		this.staffDTO = staffDTO;
	}

	public ApprovalDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ApprovalDTO [app_id=" + app_id + ", hr_code=" + hr_code + ", app_date=" + app_date + ", app_title="
				+ app_title + ", app_cat=" + app_cat + ", app_info=" + app_info + ", app_file=" + app_file
				+ ", boss_code=" + boss_code + ", boss_code_second=" + boss_code_second + ", app_status=" + app_status
				+ ", app_view=" + app_view + ", app_datestart=" + app_datestart + ", app_dateend=" + app_dateend
				+ ", app_rejection=" + app_rejection + ", writer=" + writer + ", boss=" + boss + ", staffDTO="
				+ staffDTO + "]";
	}


}
