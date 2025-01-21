package com.example.yamilymart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@MapperScan("com.example.yamilymart.dao") // Mapper 패키지 경로
public class YamilymartApplication {

	public static void main(String[] args) {
		SpringApplication.run(YamilymartApplication.class, args);
	}

}