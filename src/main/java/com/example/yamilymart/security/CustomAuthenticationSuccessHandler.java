package com.example.yamilymart.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 사용자의 첫 번째 권한 가져오기
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        switch (role) {
            case "ROLE_BRANCH":
                response.sendRedirect("/user/main"); // 지점 점장
                break;
            case "ROLE_MASTER":
            case "ROLE_MANAGER":
            case "ROLE_STAFF":
                response.sendRedirect("/admin/main"); // 본사 직원
                break;
            default:
                response.sendRedirect("/login?error=true"); // 알 수 없는 권한
                break;
        }
    }
}
