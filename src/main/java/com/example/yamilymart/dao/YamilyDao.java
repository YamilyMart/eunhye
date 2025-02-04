package com.example.yamilymart.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.yamilymart.dto.ApprovalDTO;
import com.example.yamilymart.dto.BranchDTO;
import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.dto.OrderSearchDTO;
import com.example.yamilymart.dto.PartnerDTO;
import com.example.yamilymart.dto.ProductDTO;
import com.example.yamilymart.dto.QuotationDTO;
import com.example.yamilymart.dto.SaleDTO;
import com.example.yamilymart.dto.StaffDTO;
import com.example.yamilymart.dto.StockDTO;
//import com.example.yamilymart.dto.User;
import com.example.yamilymart.dto.User;


@Mapper
public interface YamilyDao {
	
	//주문목록 갯수 조회
	int countFilteredOrders(Map<String, Object> params);
	
	//재고목록 갯수 조회
	int countFilteredStock(Map<String, Object> params);
	
	//매출목록 갯수 조회
	int countFilteredSale(Map<String, Object> params);

	@Select("select * from `order`, branch where `order`.order_sender = branch.branch_id and `order`.order_type = 0 ORDER BY `order`.order_date DESC LIMIT #{start}, #{pageSize}")
	List<OrderDTO> admin_order_list(Map<String, Object> params);

	List<OrderDTO> admin_order_list_search(Map<String, Object> params);

    List<OrderDetailDTO> admin_order_detail(String orderDetail_orderid);

	@Select("SELECT * FROM `order`, branch where `order`.order_sender = branch.branch_id and order_id = #{order_id}")
	OrderDTO admin_order_detail2(String order_id);

	int admin_order_approval(@Param("approval_type")int approval_type, @Param("order_id")String order_id);
	
	int admin_order_approval_decrease(List<OrderDetailDTO> list);

	@Select("select * from `stock`, `product` where stock.stock_productid = product.product_id and stock_type = 1 and stock_del = 0 ORDER BY `stock`.stock_id DESC LIMIT #{start}, #{pageSize}")
	List<StockDTO> admin_stock_list(Map<String, Object> params);

	List<StockDTO> admin_stock_list_search(Map<String, Object> params);

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

	List<SaleDTO> admin_sale_list(Map<String, Object> params);

	List<SaleDTO> admin_sale_list_search(Map<String, Object> params);

    List<SaleDTO> admin_sale_detail(@Param("sale_branchid") String sale_branchid, @Param("sale_date") String sale_date);
    
    List<OrderDTO> admin_main_orderStatus();
    
    List<SaleDTO> admin_main_prductSale();
    
    List<SaleDTO> admin_main_monthSale();
    
    List<SaleDTO> admin_main_branchSale();
    
    @Select("select * FROM User WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    @Insert("insert into User(username, password, role) "
    		+ "values (#{username}, #{password}, #{role})")
    int userSave(User user);
    
    @Update("update user set password = #{password} where username = #{username}")
    int userUpdate(User user);
    
    @Select("select hr_name from hr where hr_id = #{username}")
    String hrNameSelect(String username);

    
    //우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
  	//우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
  	//우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    
    /*본사 거래처 목록*/
	 @Select("SELECT * FROM partner")
	    List<PartnerDTO> getPartnerList();
	
	 /*본사 주문 목록*/
	 @Select("SELECT * FROM `order`")
	 List<OrderDTO> getOrderList();
	 
	 /*본사 지점 목록*/
	 @Select("SELECT * FROM branch")
	    List<BranchDTO> getBranchList();
	 
	 /*본사 지점 상세 보기*/
	 @Select("SELECT * FROM branch WHERE branch_code = #{branch_code}")
	 BranchDTO getBranchDetails(String branch_code);
	 
	 /*본사 지점 추가*/
		/*
		 * @Insert( "INSERT INTO branch " +
		 * "(branch_id, branch_pwd, branch_owner, branch_name, branch_contact, branch_region, branch_address, branch_status, branch_regdate) "
		 * + "VALUES " +
		 * "(#{branch_id}, #{branch_pwd}, #{branch_owner}, #{branch_name}, #{branch_contact}, #{branch_region}, #{branch_address}, 0, now())"
		 * )
		 */
	 @Insert(
			    "INSERT INTO branch " +
			    "(branch_id, branch_owner, branch_name, branch_contact, branch_region, branch_address, branch_status, branch_regdate) " +
			    "VALUES " +
			    "(#{branch_id}, #{branch_owner}, #{branch_name}, #{branch_contact}, #{branch_region}, #{branch_address}, 0, now())"
		)
	 int addBranch(BranchDTO branch) throws Exception;
	 
