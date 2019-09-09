package com.kakao.hfapi.institute.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import com.kakao.hfapi.institute.entity.Institute;
import com.kakao.hfapi.institute.service.InstituteService;

@AllArgsConstructor
@RestController
public class InstituteController {
	private final InstituteService instituteService;

	@GetMapping("/api/v1/housing/institutes")
	public ResponseEntity<List<Institute>> getInstitutes() {
		List<Institute> institutes = instituteService.findAll();
		return ResponseEntity.ok().body(institutes);
	}
}
