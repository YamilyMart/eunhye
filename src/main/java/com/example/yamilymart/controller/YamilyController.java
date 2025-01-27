package com.example.yamilymart.controller;
import org.springframework.http.HttpHeaders;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.example.yamilymart.dao.YamilyDao;
import com.example.yamilymart.dto.ApprovalDTO;
import com.example.yamilymart.dto.BranchDTO;
import com.example.yamilymart.dto.HrDTO;
import com.example.yamilymart.dto.OrderDTO;
import com.example.yamilymart.dto.OrderDetailDTO;
import com.example.yamilymart.dto.OrderRequestDTO;
import com.example.yamilymart.dto.OrderSearchDTO;
import com.example.yamilymart.dto.PartnerDTO;
import com.example.yamilymart.dto.ProductDTO;
import com.example.yamilymart.dto.QuotationDTO;
import com.example.yamilymart.dto.SaleDTO;
import com.example.yamilymart.dto.StaffDTO;
import com.example.yamilymart.dto.User;
//import com.example.yamilymart.dto.User;
import com.example.yamilymart.service.YamilyService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes("oList")
public class YamilyController {

	private ModelAndView mv;

	@Autowired
    private YamilyService yServ;
	
    @Autowired
    private YamilyDao yDao;
    

	//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

	//메인 페이지
	@GetMapping("/admin/main")
    public ModelAndView admin_main(){
        mv = yServ.admin_main();
        return mv;
    }
    
	@GetMapping("/loginForm")
	public String loginForm() {
		return "login_form";
	}

	/*
	 * @GetMapping("/joinForm") public String join() { return "join"; }
	 * 
	 * @PostMapping("/joinForm") public String join(@RequestBody User user) {
	 * yServ.join(user); return "redirect:/loginForm"; }
	 */
		
	//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

	//본사 - 발주요청목록 get
	@GetMapping("/admin/order/list")
    public ModelAndView admin_order_list(){
        mv = yServ.admin_order_list();
        return mv;
    }

	//본사 - 발주요청목록검색
	@PostMapping("/admin/order/list")
    public ModelAndView admin_order_list_search(OrderSearchDTO dto){
        mv = yServ.admin_order_list_search(dto);
        return mv;
    }

	//본사 - 발주상세정보1
	//ajax 다건 검색
	@GetMapping("/admin/order/detail")
	@ResponseBody
	public List<OrderDetailDTO> adminOrderDetail(@RequestParam("orderDetail_orderid") String orderDetailOrderId) {
	    return yServ.admin_order_detail(orderDetailOrderId);
	}

