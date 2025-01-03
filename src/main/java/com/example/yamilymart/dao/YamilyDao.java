package com.example.yamilymart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;


@Mapper
public interface YamilyDao {    
	
	@Select("SELECT * FROM `order`")
	List<OrderDTO> adminOrderListSelect();
	
    @Select("SELECT * FROM `orderdetail` WHERE orderDetail_orderid = #{orderDetail_orderid}")
    List<OrderDetailDTO> adminOrderDetailSelect(String orderDetail_orderid);
    
	@Select("SELECT * FROM `order` where order_id = #{order_id}")
	OrderDTO adminOrderDetail2Select(String order_id);

}