	 /*본사 지점 수정*/
		/*
		 * @Update("UPDATE branch" + "	    SET branch_id = #{branch_id}," +
		 * "	        branch_pwd = #{branch_pwd}," +
		 * "	        branch_name = #{branch_name}," +
		 * "	        branch_owner = #{branch_owner}," +
		 * "	        branch_contact = #{branch_contact}," +
		 * "	        branch_region = #{branch_region}," +
		 * "	        branch_address = #{branch_address}" +
		 * "	    WHERE branch_code = #{branch_code}")
		 */
	 @Update("UPDATE branch"
		 		+ "	    SET branch_id = #{branch_id},"
		 		+ "	        branch_name = #{branch_name},"
		 		+ "	        branch_owner = #{branch_owner},"
		 		+ "	        branch_contact = #{branch_contact},"
		 		+ "	        branch_region = #{branch_region},"
		 		+ "	        branch_address = #{branch_address}"
		 		+ "	    WHERE branch_code = #{branch_code}")
	 int updateBranch(BranchDTO branch) throws Exception;
	 
	 /*본사 지점 폐업*/
	 @Update("UPDATE branch " +
		        "SET branch_id = '-', " +
		        "    branch_pwd = '-', " +
		        "    branch_status = 1, " +
		        "    branch_enddate = now() " +
		        "WHERE branch_code = #{branch_code}")
	int closedBranch(BranchDTO branch) throws Exception;
	 
	/*본사 지점 검색 필터*/
	 List<BranchDTO> bList(Map<String, String> params);
	 
	 /*본사 지점 아이디 중복 검사*/
	 @Select("SELECT COUNT(*) FROM branch WHERE branch_id = #{branch_id}")
	 int idCheck(@Param("branch_id") String branch_id) throws Exception;
	 
	 /*거래처 상세보기*/
	 @Select("SELECT * FROM partner WHERE partner_id = #{partner_id}")
	 PartnerDTO getPartnerDetails(String partner_id);
	 
	 /*거래처 등록*/
	 @Insert(
		    "INSERT INTO partner " +
		    "(partner_id, partner_name, partner_email, partner_manager, partner_no, partner_phone, partner_address) " +
		    "VALUES " +
		    "(#{partner_id}, #{partner_name}, #{partner_email}, #{partner_manager}, #{partner_no}, #{partner_phone}, #{partner_address})"
	 )
	 int addPartner(PartnerDTO partner) throws Exception;
	 
	 /*본사 거래처 아이디 중복 검사*/
	 @Select("SELECT COUNT(*) FROM partner WHERE partner_id = #{partner_id}")
	 int partnerIdCheck(@Param("partner_id") String partner_id) throws Exception;
	 
	 /*본사 거래처 수정*/
	 @Update("UPDATE partner"
	 		+ "	    SET partner_name = #{partner_name},"
	 		+ "	        partner_manager = #{partner_manager},"
	 		+ "	        partner_phone = #{partner_phone},"
	 		+ "	        partner_email = #{partner_email},"
	 		+ "	        partner_no = #{partner_no},"
	 		+ "	        partner_address = #{partner_address}"
	 		+ "	    WHERE partner_id = #{partner_id}")
	 int updatePartner(PartnerDTO partner) throws Exception;
	 
	 /*본사 거래처 목록 검색 필터*/
	 List<PartnerDTO> pList(Map<String, String> params);
	 
	 @Delete({
		    "<script>",
		    "DELETE FROM partner WHERE partner_id IN",
		    "<foreach collection='partnerIds' item='id' open='(' separator=',' close=')'>",
		    "#{id}",
		    "</foreach>",
		    "</script>"
		})
		int deletePartners(@Param("partnerIds") String[] partnerIds) throws Exception;
	 
	 /*서류 관리*/
	 /*서류 관리 목록*/
	 @Select("SELECT * FROM quotation ORDER BY quotation_date DESC")
	 List<QuotationDTO> getQuotationList();
	 
	 /* 서류 등록 */
	 @Insert(
	     "INSERT INTO quotation " +
	     "(quotation_id, quotation_partnername, quotation_hqmanager, quotation_date, quotation_description, quotation_file_name, quotation_type)" +
	     "VALUES " +
	     "(#{quotation_id}, #{quotation_partnername}, #{quotation_hqmanager}, now(), #{quotation_description}, #{quotation_file_name}, #{quotation_type})"
	 )
	 int addQuotation(QuotationDTO quotation) throws Exception;
	 
