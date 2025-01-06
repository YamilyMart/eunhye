package com.example.yamilymart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.yamilymart.dao.YamilyDao;
import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
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
    	List<OrderDTO> list = yDao.adminOrderListSelect();
        mv.addObject("list", list);
        mv.setViewName("admin_order_list");

        return mv;
    }
    
    public List<OrderDetailDTO> admin_order_detail(String orderDetail_orderid) {    	        
    	List<OrderDetailDTO> dto = yDao.adminOrderDetailSelect(orderDetail_orderid);
        return dto;
    }
    
    public OrderDTO admin_order_detail2(String order_id) {    	
    	OrderDTO dto = yDao.adminOrderDetail2Select(order_id);
        return dto;
    }
    
    public ModelAndView admin_stock_list() {
    	 mv = new ModelAndView();
    	List<StockDTO> list = yDao.adminStockListSelect();
        mv.addObject("list", list);
        mv.setViewName("admin_stock_list");

        return mv;
    }
    
    public ModelAndView admin_stock_product_add(ProductDTO pdto) {
   	   mv = new ModelAndView();
   	   int a = yDao.admin_stock_product_add(pdto);
       mv.setViewName("admin_stock_list");

       return mv;
   }
    
    public ProductDTO admin_stock_update_get(String stock_productid) {
    	ProductDTO dto = yDao.admin_stock_update_get(stock_productid);
        return dto;
    }
    
    public ModelAndView admin_stock_update_post(ProductDTO pdto) {
    	mv = new ModelAndView();
	    int a = yDao.admin_stock_update_post(pdto);
        mv.setViewName("admin_stock_list");    
        
        return mv;
    }

}
