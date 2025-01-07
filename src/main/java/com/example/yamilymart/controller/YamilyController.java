package com.example.yamilymart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.dto.OrderSearchDTO;
import com.example.yamilymart.dto.ProductDTO;
import com.example.yamilymart.service.YamilyService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class YamilyController {
	
	private ModelAndView mv;

	@Autowired
    private YamilyService yServ;

	//메인 페이지
	@GetMapping("/")
    public ModelAndView admin_main(){
        mv = yServ.admin_main();
        return mv;
    }
	
	//본사 - 발주요청목록 get
	@GetMapping("/admin/order/list")
    public ModelAndView admin_order_list(){
        mv = yServ.admin_order_list();
        return mv;
    }
	
	//본사 - 발주요청목록검색
	@PostMapping("/admin/order/list")
    public ModelAndView admin_order_list_search(OrderSearchDTO dto){
        mv = yServ.admin_order_list_search(dto);
        return mv;
    }
	
	//본사 - 발주상세정보1
	//ajax 다건 검색
	@GetMapping("/admin/order/detail")
	@ResponseBody
	public List<OrderDetailDTO> adminOrderDetail(@RequestParam("orderDetail_orderid") String orderDetailOrderId) {
		
	    return yServ.admin_order_detail(orderDetailOrderId);
	}

	//본사 - 발주상세정보2
	//ajax 단일건 검색
	@GetMapping("admin/order/detail2")
    public ResponseEntity<OrderDTO> admin_order_detail2(@RequestParam("order_id") String order_id){
		OrderDTO dto = yServ.admin_order_detail2(order_id);
		
        if (dto != null) {
            // DTO를 JSON으로 반환
            return ResponseEntity.ok(dto);
        } else {
            // 데이터가 없을 경우 HTTP 404 상태 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
	//본사 - 재고관리목록
	@GetMapping("/admin/stock/list")
    public ModelAndView admin_stock_list(){
        mv = yServ.admin_stock_list();
        return mv;
    }
	
	//본사 - 재고관리목록
	@PostMapping("/admin/stock/list")
    public ModelAndView admin_stock_list_search(@RequestParam("searchType") int searchType, 
    		@RequestParam("keyword") String keyword){
        mv = yServ.admin_stock_list_search(searchType, keyword);
        return mv;
    }
	
	//본사 - 상품등록
	@PostMapping("admin/stock/product/add")
    public ModelAndView admin_stock_product_add(ProductDTO pdto){
        mv = yServ.admin_stock_product_add(pdto);
        return mv;
    }
	
	//본사 - 상품수정(GET)
	@GetMapping("admin/stock/update")
    public ResponseEntity<ProductDTO> admin_stock_update_get(@RequestParam("stock_productid") String stock_productid){
        ProductDTO dto = yServ.admin_stock_update_get(stock_productid);
        if (dto != null) {
            // DTO를 JSON으로 반환
            return ResponseEntity.ok(dto);
        } else {
            // 데이터가 없을 경우 HTTP 404 상태 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
	//본사 - 상품수정(POST)
	@PostMapping("admin/stock/update")
    public ModelAndView admin_stock_update_post(ProductDTO pdto){
        mv = yServ.admin_stock_update_post(pdto);
        return mv;
    }
	
	//본사 - 상품삭제(GET)
	@GetMapping("admin/stock/delete")
    public ModelAndView admin_stock_delete(@RequestParam("product_id") String product_id){
        mv = yServ.admin_stock_delete(product_id);
        
        return mv;
    }
	
	//본사 - 상품수령변경(GET)
	@GetMapping("admin/stock/amount")
    public ModelAndView admin_stock_amount(@RequestParam("stock_id") int stock_id, @RequestParam("stock_remain") int stock_remain){
        mv = yServ.admin_stock_amount(stock_id, stock_remain);
        
        return mv;
    }

}

