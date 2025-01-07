package com.example.yamilymart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.yamilymart.dao.YamilyDao;
import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.dto.OrderSearchDTO;
import com.example.yamilymart.dto.ProductDTO;
import com.example.yamilymart.dto.StockDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class YamilyService {
	
    private ModelAndView mv;

    @Autowired
    private YamilyDao yDao;
    
    public ModelAndView admin_main() {
    	 mv = new ModelAndView();
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
   	   
   	   //초기 재고 등록
   	   int b = yDao.admin_stock_add(pdto.getProduct_id());

       mv.setViewName("redirect:/admin/stock/list");

       return mv;
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

}
