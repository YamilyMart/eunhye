package com.example.yamilymart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.yamilymart.dao.YamilyDao;
import com.example.yamilymart.dto.HrDTO;
import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.dto.OrderSearchDTO;
import com.example.yamilymart.dto.PartnerDTO;
import com.example.yamilymart.dto.ProductDTO;
import com.example.yamilymart.dto.SaleDTO;
import com.example.yamilymart.dto.StockDTO;
//import com.example.yamilymart.dto.User;
import com.example.yamilymart.dto.User;

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
        yDao.save(user);
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
        
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 로그인된 사용자 이름
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String hr_name = yDao.hrNameSelect(username);

        mv.addObject("username", hr_name); // 모델에 사용자 이름 추가
              
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

}
