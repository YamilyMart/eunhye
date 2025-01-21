package com.example.yamilymart.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@ComponentScan("controllers")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화 // 폼 로그인 비활성화
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/user/**", "/reissue").authenticated() // 인증된 사용자만 접근
                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGE")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll() // 나머지 요청은 허용
            )
            .formLogin((formLogin) -> formLogin
                    .loginPage("/login")
                    //.defaultSuccessUrl("/")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
            		);

        return http.build();
    }
    
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}