package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("StockDTO")
public class StockDTO {
	private String stock_id;
	private String stock_productid;
	private String stock_branchid;
	private int stock_remain;
	private int stock_type;
}