	 /*quotation_status 변경*/
	 @Update("UPDATE quotation SET quotation_status = #{newStatus} WHERE quotation_id = #{quotationId}")
	 int updateQuotationStatus(@Param("quotationId") String quotationId, @Param("newStatus") int newStatus);
	 
	 /*quotation 검색 필터*/
	 List<QuotationDTO> qList(Map<String, String> params);
	 
	 /*서류 등록 quotation_id 중복 체크*/
	 @Select("SELECT COUNT(*) FROM quotation WHERE quotation_id = #{quotation_id}")
	 int quotationIdCheck(@Param("quotation_id") String quotation_id) throws Exception;
	 
	 /*서류 등록 모달 리스트*/
	 @Select("SELECT * FROM product")
	 	List<ProductDTO> getProductList();
	 
	 /*상품 추가 모달 -> 상품 추가 페이지*/
	 @Select("SELECT * FROM product WHERE product_id = #{product_name}")
	 ProductDTO findProductById(String productId);
	 
	//물품 요청 상세 페이지
	 @Select("""
	    SELECT p.*, pt.partner_name
	    FROM product p
	    JOIN partner pt ON p.product_partnerid = pt.partner_id
	    WHERE (pt.partner_name LIKE CONCAT('%', #{searchKeyword}, '%')
	        OR p.product_name LIKE CONCAT('%', #{searchKeyword}, '%'))
	""")
	List<ProductDTO> order_request_detail(@Param("searchKeyword") String searchKeyword);
	 	
	 // 상품 등록 페이지 HR부서 데이터 가져오기
	 @Select("SELECT hr_name, hr_id FROM hr")
	 List<Map<String, String>> getAllHQManagers();
	 
	 // 발주 요청 post
	 @Insert("INSERT INTO `order` (order_id, order_sender, order_date, order_amount, order_sum, order_type, order_manager, order_delivery, order_memo)"
	 		+ "		VALUES (#{order_id}, #{order_sender}, now(), #{orderDetail_amount}, #{order_sum}, 1, #{order_manager}, #{order_delivery}, #{order_memo})")
	 int insertOrder(OrderDTO order) throws Exception;
	 
	//물품 요청 목록 상세보기
	 @Select("SELECT * FROM `order` WHERE order_id = #{order_id}")
	 OrderDTO listdetail(String order_id);

	 @Select("SELECT * FROM `orderdetail` WHERE orderDetail_orderid = #{orderDetail_orderid}")
	 List<OrderDetailDTO> listdetail_2(String orderDetail_orderid);
	 
		
	@Insert("INSERT INTO `orderdetail` (orderDetail_orderid, orderDetail_productid, orderDetail_price, orderDetail_amount, orderDetail_sum)"
			+"VALUES (#{orderDetail_orderid}, #{orderDetail_productid}, #{orderDetail_price}, #{orderDetail_amount}, #{orderDetail_sum})"
	) int insertOrderDetail(OrderDetailDTO detail) throws Exception;

	//현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    //현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    //현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    
	int addStaff(StaffDTO sDTO);
	
	@Select("select * from hr where hr_del = 0")
	List<StaffDTO> staffList();
	
	@Select("select * from hr where hr_code = #{hr_code}")
	StaffDTO staffDetail(int hr_code);
	
	@Select("select * from hr where hr_id = #{hr_id}")
	StaffDTO idSearch(String hr_id);
	
	int staffEdit(StaffDTO sDTO);
	
	List<StaffDTO> staffSearch(
			@Param("del") Integer del, 
			@Param("staff_search") String staff_search, 
			@Param("grade") Integer grade, 
			@Param("start") String start,
			@Param("end") String end, 
			@Param("auth") Integer auth);
	
	//temporary
	StaffDTO login(Map<String, String> params);
	
	int addApproval(ApprovalDTO aDTO);
	
	List<StaffDTO> bossList(int hr_authority);
	
	List<StaffDTO> bossSearch(Map<String, Object> params);
	
	List<ApprovalDTO> appList(int hr_code);
			
	ApprovalDTO appDetail(int app_id);
	
	List<ApprovalDTO> appListBoss(int boss_code);

	int bossView(int app_id);
	
	int bossViewRevert(int app_id);
	
	int cancelApproval(Map<String, Object> params);
	
	int approveApproval(Map<String, Object> params);
	
	int pdoApproval(int app_id);
	
	List<ApprovalDTO> calendar();
	
	@Select("select hr_code from hr where hr_id = #{username}")
	int staff_get_hrCode(String username);

}
