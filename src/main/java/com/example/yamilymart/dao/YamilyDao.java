package com.example.yamilymart.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.dto.OrderSearchDTO;
import com.example.yamilymart.dto.PartnerDTO;
import com.example.yamilymart.dto.ProductDTO;
import com.example.yamilymart.dto.SaleDTO;
import com.example.yamilymart.dto.StockDTO;
//import com.example.yamilymart.dto.User;
import com.example.yamilymart.dto.User;


@Mapper
public interface YamilyDao {

	@Select("select * from `order`, branch where `order`.order_sender = branch.branch_id and order.order_type = 0")
	List<OrderDTO> admin_order_list();

	List<OrderDTO> admin_order_list_search(OrderSearchDTO dto);

    List<OrderDetailDTO> admin_order_detail(String orderDetail_orderid);

	@Select("SELECT * FROM `order`, branch where `order`.order_sender = branch.branch_id and order_id = #{order_id}")
	OrderDTO admin_order_detail2(String order_id);

	int admin_order_approval(@Param("approval_type")int approval_type, @Param("order_id")String order_id);
	
	int admin_order_approval_decrease(List<OrderDetailDTO> list);

	@Select("select * from `stock`, `product` where stock.stock_productid = product.product_id and stock_type = 1 and stock_del = 0")
	List<StockDTO> admin_stock_list();

	List<StockDTO> admin_stock_list_search(@Param("searchType") int searchType,
			@Param("keyword") String keyword);

	int admin_stock_product_add(ProductDTO pdto);
	
	List<PartnerDTO> admin_stock_partner(@Param("keyword") String keyword);

	int admin_stock_add(String product_id);
	
	@Select("select count(*) from product where product_id = #{product_id}")
	int admin_stock_product_check(String product_id);

	@Select("SELECT * FROM `product`, partner where product.product_id = #{stock_productid} and product.product_partnerid = partner.partner_id")
	ProductDTO admin_stock_update_get(String stock_productid);

	int admin_stock_update_post(ProductDTO pdto);

	@Update("update stock set stock_del = 1 where stock_productid = #{product_id}")
	int admin_stock_delete(String product_id);

	@Update("update stock set stock_remain = #{stock_remain} where stock_id = #{stock_id}")
	int admin_stock_amount(@Param("stock_id") int stock_id, @Param("stock_remain") int stock_remain);

	List<SaleDTO> admin_sale_list();

	List<SaleDTO> admin_sale_list_search(@Param("keyword") String keyword,
    		@Param("startDate") String startDate,
    		@Param("endDate") String endDate);

    List<SaleDTO> admin_sale_detail(@Param("sale_branchid") String sale_branchid, @Param("sale_date") String sale_date);
    
    List<OrderDTO> admin_main_orderStatus();
    
    List<SaleDTO> admin_main_prductSale();
    
    List<SaleDTO> admin_main_monthSale();
    
    List<SaleDTO> admin_main_branchSale();
    
    
//    @Select("select * FROM hr WHERE hr_id = #{username}")
//    Optional<HrDTO> findByUsername(String username);
//
//    @Insert("insert into hr(hr_id, hr_pwd, hr_authority, hr_grade) "
//    		+ "values (#{hr_id}, #{hr_pwd}, #{hr_authority}, #{hr_grade})")
//    int save(HrDTO hrDTO);

    
    @Select("select * FROM User WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    @Insert("insert into User(username, password, role) "
    		+ "values (#{username}, #{password}, #{role})")
    int save(User user);





}
