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
import com.kakao.hfapi.finance.model.SummaryOfYear;
import com.kakao.hfapi.finance.repository.HousingFinanceHistoryRepository;
import com.kakao.hfapi.finance.repository.HousingFinanceRepository;
import com.kakao.hfapi.institute.entity.Institute;
import com.kakao.hfapi.institute.model.InstituteCode;
import com.kakao.hfapi.institute.model.InstituteDetail;
import com.kakao.hfapi.institute.model.InstituteSupportAmount;
import com.kakao.hfapi.util.CsvConverter;

@Service
@AllArgsConstructor
public class HousingFinanceService {
	private final HousingFinanceRepository housingFinanceRepository;
	private final HousingFinanceHistoryRepository housingFinanceHistoryRepository;

	@Transactional
	public int save(MultipartFile multipartFile) throws Exception {
		HousingFinanceHistory housingFinanceHistory =
				housingFinanceHistoryRepository.save(HousingFinanceHistory.getInstance(multipartFile.getOriginalFilename()));

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

	public SummaryOfYear findSummaryOfYear(int year) {
		List<HousingFinance> housingFinances = housingFinanceRepository.findByHousingFinanceYear(year);
		return convertSummaryOfYear(housingFinances);
	}

	public SummaryOfYear findAllSummaryOfYear() {
		List<HousingFinance> housingFinances = housingFinanceRepository.findAll();
		return convertSummaryOfYear(housingFinances);
	}

	public InstituteDetail findLargestInstituteByYears(int year) {
		List<HousingFinance> housingFinances = housingFinanceRepository.findByHousingFinanceYear(year);
		InstituteDetail instituteDetail = InstituteDetail.ofLargestAmount(housingFinances);

		return InstituteDetail.ofYear(instituteDetail.getInstituteName(), year);
	}

	public InstituteSupportAmount findInstituteSupport(String instituteName) {
		InstituteCode instituteCode = InstituteCode.getType(instituteName);
		List<HousingFinance> housingFinances =
				housingFinanceRepository.findByInstitute(Institute.of(instituteCode.name(), instituteCode.getBankName()));
		return InstituteSupportAmount.of(instituteName, housingFinances);
	}

	private SummaryOfYear convertSummaryOfYear(List<HousingFinance> housingFinances) {
		if (housingFinances == null) {
			return SummaryOfYear.empty();
		}

		return SummaryOfYear.of(housingFinances);
	}
}
