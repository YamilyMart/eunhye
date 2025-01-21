package com.example.yamilymart.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("HrDTO")
public class HrDTO {
    private String hr_id;
    private String hr_pwd;
    private int hr_code;
    private int hr_authority;
    private String hr_name;
    private String hr_joindate;
    private int hr_grade;


}
