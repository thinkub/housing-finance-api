package com.kakao.hfapi.finance.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

import com.kakao.hfapi.finance.entity.HousingFinance;
import com.kakao.hfapi.finance.entity.HousingFinanceHistory;
import com.kakao.hfapi.finance.model.HousingFinanceDto;
import com.kakao.hfapi.finance.repository.HousingFinanceHistoryRepository;
import com.kakao.hfapi.finance.repository.HousingFinanceRepository;
import com.kakao.hfapi.util.CsvConverter;

@Service
@AllArgsConstructor
public class HousingFinanceService {
	private final HousingFinanceRepository housingFinanceRepository;
	private final HousingFinanceHistoryRepository housingFinanceHistoryRepository;

	@Transactional
	public int save(MultipartFile multipartFile) throws Exception {
		HousingFinanceHistory housingFinanceHistory = housingFinanceHistoryRepository.save(HousingFinanceHistory.getInstance());

		List<HousingFinanceDto> dtos = CsvConverter.makeHousingFinanceDtos(multipartFile);
		List<HousingFinance> housingFinances = dtos.stream()
												   .map(h -> HousingFinance.ofDtos(h, housingFinanceHistory))
												   .collect(Collectors.toList());
		housingFinanceRepository.saveAll(housingFinances);

		return housingFinanceHistory.getHousingFinanceHistoryId();
	}

	@Transactional(readOnly = true)
	public List<HousingFinance> findHousingFinanceByHistoryId(int housingFinanceHistoryId) {
		return housingFinanceRepository.findByHousingFinanceHistory(HousingFinanceHistory.builder()
																						 .housingFinanceHistoryId(housingFinanceHistoryId)
																						 .build());
	}
}
