package com.kakao.hfapi.institute.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.kakao.hfapi.institute.entity.Institute;
import com.kakao.hfapi.institute.model.InstituteCode;
import com.kakao.hfapi.institute.repository.InstituteRepository;

@Service
@AllArgsConstructor
public class InstituteService {
	private final InstituteRepository instituteRepository;

	public List<Institute> findAll() {
		return instituteRepository.findAll();
	}

	public void saveAll() {
		List<Institute> institutes = Stream.of(InstituteCode.values())
										   .map(i -> Institute.of(i.name(), i.getBankName()))
										   .collect(Collectors.toList());

		instituteRepository.saveAll(institutes);
	}
}
