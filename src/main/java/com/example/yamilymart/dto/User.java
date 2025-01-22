package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("User")
@Data
@Entity
public class User {
	
    @Id // 기본 키 설정
    @Column(name = "username")
    private String username;
    
    private String password;
    private String role;

}
