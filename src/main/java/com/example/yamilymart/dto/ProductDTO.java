package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("ProductDTO")
public class ProductDTO {
	private String product_id;
	private String product_partnerid;
	private String product_name;
	private int product_price;
	private String product_image;
}