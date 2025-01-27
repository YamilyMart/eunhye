package com.example.yamilymart.service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.yamilymart.dao.YamilyDao;
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

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class YamilyService {

    private ModelAndView mv;

    @Autowired
    private YamilyDao yDao;
    
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


//    public String join(HrDTO hrDTO) {
//    	hrDTO.setHr_pwd(bCryptPasswordEncoder.encode(hrDTO.getHr_pwd()));
//    	hrDTO.setHr_grade(0);
//        yDao.save(hrDTO);
//        return "redirect:/loginForm";
//    }
    
    public String join(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	user.setRole("ROLE_USER");
        yDao.userSave(user);
        return "redirect:/loginForm";
    }
    
    
    
    

    public ModelAndView admin_main() {
    	mv = new ModelAndView();
    	
    	//발주 상태별 건수 조회(order)
    	List<OrderDTO> orderStatusList = yDao.admin_main_orderStatus();
        mv.addObject("orderStatusList", orderStatusList);
        
        //제품별 판매 순위(sale)
    	List<SaleDTO> productSaleList = yDao.admin_main_prductSale();
        mv.addObject("productSaleList", productSaleList);
        
        //지점별 매출 순위(sale)
    	List<SaleDTO> branchSaleList = yDao.admin_main_branchSale();
        mv.addObject("branchSaleList", branchSaleList);
        
        //달별 판매 순위(sale)
        List<SaleDTO> monthSaleList = yDao.admin_main_monthSale();
        
        int count = monthSaleList.size();
        int[] monthSaleArr = new int[count]; // 배열 크기 초기화

        //달별 매출 배열 저장
        for(int i=0; i<count; i++) {
     	   SaleDTO dto = monthSaleList.get(i);
     	   monthSaleArr[i] = dto.getSale_sum();
        }
        
        mv.addObject("monthSaleArr", monthSaleArr);
        
        mv.setViewName("admin_main");

        return mv;
    }

    public ModelAndView login() {
   	   mv = new ModelAndView();
       mv.setViewName("login");

       return mv;
   }

    public ModelAndView yamily_login() {
    	   mv = new ModelAndView();
        mv.setViewName("login");

        return mv;
    }

    public ModelAndView login_submit() {
      	 mv = new ModelAndView();

     	//int a = yDao.admin_stock_product_add(pdto);

         mv.setViewName("admin_main");

          return mv;
      }

    public ModelAndView admin_order_list() {
    	mv = new ModelAndView();
    	List<OrderDTO> list = yDao.admin_order_list();
    	
        mv.addObject("list", list);
        mv.setViewName("admin_order_list");

        return mv;
    }

    public ModelAndView admin_order_list_search(OrderSearchDTO dto) {
    	mv = new ModelAndView();
    	List<OrderDTO> list = yDao.admin_order_list_search(dto);
        mv.addObject("list", list);
        mv.setViewName("admin_order_list");

        return mv;
    }

    public List<OrderDetailDTO> admin_order_detail(String orderDetail_orderid) {
    	List<OrderDetailDTO> dto = yDao.admin_order_detail(orderDetail_orderid);
        return dto;
    }

    public OrderDTO admin_order_detail2(String order_id) {
    	OrderDTO dto = yDao.admin_order_detail2(order_id);
        return dto;
    }

    public ModelAndView admin_stock_list() {
    	mv = new ModelAndView();
    	List<StockDTO> list = yDao.admin_stock_list();
        mv.addObject("list", list);
        mv.setViewName("admin_stock_list");

        return mv;
    }


    public ModelAndView admin_order_approval(int approval_type, String order_id) {
    	mv = new ModelAndView();
    	
    	if(approval_type == 0) { //승인 시에 물류창고 재고 감소
        	List<OrderDetailDTO> list = yDao.admin_order_detail(order_id);
        	
    		int a = yDao.admin_order_approval_decrease(list);
    	}
    	
    	int a = yDao.admin_order_approval(approval_type, order_id);

        mv.setViewName("redirect:/admin/order/list");

        return mv;
    }

    public ModelAndView admin_stock_list_search(int searchType, String keyword) {
    	mv = new ModelAndView();
    	List<StockDTO> list = yDao.admin_stock_list_search(searchType, keyword);
        mv.addObject("list", list);
        mv.setViewName("admin_stock_list");

        return mv;
    }

    public ModelAndView admin_stock_product_add(ProductDTO pdto) {
   	   mv = new ModelAndView();

   	   //제품등록
   	   int a = yDao.admin_stock_product_add(pdto);
   	   if(a == 0) {

   	   } else {

   	   }

   	   //초기 재고 등록
   	   int b = yDao.admin_stock_add(pdto.getProduct_id());

       mv.setViewName("redirect:/admin/stock/list");

       return mv;
   }
    
    public int admin_stock_product_check(String product_id) {
    	int a = yDao.admin_stock_product_check(product_id);

        return a;
    }
    
    public List<PartnerDTO> admin_stock_partner(String keyword) {
    	List<PartnerDTO> list = yDao.admin_stock_partner(keyword);
        return list;
    }

    public ProductDTO admin_stock_update_get(String stock_productid) {
    	ProductDTO dto = yDao.admin_stock_update_get(stock_productid);
        return dto;
    }

    public ModelAndView admin_stock_update_post(ProductDTO pdto) {
    	mv = new ModelAndView();
	    int a = yDao.admin_stock_update_post(pdto);
        mv.setViewName("redirect:/admin/stock/list");

        return mv;
    }

    public ModelAndView admin_stock_delete(String product_id) {
    	mv = new ModelAndView();
	    int a = yDao.admin_stock_delete(product_id);
        mv.setViewName("redirect:/admin/stock/list");

        return mv;
    }

    public ModelAndView admin_stock_amount(int stock_id, int stock_remain) {
    	mv = new ModelAndView();
	    int a = yDao.admin_stock_amount(stock_id, stock_remain);
        mv.setViewName("redirect:/admin/stock/list");

        return mv;
    }

    public ModelAndView admin_sale_list() {
   	   mv = new ModelAndView();
   	   List<SaleDTO> list = yDao.admin_sale_list();

       int count = list.size();
       int sum = 0;

       //총 매출 계산하기
       for(int i=0; i<count; i++) {
    	   SaleDTO dto = list.get(i);
			sum += dto.getSale_sum();
       }

       mv.addObject("list", list);
       mv.addObject("count", count);
       mv.addObject("sum", sum);
       mv.setViewName("admin_sale_list");

       return mv;
   }

    public ModelAndView admin_sale_list_search(String keyword, String starDate, String endDate) {
    	mv = new ModelAndView();
    	List<SaleDTO> list = yDao.admin_sale_list_search(keyword, starDate, endDate);

        int count = list.size();
        int sum = 0;

        //총 매출 계산하기
        for(int i=0; i<count; i++) {
     	   SaleDTO dto = list.get(i);
 			sum += dto.getSale_sum();
        }

        mv.addObject("list", list);
        mv.addObject("count", count);
        mv.addObject("sum", sum);
        mv.setViewName("admin_sale_list");

        return mv;
    }

    public List<SaleDTO> admin_sale_detail(String sale_branchid, String sale_date) {
    	List<SaleDTO> dto = yDao.admin_sale_detail(sale_branchid, sale_date);
        return dto;
    }
    
    
    
    //우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
  	//우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
  	//우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    
    /*거래처 관리*/
    public ModelAndView getPartnerList() {
        log.info("getPartnerList() 호출됨");

        ModelAndView mv = new ModelAndView();
        // DAO에서 데이터 조회
        List<PartnerDTO> pList = yDao.getPartnerList();
        
        log.info("조회된 Partner 수: {}", pList.size());

        // 데이터를 ModelAndView에 추가
        mv.addObject("pList", pList);
        mv.setViewName("admin_Partner_List"); // "table.html" 템플릿을 반환
        return mv;
    }
    
    // 상품 주문 목록
    public ModelAndView getOrderList() {
    	log.info("getOrderList() 호출됨");
    	
    	ModelAndView mv = new ModelAndView();
    	
    	List<OrderDTO> oList = yDao.getOrderList();
    	
    	log.info("oList 크기: {}", oList.size()); // 로그 확인
    	log.info("oList 데이터: {}", oList); // 데이터 확인    	
    	
    	mv.addObject("oList", oList);
    	mv.setViewName("admin_ProductOrder_List");
    	
    	return mv;
    }
    
    // 지점 목록
    public ModelAndView getBranchList() {
    	log.info("getBranchList() 호출됨");
    	
    	ModelAndView mv = new ModelAndView();
    	
    	List<BranchDTO> bList = yDao.getBranchList();
    	
    	log.info("oList 크기: {}", bList.size()); // 로그 확인
    	log.info("oList 데이터: {}", bList); // 데이터 확인
    	
    	mv.addObject("bList", bList);
    	mv.setViewName("adminBranchList");
    	
    	return mv;
    }
    
    // 지점 상세보기
    public BranchDTO getBranchDetails(String branch_code) {
        BranchDTO dto = yDao.getBranchDetails(branch_code);
        return dto;
    }
    
    // 지점 등록
    public int addBranch(BranchDTO branch) throws Exception {
		// TODO Auto-generated method stub
		 // 중복 확인 로직 추가
		/*
		 * Map<String, Object> userMap = new HashMap<>(); userMap.put("user_id",
		 * member.getUser_id());
		 * 
		 * Map<String, Object> foundUser = catDogDAO.login(userMap); if (foundUser !=
		 * null) { throw new RuntimeException("Duplicate user_id: " +
		 * member.getUser_id()); }
		 * 
		 * System.out.println("회원 추가 됩니다잉" + userMap);
		 */

	    // 중복되지 않은 경우 INSERT 수행
    	
    	//user 테이블에 암호화된 비번이랑 아이디, 권한 추가
    	User user = new User();
    	user.setPassword(bCryptPasswordEncoder.encode(branch.getBranch_pwd()));
    	user.setRole("ROLE_BRANCH");
    	user.setUsername(branch.getBranch_id());
        yDao.userSave(user);
    	
    	 return yDao.addBranch(branch);
	}
    
    // 지점 수정
    public int updateBranch(BranchDTO branch) throws Exception {

    	//user 테이블 비번 수정
    	User user = new User();
    	user.setPassword(bCryptPasswordEncoder.encode(branch.getBranch_pwd()));
    	user.setUsername(branch.getBranch_id());
    	yDao.userUpdate(user);
    	
    	return yDao.updateBranch(branch);
    }
    
    // 지점 폐업
    public int closedBranch(BranchDTO branch) throws Exception {
    	return yDao.closedBranch(branch);
    }
    
    // 지점 검색 필터
    public List<BranchDTO> bList(String branch_name, String branch_owner, String startDate, String endDate, String branch_region) {
        Map<String, String> params = new HashMap<>();
        params.put("branch_name", branch_name);
        params.put("branch_owner", branch_owner);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("branch_region", branch_region);

        return yDao.bList(params);
    }
    
	// 지점 아이디 중복 검사
    public int getMemberByEmail(String branch_id) throws Exception {
        int count = yDao.idCheck(branch_id);
        System.out.println("Service - branch_id: " + branch_id + ", count: " + count); // 디버깅 로그
        return count;
    }
    
    /*거래처 관리*/
    public PartnerDTO getPartnerDetails(String partner_id) {
        PartnerDTO dto = yDao.getPartnerDetails(partner_id);
        return dto;
    }
    
    /*거래처 등록*/
    public int addPartner(PartnerDTO partner) throws Exception {
    	 return yDao.addPartner(partner);
	}
    
    // 거래처 아이디 중복 검사
    public int getPartnerById(String partner_id) throws Exception {
        int count = yDao.partnerIdCheck(partner_id);
        System.out.println("Service - partner_id: " + partner_id + ", count: " + count); // 디버깅 로그
        return count;
    }
    
    // 거래처 수정
    public int updatePartner(PartnerDTO partner) throws Exception {
    	return yDao.updatePartner(partner);
    }
    
    // 거래처 목록 검색 필터
    public List<PartnerDTO> pList(String partner_name, String partner_manager, String partner_email, String partner_phone) {
        Map<String, String> params = new HashMap<>();
        params.put("partner_name", partner_name);
        params.put("partner_manager", partner_manager);
        params.put("partner_email", partner_email);
        params.put("partner_phone", partner_phone);

        return yDao.pList(params);
    }
    
    // 거래처 삭제
    public int deletePartners(String[] partnerIds) throws Exception {
        return yDao.deletePartners(partnerIds);
    }
    
    // 서류 관리
    public List<QuotationDTO> getQuotations() {
        List<QuotationDTO> qList = yDao.getQuotationList();

        // URL 인코딩 처리
        qList.forEach(q -> {
            if (q.getQuotation_file_name() != null) {
                try {
                    q.setQuotation_file_name(URLEncoder.encode(q.getQuotation_file_name(), StandardCharsets.UTF_8));
                } catch (Exception e) {
                    log.error("파일 이름 URL 인코딩 중 오류 발생: {}",e);
                }
            }
        });

        return qList;
    }
    
    // 서류 등록
    public int addQuotation(QuotationDTO quotation) throws Exception {
   	 return yDao.addQuotation(quotation);
	}
    
    // quotation_stauts 변경
    public int updateQuotationStatus(String quotationId, int newStatus) {
        return yDao.updateQuotationStatus(quotationId, newStatus);
    }
    
    // quotation 검색 필터
    public List<QuotationDTO> qList(String quotation_id, String quotation_partnername, String quotation_hqmanager,
            String startDate, String endDate, String searchType) {
		Map<String, String> params = new HashMap<>();
		
		if (quotation_id != null && !quotation_id.isEmpty()) params.put("quotation_id", quotation_id);
		if (quotation_partnername != null && !quotation_partnername.isEmpty()) params.put("quotation_partnername", quotation_partnername);
		if (quotation_hqmanager != null && !quotation_hqmanager.isEmpty()) params.put("quotation_hqmanager", quotation_hqmanager);
		if (startDate != null && !startDate.isEmpty()) params.put("startDate", startDate);
		if (endDate != null && !endDate.isEmpty()) params.put("endDate", endDate);
		if (searchType != null && !searchType.isEmpty()) params.put("searchType", searchType);
		
		return yDao.qList(params);
		}
    
    // quotation_id 중복 체크
    public int getQuotationById(String quotation_id) throws Exception {
        int count = yDao.quotationIdCheck(quotation_id);
        return count;
    }
    
    // 상품 등록 GET
//    public int getAddOrder(OrderDTO order) throws Exception {
//    	return yDao.getAddOrder(order);
//    }
    
    public List<ProductDTO> getProductList() {
    	return yDao.getProductList();
    }
    
    public ProductDTO getProductById(String productId) {
        return yDao.findProductById(productId);
    }
    
    public List<ProductDTO> order_request_detail(String searchKeyword) {
    	return yDao.order_request_detail(searchKeyword);
    }
    
    // hr부서 이름 가져오기
    public List<Map<String, String>> getAllHQManagers() {
        return yDao.getAllHQManagers();
    }
    
    //점주 발주 요청 완료
    public String saveOrder(OrderDTO orderDTO) throws Exception {
        yDao.insertOrder(orderDTO);
        return orderDTO.getOrder_id(); // 발주 ID 반환
    }
    
    public OrderDTO listdetail(String order_id) {
        return yDao.listdetail(order_id);
    }

    public List<OrderDetailDTO> listdetail_2(String orderDetail_orderid) {
        return yDao.listdetail_2(orderDetail_orderid);
    }
    
	
	public void saveOrderDetails(List<OrderDetailDTO> orderDetails) throws Exception{ 
		for (OrderDetailDTO detail : orderDetails) {
			yDao.insertOrderDetail(detail); 
		} 
	}
	
	//현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    //현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    //현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	
	 public String addStaff(StaffDTO sDTO, List<MultipartFile> files, HttpSession session) {
	    	
	    	try {
	    		if (files != null && !files.isEmpty() && !files.get(0).isEmpty()) {
	    			fileUpload(files, session, sDTO);
	    		} else {
	    			sDTO.setHr_sybname("default.jpg");
	    			sDTO.setHr_oriname("default.jpg");
	    		}
	    		
	    		System.out.println(sDTO.getHr_sybname());
	    		System.out.println(sDTO.getHr_oriname());
	    		System.out.println(sDTO);
	    		int r = yDao.addStaff(sDTO);
	        	if (r>=1) {
	        		System.out.println("added");
	        	} else {
	        		System.out.println("Nope");
	        	}
	    	}catch (Exception e) {
	    		e.printStackTrace();
	    		System.out.println("Not added");
	    	}
	    	
	    	//user 테이블에 저장
	    	User user = new User();
	    	user.setPassword(bCryptPasswordEncoder.encode(sDTO.getHr_pwd()));
	    	user.setUsername(sDTO.getHr_id());
	    	
	    	if (sDTO.getHr_grade() == 1) {
		    	user.setRole("ROLE_MANAGER");
	    	} else {
		    	user.setRole("ROLE_STAFF");
	    	}
	    	
	        yDao.userSave(user);

	    	return "redirect:/admin/staff/main";
	    }
	    
	    public ModelAndView staffList() {
	    	mv = new ModelAndView();
	    	List<StaffDTO> staffList = yDao.staffList();
	    	mv.addObject("staffList", staffList);
	    	mv.setViewName("staff_main");
	    	return mv;
	    }
	    
	    public StaffDTO staffDetail(@RequestParam("hr_code")int hr_code) {
	    	StaffDTO staffDetail = yDao.staffDetail(hr_code);
	    	return staffDetail;
	    }

	    
	    public StaffDTO idSearch(@RequestParam("hr_id")String hr_id) {
	    	StaffDTO idSearch = yDao.idSearch(hr_id);
	    	return idSearch;
	    }
	    
	    private void fileUpload(List<MultipartFile> files, HttpSession session, StaffDTO sDTO) throws Exception {
	        System.out.println("File upload");
	        String sybname = null;
	        String oriname = null;

	        // Use a fixed upload directory
	        String fixedUploadPath = "C:/images/uploads/";  // Make sure this path exists and is writable
	        File folder = new File(fixedUploadPath);
	        
	        // Ensure the directory exists
	        if (!folder.exists()) {
	            boolean created = folder.mkdirs();
	            if (created) {
	                System.out.println("Directory created: " + fixedUploadPath);
	            } else {
	                System.out.println("Failed to create directory: " + fixedUploadPath);
	            }
	        }

	        // Handle file upload if it's not empty
	        if (files != null && !files.isEmpty() && !files.get(0).isEmpty()) {
	            MultipartFile mf = files.get(0);
	            oriname = mf.getOriginalFilename();
	            
	            // Generate a unique file name using current timestamp
	            sybname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
	            System.out.println("Generated file name: " + sybname);

	            File file = new File(fixedUploadPath + sybname);
	            
	            // Save the file to the specified directory
	            mf.transferTo(file);

	            // Set file details in DTO
	            sDTO.setHr_sybname(sybname);
	            sDTO.setHr_oriname(oriname);
	        } else {
	            // Set default image if no file is provided
	            sybname = "default.jpg";
	            oriname = "default.jpg";
	            sDTO.setHr_sybname(sybname);
	            sDTO.setHr_oriname(oriname);
	        }

	        // Log the file details
	        System.out.println("Stored file name: " + sDTO.getHr_sybname());
	        System.out.println("Original file name: " + sDTO.getHr_oriname());
	    }

	    public String staffEdit(@RequestParam("hr_code")int hr_code,
	    		@RequestParam("hr_grade")int hr_grade,
	    		@RequestParam("hr_id") String hr_id,
	    		@RequestParam("hr_pwd") String hr_pwd,
	    		@RequestParam("hr_pdo")int hr_pdo,
	    		List<MultipartFile> files,
	    		StaffDTO sDTO, HttpSession session) {
	    	System.out.println("hr_code is: "+hr_code);

	    	sDTO.setHr_code(hr_code);
	    	sDTO.setHr_grade(hr_grade);
	    	sDTO.setHr_id(hr_id);
	    	sDTO.setHr_pwd(hr_pwd);
	    	sDTO.setHr_pdo(hr_pdo);
	    	
	    	StaffDTO original = yDao.staffDetail(hr_code);
	        String image = original != null ? original.getHr_sybname() : null;
	        System.out.println("image: " + image);
	        
	    	if (image == null || image.isEmpty()) {
	    		sDTO.setHr_sybname("default.jpg");
				sDTO.setHr_oriname("default.jpg");
	    	}
	    	String upFile = files.get(0).getOriginalFilename();
	    	
	    	try {
	    		if(!upFile.equals("")) {
	    			fileUpload(files, session, sDTO);
	    		}else {
	    		}
	    		
	    		int r = yDao.staffEdit(sDTO);
	        	if (r>=1) {
	        		System.out.println("됐어요");
	        	} else {
	        		System.out.println("안됐어요..");
	        	}
	        	System.out.println("INCOMINGGGGGGGGGGGGG");
	        	System.out.println(sDTO.getHr_sybname());
	        	System.out.println(sDTO.getHr_oriname());
	        	System.out.println("BBBBBBBBYYYYYYYYYYYYYEEEEEEEEEE");
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		System.out.println("Uh can't edit");
	    	}
	    	
	    	
	    	//user 테이블에 저장
	    	User user = new User();
	    	user.setPassword(bCryptPasswordEncoder.encode(sDTO.getHr_pwd()));
	    	user.setUsername(sDTO.getHr_id());
	    	
	    	if (sDTO.getHr_grade() == 1) {
		    	user.setRole("ROLE_MANAGER");
	    	} else {
		    	user.setRole("ROLE_STAFF");
	    	}
	    	
	        yDao.userUpdate(user);

	    	
	    	return "redirect:/admin/staff/main";
	    }
	    
	    public ModelAndView staffSearch(Integer del, String staff_search,Integer grade,String start,String end,Integer auth) {
	    	ModelAndView mav = new ModelAndView();
	    	if(del == null && staff_search == null && grade == null && start == null && end == null && auth == null) {
	    		List<StaffDTO> staffList = yDao.staffList();
	    		mav.addObject("staffList", staffList);
	    	} else {
	    		List<StaffDTO> searchResult = yDao.staffSearch(del, staff_search, grade, start, end, auth);
	        	mav.addObject("staffList", searchResult);
	    	}
	    	mav.setViewName("staff_main");
	    	return mav;
	    }
	    
		/*
		 * public ModelAndView login(Map<String, String> params) { StaffDTO staff = new
		 * StaffDTO(); staff = yDao.login(params); ModelAndView mav = new
		 * ModelAndView(); mav.addObject("staff", staff);
		 * mav.setViewName("redirect:staff_approval?hr_code=" + staff.getHr_code());
		 * 
		 * return mav; }
		 */

	    public List<StaffDTO> searchBoss(@RequestParam("hr_name")String hr_name, @RequestParam("hr_code")String hr_code) {
	    	System.out.println("hr_code is::::::::::::::" +hr_code);
	    	int id = Integer.parseInt(hr_code);
	    	StaffDTO staff = yDao.staffDetail(id);
	    	int hr_authority = staff.getHr_authority();
	    	Map<String, Object> params = new HashMap<>();
	        params.put("hr_name", hr_name);
	        params.put("hr_authority", hr_authority);
	    	List<StaffDTO> boss = yDao.bossSearch(params);
	    	return boss;
	    }
	    
	    public String addApproval(@RequestParam("hr_code")int hr_code, 
				@RequestParam("selected_hr_code")int selected_hr_code,
				@RequestParam("approvalFile") List<MultipartFile> files,
				ApprovalDTO aDTO) {
	    	aDTO.setBoss_code(selected_hr_code);
	    	
	    	String sybname = null;
	    	String oriname = null;
	    	
	    	String fixedUploadPath = "C:/images/uploadDoc/";
	    	
	    	File folder = new File(fixedUploadPath);
	    	if (!folder.exists()) {
	    		boolean created = folder.mkdirs();
	    		if (created) {
	    			System.out.println("Directory created: "+ fixedUploadPath);
	    		} else {
	    			System.out.println("Nope");
	    		}
	    	}
	    	
	    	if (files != null && !files.isEmpty() && !files.get(0).isEmpty()) {
	    		MultipartFile mf = files.get(0);
	    		oriname = mf.getOriginalFilename();
	    		sybname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
	    		System.out.println(sybname);
	    		
	    		File file = new File(fixedUploadPath + sybname);
	    		try {
	    			mf.transferTo(file);
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    			return "redirect:errorPage";
	    		}
	    		
	    		
	    		aDTO.setApp_file(sybname);
	    	} else {
	    		aDTO.setApp_file("");
	    	}
	    	
	    	
	    	int r = yDao.addApproval(aDTO);
	    	if (r>=1) {
	    		System.out.println("yes");
	    	} else {
	    		System.out.println("not");
	    	}
	    	
	    	return "redirect:/admin/approval/main";
	    }
	    
	    public ModelAndView appList (@RequestParam("hr_code") int hr_code) {
	    	StaffDTO writer = new StaffDTO();
	    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!"+hr_code);
	    	writer = yDao.staffDetail(hr_code);
	    	
	    	ModelAndView mav = new ModelAndView();
	    		List<ApprovalDTO> list = yDao.appList(hr_code);
	    		List<ApprovalDTO> newList = new ArrayList<>();
	        	for (ApprovalDTO aDTO: list) {
	        		int boss_code = 0;
	        		if (aDTO.getBoss_code_second() == 0) {
	        			boss_code = aDTO.getBoss_code();
	        		} else if (aDTO.getBoss_code_second() != 0) {
	        			boss_code = aDTO.getBoss_code_second();
	        		}
	        		StaffDTO boss = new StaffDTO();
	        		boss = yDao.staffDetail(boss_code);
	        		
	        		aDTO.setWriter(writer.getHr_name());
	        		aDTO.setBoss(boss.getHr_name());
	        		newList.add(aDTO);
	        	}
	        mav.addObject("appList", newList);
	        
	        List<ApprovalDTO> bossList = yDao.appListBoss(hr_code);
	        System.out.println(bossList);
	        List<ApprovalDTO> newBossList = new ArrayList<>();
	        for(ApprovalDTO aDTO: bossList) {
	        	int boss_code = hr_code;
	        	if (aDTO.getBoss_code_second() != 0) {
	        		StaffDTO boss = yDao.staffDetail(aDTO.getBoss_code_second());
	        		aDTO.setBoss(boss.getHr_name());
	        	} else {
	        		StaffDTO boss = yDao.staffDetail(hr_code);
	        		aDTO.setBoss(boss.getHr_name());
	        	}
	        	StaffDTO staff = yDao.staffDetail(aDTO.getHr_code());
	        	
	        	aDTO.setWriter(staff.getHr_name());
	        	newBossList.add(aDTO);
	        }
	        
	        List<StaffDTO> sangsa = yDao.bossList(writer.getHr_authority());
	        
	        mav.addObject("sangsa", sangsa);
	        mav.addObject("bossList", newBossList);
	    	mav.addObject("hr_code", hr_code);
	    	mav.setViewName("staff_approval");
	    	return mav;
	    }
	    
	    public ApprovalDTO appDetail(
	    		@RequestParam("app_id")int app_id,
	    		@RequestParam("hr_code")int hr_code) {
	    	System.out.println("hr_code is: "+hr_code);

	    	ApprovalDTO aDTO = new ApprovalDTO();
	    	aDTO = yDao.appDetail(app_id);
	    	StaffDTO writer = yDao.staffDetail(aDTO.getHr_code());
	    	
	    	if (aDTO.getBoss_code_second() == 0) {
	    		StaffDTO boss = yDao.staffDetail(aDTO.getBoss_code());
	        	aDTO.setBoss(boss.getHr_name());
	    	} else if (aDTO.getBoss_code_second() != 0) {
	    		StaffDTO secondBoss = yDao.staffDetail(aDTO.getBoss_code_second());
	    		aDTO.setBoss(secondBoss.getHr_name());
	    	}
	    	aDTO.setWriter(writer.getHr_name());
	    	aDTO.setStaffDTO(writer);

	    	if(hr_code == aDTO.getBoss_code() || hr_code == aDTO.getBoss_code_second()) {
	    		int r = yDao.bossView(app_id);
	        	if (r>=1) {
	        		System.out.println("우왕");
	        	} else {
	        		System.out.println("에이씨.");
	        	}
	    	} else {
	    		System.out.println("님 아니자나요");
	    	}
	    	return aDTO;
	    }
	    
	    public String cancelApproval (
	    		@RequestParam("hr_code")int hr_code,
	    		@RequestParam("app_id")int app_id,
	    		@RequestParam("app_rejection")String app_rejection) {
	    	System.out.println("This is: "+hr_code);
	    	System.out.println("We're talking about: "+ app_id);
	    	ApprovalDTO app = yDao.appDetail(app_id);
	    	int writer = app.getHr_code();
	    	int boss = app.getBoss_code();
	    	Map<String, Object> params = new HashMap<>();
	    	params.put("app_id", app_id);
	    	params.put("app_rejection", app_rejection);
	    	if (hr_code == writer || hr_code == boss) {
	    		int r = yDao.cancelApproval(params);
	    		if (r>=1) {
	    			System.out.println("Success");
	    		} else {
	    			System.out.println("Failed");
	    		}}
	    	else {
	    		System.out.println("You didn't write this.");
	    	}
	    	return "redirect:/admin/approval/main";
	    }
	    
	    public String approveApproval (
	    		@RequestParam("hr_code")int hr_code,
	    		@RequestParam("app_id")int app_id,
	    		@RequestParam("boss_code_second")int boss_code_second) {
	    	System.out.println("Hello, I'm " + hr_code);
	    	System.out.println("And we're talking about "+ app_id);
	    	System.out.println("The boss_code_second is "+boss_code_second);
	    	Map<String, Object> params = new HashMap<>();
	    	params.put("app_id", app_id);
	    	params.put("boss_code_second", boss_code_second);
			ApprovalDTO aDTO = yDao.appDetail(app_id);
			
			if (aDTO.getBoss_code_second()==0) {
				int r = yDao.approveApproval(params);
				System.out.println("업뎃됐을껄..?");
			} else {
				int a = yDao.approveApproval(params);
				int b = yDao.pdoApproval(app_id);
			}
	    	
	    	return "redirect:/admin/approval/main";
	    }
	    
	    public List<ApprovalDTO> calendar() {
	    	return yDao.calendar();
	    }
	    
	    public int staff_get_hrCode(String username) {
	    	return yDao.staff_get_hrCode(username);
	    }

}
