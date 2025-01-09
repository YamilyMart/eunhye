package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("OrderSearchDTO")
public class OrderSearchDTO {
	private String keyword;
	private String startDate1;
	private String endDate1;
	private String startDate2;
	private String endDate2;
	private int status;



}
