package com.example.yamilymart.dto;

import java.io.File;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("QuotationDTO")
public class QuotationDTO {
    private String quotation_id;
    private String quotation_partnername;
    private String quotation_hqmanager;
    private String quotation_date;
    private String quotation_description;
    private String quotation_file_name;
    private int quotation_type;
    private int quotation_status;
}
