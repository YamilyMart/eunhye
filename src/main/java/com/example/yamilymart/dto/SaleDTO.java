 package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("SaleDTO")
public class SaleDTO {
	private int sale_code;
	private String sale_branchid;
	private String sale_productid;
	private int sale_amount;
	private String sale_date;
	private int sale_sum;

	private String branch_name;
	private String product_name;
	private String partner_name;
	private int product_price;
	private int count;
	private String product_image;
	
	private int sale_year;
	private int sale_month;

}
