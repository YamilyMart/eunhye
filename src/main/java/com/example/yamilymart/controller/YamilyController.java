package com.example.yamilymart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.service.YamilyService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class YamilyController {
	
	private ModelAndView mv;

	@Autowired
    private YamilyService yServ;

	@GetMapping("/")
    public ModelAndView admin_main(){
        mv = yServ.admin_main();
        return mv;
    }
	
	@GetMapping("/admin/order/list")
    public ModelAndView admin_order_list(){
        mv = yServ.admin_order_list();
        return mv;
    }
	
//	@GetMapping("admin/order/detail")
//    public ResponseEntity<List<OrderDetailDTO>> admin_order_detail(@RequestParam("orderDetail_orderid") String orderDetail_orderid){
//		List<OrderDetailDTO> list = yServ.admin_order_detail(orderDetail_orderid);
//		
//        if (list != null) {
//            // DTO를 JSON으로 반환
//            return ResponseEntity.ok(list);
//        } else {
//            // 데이터가 없을 경우 HTTP 404 상태 반환
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
	
	
	//ajax 다건 검색
	@GetMapping("/admin/order/detail")
	@ResponseBody
	public List<OrderDetailDTO> adminOrderDetail(@RequestParam("orderDetail_orderid") String orderDetailOrderId) {
	    return yServ.admin_order_detail(orderDetailOrderId);
	}

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
	
	@GetMapping("/admin/stock/list")
    public ModelAndView admin_stock_list(){
        mv = yServ.admin_stock_list();
        return mv;
    }

}

