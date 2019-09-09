package com.kakao.hfapi.finance.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

import com.kakao.hfapi.finance.entity.HousingFinance;
import com.kakao.hfapi.finance.entity.HousingFinanceHistory;
import com.kakao.hfapi.finance.entity.HousingFinanceSummaryByYear;
import com.kakao.hfapi.finance.model.HousingFinanceDto;
import com.kakao.hfapi.finance.model.SummaryByYear;
import com.kakao.hfapi.finance.repository.HousingFinanceHistoryRepository;
import com.kakao.hfapi.finance.repository.HousingFinanceRepository;
import com.kakao.hfapi.finance.repository.HousingFinanceSummaryRepository;
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
	private final HousingFinanceSummaryRepository housingFinanceSummaryRepository;

	@Transactional
	public int save(MultipartFile multipartFile) throws Exception {
		HousingFinanceHistory housingFinanceHistory =
				housingFinanceHistoryRepository.save(HousingFinanceHistory.getInstance(multipartFile.getOriginalFilename()));

		List<HousingFinanceDto> dtos = CsvConverter.makeHousingFinanceDtos(multipartFile);
		List<HousingFinance> housingFinances = dtos.stream()
												   .map(h -> HousingFinance.ofDtos(h, housingFinanceHistory))
												   .collect(Collectors.toList());
		housingFinanceRepository.saveAll(housingFinances);
		saveSummaryByYear(housingFinances);

		return housingFinanceHistory.getHousingFinanceHistoryId();
	}

	@Transactional(readOnly = true)
	public List<HousingFinance> findHousingFinanceByHistoryId(int housingFinanceHistoryId) {
		return housingFinanceRepository.findByHousingFinanceHistory(HousingFinanceHistory.builder()
																						 .housingFinanceHistoryId(housingFinanceHistoryId)
																						 .build());
	}

	public SummaryByYear findAllSummaryByYear() {
		List<HousingFinanceSummaryByYear> summaryByYears = housingFinanceSummaryRepository.findAll();
		if (summaryByYears == null) {
			SummaryByYear.empty();
		}
		return SummaryByYear.of(summaryByYears);
	}

	public InstituteDetail findLargestAmountYearByInstituteAndYear() {
		List<HousingFinanceSummaryByYear> summaryByYears = housingFinanceSummaryRepository.findAll();
		if (summaryByYears == null) {
			return InstituteDetail.ofEmpty();
		}
		InstituteDetail instituteDetail = InstituteDetail.ofLargestAmountYear(summaryByYears);
		return InstituteDetail.ofYear(instituteDetail);

	}

	public InstituteSupportAmount findInstituteSupport(String instituteName) {
		InstituteCode instituteCode = InstituteCode.getType(instituteName);
		List<HousingFinanceSummaryByYear> summaryByYears =
				housingFinanceSummaryRepository.findByInstitute(Institute.of(instituteCode.name(), instituteCode.getBankName()));
		return InstituteSupportAmount.of(instituteName, summaryByYears);
	}

	private void saveSummaryByYear(List<HousingFinance> housingFinances) {
		List<HousingFinanceSummaryByYear> summaryByYears = HousingFinanceSummaryByYear.summaryByYears(housingFinances);
		housingFinanceSummaryRepository.saveAll(summaryByYears);
	}
}
