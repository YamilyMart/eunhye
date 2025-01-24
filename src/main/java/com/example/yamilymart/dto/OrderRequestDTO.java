package com.example.yamilymart.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("OrderRequestDTO")
public class OrderRequestDTO {
	private OrderDTO orderDTO;
    private List<OrderDetailDTO> orderDetails;
}