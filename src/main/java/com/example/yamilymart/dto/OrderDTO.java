package com.example.yamilymart.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("OrderDTO")
public class OrderDTO {
	private String order_id;
	private String order_partnerid;
	private String order_sender;
	private String order_date;
	private int order_amount;
	private int order_sum;
	private int order_type;
	private int order_status;
	private String order_manager;
	private String order_delivery;
	private String order_memo;

	private String branch_name;
	
	private int count;

	//우진
	private int orderDetail_amount;

}