	//본사 - 발주상세정보2
	//ajax 단일건 검색
	@GetMapping("admin/order/detail2")
    public ResponseEntity<OrderDTO> admin_order_detail2(@RequestParam("order_id") String order_id){
		OrderDTO dto = yServ.admin_order_detail2(order_id);

        if (dto != null) {
            // DTO를 JSON으로 반환
            return ResponseEntity.ok(dto);
        } else {
            // 데이터가 없을 경우 HTTP 404 상태 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

	//본사 - 발주승인/거절
	@GetMapping("/admin/order/approval")
    public ModelAndView admin_order_approval(@RequestParam("approval_type") int approval_type, @RequestParam("order_id") String order_id){
        mv = yServ.admin_order_approval(approval_type, order_id);
        return mv;
    }


	//본사 - 재고관리목록
	@GetMapping("/admin/stock/list")
    public ModelAndView admin_stock_list(){
        mv = yServ.admin_stock_list();
        return mv;
    }

	//본사 - 재고관리 필터검색
	@PostMapping("/admin/stock/list")
    public ModelAndView admin_stock_list_search(@RequestParam("searchType") int searchType,
    		@RequestParam("keyword") String keyword){
        mv = yServ.admin_stock_list_search(searchType, keyword);
        return mv;
    }

	//본사 - 상품등록
	@PostMapping("admin/stock/product/add")
    public ModelAndView admin_stock_product_add(ProductDTO pdto, @RequestParam("files") MultipartFile file){
		
		//String filename = origin.substring(origin.lastIndexOf("\\")+1); //브라우저별로 경로가 포함되서 올라오는 경우가 있기에 간단한 처리.
		
		String uuid = UUID.randomUUID().toString();
		String filename = uuid + "_" + file.getOriginalFilename();
		String savename = "/C:\\uploads/" + filename;

		File dest = new File(savename);
		pdto.setProduct_image(filename);

		try {
			file.transferTo(dest);//업로드 진행
		} catch (Exception e) {
			e.printStackTrace();
		}

        mv = yServ.admin_stock_product_add(pdto);
        return mv;
    }
	
	//본사 - 상품 추가 시 상품코드 중복체크
	@GetMapping("admin/stock/product/check")
	@ResponseBody
    public int admin_stock_product_check(@RequestParam("product_id") String product_id){
		return yServ.admin_stock_product_check(product_id);
	}
	
	//본사 - 거래처 검색
	@GetMapping("admin/stock/partner")
	@ResponseBody
    public List<PartnerDTO> admin_stock_partner(@RequestParam(required = false, defaultValue = "", name = "keyword") String keyword){
        return yServ.admin_stock_partner(keyword);
    }


	//본사 - 상품수정(GET)
	@GetMapping("admin/stock/update")
    public ResponseEntity<ProductDTO> admin_stock_update_get(@RequestParam("stock_productid") String stock_productid){
        ProductDTO dto = yServ.admin_stock_update_get(stock_productid);
        if (dto != null) {
            // DTO를 JSON으로 반환
            return ResponseEntity.ok(dto);
        } else {
            // 데이터가 없을 경우 HTTP 404 상태 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
	//상품이미지 조회	
	@GetMapping("/display")
    public ResponseEntity<Resource> displayFile(@RequestParam("filename") String filename) {
        try {
            // 파일 경로 설정
            Path filePath = Paths.get("C:/uploads").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                // 올바르게 반환
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

	//본사 - 상품수정(POST)
	@PostMapping("admin/stock/update")
    public ModelAndView admin_stock_update_post(ProductDTO pdto, @RequestParam("files") MultipartFile file){
		
	    if (file.isEmpty()) {
	        mv = yServ.admin_stock_update_post(pdto);

	    } else { //수정할 이미지가 있으면
			String uuid = UUID.randomUUID().toString();
			String filename = uuid + "_" + file.getOriginalFilename();
			String savename = "/C:\\uploads/" + filename;

			File dest = new File(savename);
			pdto.setProduct_image(filename);

			try {
				file.transferTo(dest);//업로드 진행
			} catch (Exception e) {
				e.printStackTrace();
			}
	        mv = yServ.admin_stock_update_post(pdto);

	    }
		
        return mv;
    }

	//본사 - 상품삭제(GET)
	@GetMapping("admin/stock/delete")
    public ModelAndView admin_stock_delete(@RequestParam("product_id") String product_id) {
		
        mv = yServ.admin_stock_delete(product_id);

        return mv;
    }

	//본사 - 상품수령변경(GET)
	@GetMapping("admin/stock/amount")
    public ModelAndView admin_stock_amount(@RequestParam("stock_id") int stock_id, @RequestParam("stock_remain") int stock_remain){
        mv = yServ.admin_stock_amount(stock_id, stock_remain);

        return mv;
    }

	//본사 - 매출목록(GET)
	@GetMapping("admin/sale/list")
    public ModelAndView admin_sale_list(){
        mv = yServ.admin_sale_list();

        return mv;
    }

	//본사 - 매출목록 필터검색(POST)
	@PostMapping("admin/sale/list")
    public ModelAndView admin_sale_list_search(@RequestParam("keyword") String keyword,
    		@RequestParam("startDate") String startDate,
    		@RequestParam("endDate") String endDate){
        mv = yServ.admin_sale_list_search(keyword, startDate, endDate);

        return mv;
    }

	//본사 - 매출상세정보 목록
	//ajax 다건 검색
	@GetMapping("/admin/sale/detail")
	@ResponseBody
	public List<SaleDTO> admin_sale_detail(@RequestParam("sale_branchid") String sale_branchid, @RequestParam("sale_date") String sale_date) {

	    return yServ.admin_sale_detail(sale_branchid, sale_date);
	}
	
	
	
	//우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	//우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	//우진ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

	
	/* 지점 관리 */
	@GetMapping("/adminBranchList")
	public ModelAndView admin_Branch_List() {
		log.info("admin_Branch_List()");
		mv = yServ.getBranchList();
		return mv;
	}

	@GetMapping("/admin/branch/detail")
	public ResponseEntity<BranchDTO> getBranchDetails(@RequestParam("branch_code") String branch_code) {
		BranchDTO dto = yServ.getBranchDetails(branch_code);
		if (dto != null) {
			// DTO를 JSON으로 반환
			return ResponseEntity.ok(dto);
		} else {
			// 데이터가 없을 경우 HTTP 404 상태 반환
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/admin/branch/add")
	public String addBranch(BranchDTO branch, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		request.setCharacterEncoding("utf-8");

		int r = yServ.addBranch(branch);

		return "redirect:/adminBranchList";
	}

	@PostMapping("/admin/branch/modified")
	public String modifyBranch(BranchDTO branch, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");

		int r = yServ.updateBranch(branch);

		return "redirect:/adminBranchList";
	}

	@PostMapping("/admin/branch/closed")
	public String closedBranch(BranchDTO branch) throws Exception {

		int r = yServ.closedBranch(branch);

		return "redirect:/adminBranchList";
	}

	@PostMapping("/admin/branch/filter")
	public ModelAndView filterBranches(@RequestParam(value = "branch_name", required = false) String branch_name,
			@RequestParam(value = "branch_owner", required = false) String branch_owner,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "branch_region", required = false) String branch_region) {

		ModelAndView mv = new ModelAndView();

		// Service 호출
		List<BranchDTO> bList = yServ.bList(branch_name, branch_owner, startDate, endDate, branch_region);

		mv.addObject("bList", bList);
		mv.setViewName("adminBranchList");

		return mv;
	}

	@PostMapping("/admin/branch/idCheck")
	@ResponseBody
	public int emailCheck(@RequestParam(value = "branch_id") String branch_id) throws Exception {
		int count = yServ.getMemberByEmail(branch_id);
		System.out.println("branch_id: " + branch_id + ", count: " + count); // 디버깅 로그
		return count;
	}

	/* 거래처 관리 */
	/* 거래처 목록 */
	@GetMapping("/admin_Partner_List")
	public ModelAndView admin_Partner_List() {
		log.info("admin_Partner_List()");
		mv = yServ.getPartnerList();
		return mv;
	}

	/* 거래처 상세보기 */
	@GetMapping("/admin/partner/detail")
	public ResponseEntity<PartnerDTO> getPartnerDetails(@RequestParam("partner_id") String partner_id) {
		PartnerDTO dto = yServ.getPartnerDetails(partner_id);
		if (dto != null) {
			// DTO를 JSON으로 반환
			return ResponseEntity.ok(dto);
		} else {
			// 데이터가 없을 경우 HTTP 404 상태 반환
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/* 거래처 등록 */
	@PostMapping("/admin/partner/add")
	public String addPartner(PartnerDTO partner, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		request.setCharacterEncoding("utf-8");

		int r = yServ.addPartner(partner);

		return "redirect:/admin_Partner_List";
	}

	/* 거래처 아이디 중복 검사 */
	@PostMapping("/admin/partner/idCheck")
	@ResponseBody
	public int partnerIdCheck(@RequestParam(value = "partner_id") String partner_id) throws Exception {
		int count = yServ.getPartnerById(partner_id);
		System.out.println("partner_id: " + partner_id + ", count: " + count); // 디버깅 로그
		return count;
	}

	/* 거래처 수정 */
	@PostMapping("/admin/partner/modified")
	public String modifyPartner(PartnerDTO partner, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");

		int r = yServ.updatePartner(partner);

		return "redirect:/admin_Partner_List";
	}

	/* 거래처 목록 검색 필터 */
	@PostMapping("/admin/partner/filter")
	public ModelAndView filterPartners(@RequestParam(value = "partner_name", required = false) String partner_name,
			@RequestParam(value = "partner_manager", required = false) String partner_manager,
			@RequestParam(value = "partner_email", required = false) String partner_email,
			@RequestParam(value = "partner_phone", required = false) String partner_phone) {

		ModelAndView mv = new ModelAndView();

		// Service 호출
		List<PartnerDTO> pList = yServ.pList(partner_name, partner_manager, partner_email, partner_phone);

		mv.addObject("pList", pList);
		mv.setViewName("admin_Partner_List");

		return mv;
	}

	/* 거래처 삭제 */
	@PostMapping("/admin/partner/delete")
	public String deletePartners(@RequestParam("selectedIds") String selectedIds, RedirectAttributes redirectAttributes)
			throws Exception {
		if (selectedIds == null || selectedIds.isEmpty()) {
			throw new IllegalArgumentException("No IDs selected for deletion.");
		}

		String[] partnerIds = selectedIds.split(",");
		int result = yServ.deletePartners(partnerIds);

		// 삭제 완료 메시지 추가
		redirectAttributes.addFlashAttribute("message", "삭제가 완료되었습니다.");

		return "redirect:/admin_Partner_List";
	}

	/* 서류 관리 */
	/* 견적서 계약서 목록 */
	@GetMapping("/admin_Quotation_List")
	public String getQuotationList(Model model) {
		List<QuotationDTO> quotations = yServ.getQuotations(); // 서비스에서 리스트 가져오기
		model.addAttribute("qList", quotations);
		return "admin_Quotation_List";
	}

	/* 서류 등록 */
	@PostMapping("/admin/quotation/add")
	public String addQuotation(@ModelAttribute QuotationDTO quotationDTO, @RequestParam("file") MultipartFile file,
			RedirectAttributes rttr, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		// 파일 이름 추출
		if (file != null && !file.isEmpty()) {
			String originalFileName = file.getOriginalFilename();
			
			System.out.println(originalFileName);
		
			String safeFileName = originalFileName;

			if (!isAllowedFileType(safeFileName)) {
				rttr.addFlashAttribute("alertMessage", "엑셀 또는 워드 파일만 업로드 가능합니다.");
				System.out.println("Flash Attribute: 엑셀 또는 워드 파일만 업로드 가능합니다.");
				return "redirect:/admin_Quotation_List";
			}

			// DTO에 파일 이름 설정
			quotationDTO.setQuotation_file_name(safeFileName);

			// 로컬 파일 저장 (필요하면 사용, 안 하면 주석 처리)
			String uploadDir = "C:/local/uploads";
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File destination = new File(dir, safeFileName);
			
			System.out.println(destination);

			
			file.transferTo(destination);
		} else {
			rttr.addFlashAttribute("errorMesssage", "파일을 업로드해야 합니다.");
			return "redirect:/admin/quotation/add";
		}

		// 서비스 호출
		int result = yServ.addQuotation(quotationDTO);
		if (result > 0) {
			rttr.addFlashAttribute("successMessage", "서류 등록 완료!");
		} else {
			rttr.addFlashAttribute("errorMessage", "서류 등록 실패. 다시 시도하세요.");
		}

		return "redirect:/admin_Quotation_List";
	}

	/**
	 * 유효한 파일 확장자인지 검사
	 * 
	 * @param fileName 파일 이름
	 * @return 유효한 경우 true, 그렇지 않으면 false
	 */
	private boolean isAllowedFileType(String fileName) {
		String lowerCaseFileName = fileName.toLowerCase();
		return lowerCaseFileName.endsWith(".xlsx") || lowerCaseFileName.endsWith(".xls")
				|| lowerCaseFileName.endsWith(".docx") || lowerCaseFileName.endsWith(".doc");
	}

	/* 파일 다운로드 */
	private final String uploadDir = "C:/local/uploads";

	@GetMapping("/admin/download-file/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
		try {
			// 요청된 파일 이름을 URL 디코딩
	        String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

			// 정규식으로 안전한 파일 이름 변환
			String safeFileName = decodedFileName.replaceAll("[^a-zA-Z0-9ㄱ-ㅎ가-힣(). _-]", "_");

			// 파일 경로 생성
			Path filePath = Paths.get(uploadDir).resolve(safeFileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			// 파일 존재 및 읽기 가능 여부 확인
			if (!resource.exists() || !resource.isReadable()) {
				throw new RuntimeException("File not found: " + fileName);
			}

			// 파일 이름 인코딩
			String encodedFileName = UriUtils.encode(decodedFileName, "UTF-8");

			// 파일 응답 반환
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
					.body(resource);

		} catch (Exception e) {
			log.error("File download failed", e);
			throw new RuntimeException("File download failed", e);
		}
	}

	/* quotation_status 변경 */
	@PostMapping("/admin/quotation/updateStatus")
	@ResponseBody
	public Map<String, Object> updateQuotationStatus(@RequestBody Map<String, Object> requestData) {
		Map<String, Object> response = new HashMap<>();

		try {
			String quotationId = (String) requestData.get("quotationId");
			String newStatusString = (String) requestData.get("newStatus");
			int newStatus = Integer.parseInt(newStatusString);

			// 서비스 호출
			int result = yServ.updateQuotationStatus(quotationId, newStatus);

			response.put("success", result > 0);
		} catch (NumberFormatException e) {
			response.put("success", false);
			response.put("error", "유효하지 않은 상태 값입니다.");
		} catch (Exception e) {
			response.put("success", false);
			response.put("error", e.getMessage());
		}

		return response;
	}

	/* quotation 검색 필터 */
	@PostMapping("/admin/quotation/filter")
	public ModelAndView filterQuotations(@RequestParam(value = "quotation_id", required = false) String quotationId,
			@RequestParam(value = "quotation_partnername", required = false) String quotationPartnername,
			@RequestParam(value = "quotation_hqmanager", required = false) String quotationHqmanager,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "searchType", required = false) String searchType) {

		ModelAndView mv = new ModelAndView();

		List<QuotationDTO> qList = yServ.qList(quotationId, quotationPartnername, quotationHqmanager, startDate,
				endDate, searchType);

		mv.addObject("qList", qList);
		mv.setViewName("admin_Quotation_List");

		return mv;
	}

	/*  quotation_id 중복 체크 */
	@PostMapping("/admin/quotation/idCheck")
	@ResponseBody
	public int quotationIdCheck(@RequestParam(value = "quotation_id") String quotation_id) throws Exception {
		int count = yServ.getQuotationById(quotation_id);
		return count;
	}

	/* 상품 관리 */
	/* 상품 관리 리스트 */
	@GetMapping("/admin_ProductOrder_List")
	public ModelAndView admin_Order_List() {
		log.info("admin_ProductOrder_List()");
		mv = yServ.getOrderList();
		return mv;
	}

	/* 상품 등록 */
	// oList 초기화 메서드
	@GetMapping("admin_add_ProductOrder")
	public ModelAndView admin_add_ProductOrder(Model model) {
		ModelAndView mv = new ModelAndView();

		List<ProductDTO> pList = yServ.getProductList();

		mv.addObject("pList", pList);
		mv.setViewName("admin_add_ProductOrder");

		return mv;
	}

	/* 상품 추가 */
	@PostMapping("/admin/add/product")
	public String addProduct(@RequestParam(value = "product_id", required = false) List<String> productIds,
			Model model) {
		// 선택된 상품을 DB에 반영하거나 oList로 업데이트
		List<ProductDTO> selectedProducts = new ArrayList<>();
		for (String productId : productIds) {
			ProductDTO product = yServ.getProductById(productId);
			if (product != null) {
				selectedProducts.add(product);
			}
		}

		// 기존 oList에 반영
		model.addAttribute("oList", selectedProducts);

		return "admin_add_ProductOrder";
	}

	/* 상품 추가 모달 검색 필터 */
	@GetMapping("/user/order/request/detail")
	public ResponseEntity<List<ProductDTO>> order_request_detail(@RequestParam("product_name") String searchKeyword,
			HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");

		List<ProductDTO> products = yServ.order_request_detail(searchKeyword);

		if (products != null && !products.isEmpty()) {
			return ResponseEntity.ok(products);
		} else {
			// 404 대신 메시지 반환
			return ResponseEntity.ok(Collections.emptyList());
		}
	}

	/* hr 부서 이름 가져오기 */
	@GetMapping("/hqmanagers")
	@ResponseBody
	public List<Map<String, String>> getHQManagers() {
		return yServ.getAllHQManagers();
	}

	// 발주 요청 POST
	// 점주 발주 요청 완료
	@PostMapping("/user/order/request")
	@ResponseBody
	public ResponseEntity<String> orderRequest(@RequestBody OrderRequestDTO orderRequest) throws Exception {
	    // 1. OrderDTO 저장
	    String orderId = yServ.saveOrder(orderRequest.getOrderDTO());

	    // 2. OrderDetailDTO 저장
	    List<OrderDetailDTO> orderDetails = orderRequest.getOrderDetails();
	    for (OrderDetailDTO detail : orderDetails) {
	        detail.setOrderDetail_orderid(orderId); // 메인 발주 ID를 상세 발주에 매핑
	    }
	    yServ.saveOrderDetails(orderDetails);

	    return ResponseEntity.ok("발주 요청 저장 성공");
	}
	
	
	  @GetMapping("/user/order/list/detail") public ResponseEntity<OrderDTO>
	  listdetail(@RequestParam("order_id") String order_id) { OrderDTO dto =
	  yServ.listdetail(order_id);
	  
	  if (dto != null) { return ResponseEntity.ok(dto); } else { return
	  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); } }
	 

	  @GetMapping("/user/order/list/detail2")
	  @ResponseBody
	  public List<OrderDetailDTO> listdetail_2(@RequestParam("orderDetail_orderid") String orderDetail_orderid) {
	      List<OrderDetailDTO> list = yServ.listdetail_2(orderDetail_orderid);
	      return (list != null) ? list : new ArrayList<>();
	  }
	  
	  
	  
	  //현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	  //현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	  //현주ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ


	  	//인사추가
		@PostMapping("/admin/staff/add")
		public String addStaff(StaffDTO sDTO, @RequestPart("files") List<MultipartFile> files,
	    		HttpSession session) {
			String view = yServ.addStaff(sDTO, files, session);
			return view;
		}
		
		//@RequestParam은 검색 시에 사용???
		@GetMapping("/admin/staff/main")
		public ModelAndView staff_main(@RequestParam(value="hr_code", required=false)Integer hr_code, Model model) {
			
			if(hr_code != null) {
				StaffDTO staffDetail = yServ.staffDetail(hr_code);
				mv.addObject("staffDetail", staffDetail);
			} else {
				mv = yServ.staffList();
			}
			
			mv.setViewName("staff_main");
			return mv;
		}
		
		//인사 상세조회
		@GetMapping("/admin/staff/detail")
		@ResponseBody
		public ResponseEntity<Map<String, Object>> staffDetail(@RequestParam("hr_code") int hr_code) {
		    System.out.println("Searching for code: " + hr_code);
		    StaffDTO detail = yServ.staffDetail(hr_code);
		    Map<String, Object> response = new HashMap<>();
		    response.put("detail", detail);
		    System.out.println(response);
		    return ResponseEntity.ok(response);
		}
		 
		//인사 추가 시 아이디 중복체크
		@PostMapping("/admin/staff/idSearch")
		@ResponseBody
		public ResponseEntity<Map<String, Object>> idSearch(@RequestParam("hr_id") String hr_id) {
		    System.out.println("Searching for ID: " + hr_id);
		    StaffDTO idSearch = yServ.idSearch(hr_id);
		    System.out.println(idSearch);
		    Map<String, Object> response = new HashMap<>();
		    if (idSearch != null) {
		        response.put("idSearch", idSearch);
		    } else {
		        response.put("idSearch", null);  
		    }
		    
		    return ResponseEntity.ok(response);
		}

		//인사 수정
		@PostMapping("/admin/staff/edit")
		public String staffEdit(@RequestParam("hr_code")int hr_code,
	    		@RequestParam("hr_grade")int hr_grade,
	    		@RequestParam("hr_id") String hr_id,
	    		@RequestParam("hr_pwd") String hr_pwd,
	    		@RequestParam("hr_pdo")int hr_pdo,
	    		@RequestPart("files") List<MultipartFile> files,
	    		StaffDTO sDTO, HttpSession session) {
			
			//hr 테이블 수정
			String view = yServ.staffEdit(hr_code, hr_grade, hr_id, hr_pwd, hr_pdo, files, sDTO, session);
			
			return view;
		}
		
		//인사목록 검색 필터
		@PostMapping("/admin/staff/search")
		public ModelAndView staffSearch(
				@RequestParam(value = "del", required=false)Integer del,
				@RequestParam(value="staff_search", required=false)String staff_search,
				@RequestParam(value = "grade", required=false)Integer grade,
				@RequestParam(value="start", required=false)String start,
				@RequestParam(value="end", required=false)String end,
				@RequestParam(value="auth", required=false)Integer auth) {
			System.out.println(del);
			System.out.println(staff_search);
			System.out.println(grade);
			System.out.println(start);
			System.out.println(end);
			System.out.println(auth);
			mv = yServ.staffSearch(del, staff_search, grade, start, end, auth);
			return mv;	
		}
		
		
		
		//결재관리ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

		//내 결재관리 메인
		@GetMapping("/admin/approval/main")
		public ModelAndView staff_approval(Model model) {
	
			//시큐리티 아이디 가져오기
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        int hr_code = yServ.staff_get_hrCode(username);
			
	        //인사정보
			StaffDTO staff = yServ.staffDetail(hr_code);
						
			model.addAttribute("staff", staff);
			mv = yServ.appList(hr_code);
			return mv;
		}

		//캘린더
		@GetMapping("/admin/approval/calendar")
		@ResponseBody
		public List<ApprovalDTO> calendar() {
			List<ApprovalDTO> calendar = yServ.calendar();
			for (ApprovalDTO aDTO : calendar) {
				int hr_code = aDTO.getHr_code();
				StaffDTO staff = yServ.staffDetail(hr_code);
				aDTO.setStaffDTO(staff);
			}
			System.out.println(calendar);
		    return calendar;
		}
		
		//신규 결재 추가 시 결재자 찾기
		@PostMapping("/admin/approval/bossSearch")
		@ResponseBody
		public ResponseEntity<Map<String, Object>> bossSearch(@RequestParam("hr_name") String hr_name, @RequestParam("hr_code")String hr_code) {
		    System.out.println("Searching for ID: " + hr_name);
		    List<StaffDTO> boss = yServ.searchBoss(hr_name, hr_code);
		    System.out.println(boss);
		    Map<String, Object> response = new HashMap<>();
		    
		    if (boss != null) {
		        response.put("boss", boss); 
		    } else {
		        response.put("boss", null); 
		    }
		    
		    return ResponseEntity.ok(response); 
		}
		
		//신규 결재 저장
		@PostMapping("/admin/approval/add")
		public String addApproval(@RequestParam("hr_code")int hr_code, 
				@RequestParam("selected_hr_code")int selected_hr_code,
				@RequestParam("approvalFile") List<MultipartFile> files,
				ApprovalDTO aDTO) {
			System.out.println("hr_code"+hr_code);
			System.out.println("selected_hr_code"+selected_hr_code);
			aDTO.setBoss_code(selected_hr_code);
			String view = yServ.addApproval(hr_code, selected_hr_code, files, aDTO);
			return view;
		}
		
		//결재 상세페이지
		@GetMapping("/admin/approval/detail")
		@ResponseBody
		public ResponseEntity<Map<String, Object>> appDetail(
				 @RequestParam("app_id") int app_id,
				 @RequestParam("hr_code")int hr_code) {
		    System.out.println("Searching for code: " + app_id);
		    ApprovalDTO appDetail = yServ.appDetail(app_id, hr_code);
		    Map<String, Object> response = new HashMap<>();
		    response.put("appDetail", appDetail);
		    System.out.println(response);
		    
		    return ResponseEntity.ok(response);
		}
		
		//상사가 부결 시, 내가 취소시
		@PostMapping("/admin/approval/cancel")
		public String cancelApproval(@RequestParam("hr_code")int hr_code,
				@RequestParam("app_id")int app_id,
				@RequestParam(value="selected_hr_code", required=false)Integer selected_hr_code,
				@RequestParam("app_rejection")String app_rejection) {
			String view = yServ.cancelApproval(hr_code, app_id, app_rejection);
			return view;
		}
		
		//결재 완료
		@PostMapping("/admin/approval/complete")
		public String approveApproval (
		    		@RequestParam("hr_code")int hr_code,
		    		@RequestParam("app_id")int app_id,
		    		@RequestParam("boss_code_second")int boss_code_second) {
			String view = yServ.approveApproval(hr_code, app_id, boss_code_second);
			return view;
		}
		
		
		@Configuration
		public class StaticResourceConfig implements WebMvcConfigurer {

		    @Override
		    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		        // Map the URL "/uploads/**" to the local directory "C:/images/uploads/"
		        registry.addResourceHandler("/uploads/**")
		                .addResourceLocations("file:/C:/images/uploads/");
		        
		        registry.addResourceHandler("/uploadDoc/**")
	            .addResourceLocations("file:/C:/images/uploadDoc/");
		    }
		}

}

