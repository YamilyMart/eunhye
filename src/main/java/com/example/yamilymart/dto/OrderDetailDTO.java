package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("OrderDetailDTO")
public class OrderDetailDTO {
	private int orderDetail_id;
	private String orderDetail_orderid;
	private String orderDetail_productid;
	private int orderDetail_price;
	private int orderDetail_amount;
	private int orderDetail_sum;
	
	private String product_name;
	private int product_price;

}
