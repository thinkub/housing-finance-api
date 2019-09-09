package com.kakao.hfapi.finance.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

import com.kakao.hfapi.finance.entity.HousingFinance;
import com.kakao.hfapi.finance.model.SummaryByYear;
import com.kakao.hfapi.finance.service.HousingFinanceService;
import com.kakao.hfapi.institute.model.InstituteDetail;
import com.kakao.hfapi.institute.model.InstituteSupportAmount;

@RestController
@RequestMapping("/api/v1/housing/finances")
@AllArgsConstructor
public class HousingFinanceController {
	private final HousingFinanceService housingFinanceService;

	@PostMapping
	public ResponseEntity upload(MultipartFile multipartFile) throws Exception {
		int housingFinanceHistoryId = housingFinanceService.save(multipartFile);
		return ResponseEntity.ok().body("/api/v1/housing/finance/" + housingFinanceHistoryId);
	}

	@GetMapping("/{historyId}")
	public ResponseEntity<List<HousingFinance>> getHousingFinance(@PathVariable int historyId) {
		List<HousingFinance> housingFinances = housingFinanceService.findHousingFinanceByHistoryId(historyId);
		return ResponseEntity.ok().body(housingFinances);
	}

	@GetMapping("/years/summary")
	public ResponseEntity<SummaryByYear> getAllHousingFinanceSummaryByYear() {
		SummaryByYear summaryByYear = housingFinanceService.findAllSummaryByYear();
		return ResponseEntity.ok().body(summaryByYear);
	}

	@GetMapping("/institute-years/largest-amount")
	public ResponseEntity<InstituteDetail> getLargestAmountYearByInstituteAndYear() {
		InstituteDetail instituteDetail = housingFinanceService.findLargestAmountYearByInstituteAndYear();
		return ResponseEntity.ok().body(instituteDetail);
	}

	@GetMapping("/institute/{instituteName}/support-amount")
	public ResponseEntity<InstituteSupportAmount> getInstituteSupport(@PathVariable String instituteName) {
		InstituteSupportAmount instituteSupportAmount = housingFinanceService.findInstituteSupport(instituteName);
		return ResponseEntity.ok().body(instituteSupportAmount);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handelException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							 .body(e.getMessage());
	}
}
