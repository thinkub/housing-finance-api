package com.kakao.hfapi.institute.model;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.hfapi.finance.entity.HousingFinance;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class InstituteDetail {
	private int year;
	private String instituteName;
	private long amount;

	public static List<InstituteDetail> ofList(List<HousingFinance> housingFinances) {
		Map<String, Long> instituteSummaryMap =
				housingFinances.stream()
							   .collect(groupingBy(h -> h.getInstitute().getInstituteName(), summingLong(HousingFinance::getAmount)));

		return instituteSummaryMap.entrySet()
								  .stream()
								  .map(e -> InstituteDetail.ofAmount(e.getKey(), e.getValue()))
								  .sorted(Comparator.comparingInt(c -> InstituteCode.getType(c.instituteName).getExcelOrder()))
								  .collect(toList());
	}

	public static InstituteDetail ofLargestAmount(List<HousingFinance> housingFinances) {
		List<InstituteDetail> instituteDetails = ofList(housingFinances);
		return instituteDetails.stream()
							   .max(Comparator.comparingLong(InstituteDetail::getAmount))
							   .orElse(null);
	}

	public static InstituteDetail ofLargestAmountByInstitute(List<HousingFinance> housingFinances) {
		List<InstituteDetail> instituteDetails = ofYearsSummary(housingFinances);
		return instituteDetails.stream()
							   .max(Comparator.comparingLong(InstituteDetail::getAmount))
							   .orElse(null);
	}

	public static InstituteDetail ofSmallestAmountByInstitute(List<HousingFinance> housingFinances) {
		List<InstituteDetail> instituteDetails = ofYearsSummary(housingFinances);
		return instituteDetails.stream()
							   .min(Comparator.comparingLong(InstituteDetail::getAmount))
							   .orElse(null);
	}

	public static InstituteDetail ofYear(String instituteName, int year) {
		return new InstituteDetail(instituteName, year);
	}

	private static List<InstituteDetail> ofYearsSummary(List<HousingFinance> housingFinances) {
		Map<Integer, Long> yearsSummaryMap =
				housingFinances.stream()
							   .collect(groupingBy(HousingFinance::getHousingFinanceYear, summingLong(HousingFinance::getAmount)));

		return yearsSummaryMap.entrySet()
							  .stream()
							  .map(e -> InstituteDetail.ofYearAndAmount(e.getKey(), e.getValue()))
							  .collect(toList());
	}

	private static InstituteDetail ofAmount(String instituteName, long amount) {
		return new InstituteDetail(instituteName, amount);
	}

	private static InstituteDetail ofYearAndAmount(int year, long amount) {
		return new InstituteDetail(year, amount);
	}

	private InstituteDetail(String instituteName, long amount) {
		this.instituteName = instituteName;
		this.amount = amount;
	}

	private InstituteDetail(String instituteName, int year) {
		this.year = year;
		this.instituteName = instituteName;
	}

	private InstituteDetail(int year, long amount) {
		this.year = year;
		this.amount = amount;
	}
}
