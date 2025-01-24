package com.example.yamilymart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.yamilymart.dao.YamilyDao;

@ControllerAdvice
public class GlobalControllerAdvice {
	
    @Autowired
    private YamilyDao yDao;

	//전역변수로 현재 로그인한 id값 보내기
    @ModelAttribute
    public void addUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() 
                && !authentication.getName().equals("anonymousUser")) {
        	
            String username = authentication.getName();
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            String hr_name = yDao.hrNameSelect(username);
        	
            model.addAttribute("username", hr_name);
            
            
        }
    }
}
