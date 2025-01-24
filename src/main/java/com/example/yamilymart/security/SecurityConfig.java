package com.example.yamilymart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@ComponentScan("controllers")
public class SecurityConfig {
	
    private final PrincipalDetailsService principalDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    public SecurityConfig(PrincipalDetailsService principalDetailsService, CustomAuthenticationSuccessHandler successHandler) {
        this.principalDetailsService = principalDetailsService;
        this.successHandler = successHandler;
    }
	
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
//            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                .requestMatchers("/admin/**", "/reissue").authenticated() // 인증된 사용자만 접근
//                .anyRequest().permitAll() // 나머지 요청은 허용
//            )
//            .formLogin(formLogin -> formLogin
//                .loginPage("/loginForm") // 커스텀 로그인 페이지
//                .successHandler(authenticationSuccessHandler()) // 성공 핸들러
//                //.successHandler(successHandler)
//                .failureHandler(authenticationFailureHandler()) // 실패 핸들러
//                .permitAll()
//            );
//
//        return http.build();
//    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 로그인 성공 시 "/"로 리다이렉트
            response.sendRedirect("/");
        };
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**").hasRole("BRANCH") // 지점 점장
                .requestMatchers("/admin/**").hasAnyRole("MANAGER", "STAFF") // 본사 직원
                .requestMatchers("/admin/hr/manage").hasRole("MANAGER") //인사관리는 매니저만 접근 가능
                .requestMatchers("/admin/download-file/**").hasAnyRole("MANAGER", "STAFF") //인사관리는 매니저만 접근 가능
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // 정적 리소스 허용
                .anyRequest().authenticated() // 그 외 요청은 인증 필요
            )
            .formLogin(form -> form
                .loginPage("/loginForm") // 커스텀 로그인 페이지
                .successHandler(successHandler) // 성공 핸들러
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/loginForm")
                .permitAll()
            )
            .userDetailsService(principalDetailsService);

        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            // 로그인 실패 시 "/loginForm?error=true"로 리다이렉트
            response.sendRedirect("/loginForm?error=true");
        };
    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}