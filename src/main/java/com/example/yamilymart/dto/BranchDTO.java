package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("BranchDTO")
public class BranchDTO {
	private int branch_code;
	private String branch_id;
	private String branch_pwd;
	private String branch_name;
	private String branch_region;
	private String branch_address;
	private String branch_owner;
	private String branch_regdate;
	private String branch_enddate;
	private int branch_status;
	private String branch_contact;





}
