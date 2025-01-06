package com.example.yamilymart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.dto.ProductDTO;
import com.example.yamilymart.dto.StockDTO;


@Mapper
public interface YamilyDao {    
	
	@Select("SELECT * FROM `order`")
	List<OrderDTO> adminOrderListSelect();
	
    @Select("SELECT * FROM `orderdetail` WHERE orderDetail_orderid = #{orderDetail_orderid}")
    List<OrderDetailDTO> adminOrderDetailSelect(String orderDetail_orderid);
    
	@Select("SELECT * FROM `order` where order_id = #{order_id}")
	OrderDTO adminOrderDetail2Select(String order_id);
	
	@Select("SELECT * FROM `stock` where stock_type = 1")
	List<StockDTO> adminStockListSelect();
	
	int admin_stock_product_add(ProductDTO pdto);
	
	@Select("SELECT * FROM `product` where product_id = #{stock_productid}")
	ProductDTO admin_stock_update_get(String stock_productid);
	
	int admin_stock_update_post(ProductDTO pdto);


}
