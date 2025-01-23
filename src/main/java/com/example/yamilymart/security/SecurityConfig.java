package com.example.yamilymart.security;

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

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable()) // CSRF 비활성화 // 폼 로그인 비활성화
//            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                .requestMatchers("/admin/**", "/reissue").authenticated() // 인증된 사용자만 접근
//                .anyRequest().permitAll() // 나머지 요청은 허용
//
//            )
//            .formLogin((formLogin) -> formLogin
//                    .loginPage("/loginForm")
//                    //.defaultSuccessUrl("/")
//                    .defaultSuccessUrl("/", true)
//                    .failureUrl("/loginForm?error=true")
//                    .permitAll()
//                    .successHandler((request, response, authentication) -> {
//                        response.sendRedirect("/");  // 로그인 성공 후 메인 페이지로 이동
//                    })
//            		);
//
//        return http.build();
//    }
	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/admin/**", "/reissue").authenticated() // 인증된 사용자만 접근
                .anyRequest().permitAll() // 나머지 요청은 허용
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/loginForm") // 커스텀 로그인 페이지
                .successHandler(authenticationSuccessHandler()) // 성공 핸들러
                .failureHandler(authenticationFailureHandler()) // 실패 핸들러
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 로그인 성공 시 "/"로 리다이렉트
            response.sendRedirect("/");
        };
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
	
//    @Bean
//    public BCryptPasswordEncoder encodePwd() {
//        return new BCryptPasswordEncoder();
//    }
//    
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}