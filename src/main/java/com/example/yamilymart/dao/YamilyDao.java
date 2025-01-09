package com.example.yamilymart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.dto.OrderSearchDTO;
import com.example.yamilymart.dto.ProductDTO;
import com.example.yamilymart.dto.SaleDTO;
import com.example.yamilymart.dto.StockDTO;


@Mapper
public interface YamilyDao {

	@Select("select * from `order`, branch where `order`.order_sender = branch.branch_id")
	List<OrderDTO> admin_order_list();

	List<OrderDTO> admin_order_list_search(OrderSearchDTO dto);

    @Select("SELECT * FROM `orderdetail`, `product` WHERE orderDetail_orderid = #{orderDetail_orderid} and orderdetail.orderDetail_productid = product.product_id")
    List<OrderDetailDTO> admin_order_detail(String orderDetail_orderid);

	@Select("SELECT * FROM `order`, branch where `order`.order_sender = branch.branch_id and order_id = #{order_id}")
	OrderDTO admin_order_detail2(String order_id);

	@Select("select * from `stock`, `product` where stock.stock_productid = product.product_id and stock_type = 1")
	List<StockDTO> admin_stock_list();

	List<StockDTO> admin_stock_list_search(@Param("searchType") int searchType,
			@Param("keyword") String keyword);

	int admin_stock_product_add(ProductDTO pdto);

	int admin_stock_add(String product_id);

	@Select("SELECT * FROM `product` where product_id = #{stock_productid}")
	ProductDTO admin_stock_update_get(String stock_productid);

	int admin_stock_update_post(ProductDTO pdto);

	@Delete("delete from product where product_id = #{product_id}")
	int admin_stock_delete(String product_id);

	@Update("update stock set stock_remain = #{stock_remain} where stock_id = #{stock_id}")
	int admin_stock_amount(@Param("stock_id") int stock_id, @Param("stock_remain") int stock_remain);

	List<SaleDTO> admin_sale_list();

	List<SaleDTO> admin_sale_list_search(@Param("keyword") String keyword,
    		@Param("startDate") String startDate,
    		@Param("endDate") String endDate);

    List<SaleDTO> admin_sale_detail(@Param("sale_branchid") String sale_branchid, @Param("sale_date") String sale_date);


}
