package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("PartnerDTO")
public class PartnerDTO {
	private String partner_id;
	private String partner_name;
	private String partner_email;
	private String partner_manager;
	private String partner_no;
	private String partner_phone;
	private String partner_address;

}
