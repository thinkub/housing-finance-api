package com.kakao.hfapi.institute.model;

import java.util.Comparator;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakao.hfapi.finance.entity.HousingFinanceSummaryByYear;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class InstituteDetail {
	private int year;
	private String instituteName;
	private long amount;

	public static InstituteDetail ofEmpty() {
		return new InstituteDetail();
	}

	public static InstituteDetail ofLargestAmountYear(List<HousingFinanceSummaryByYear> summaryByYears) {
		HousingFinanceSummaryByYear summaryByYear = summaryByYears.stream()
																  .max(Comparator.comparingLong(HousingFinanceSummaryByYear::getAmount))
																  .orElse(null);
		return new InstituteDetail(summaryByYear.getYear(),
								   summaryByYear.getInstitute().getInstituteName(),
								   summaryByYear.getAmount());
	}

	public static InstituteDetail ofSmallestAmountByYear(List<HousingFinanceSummaryByYear> summaryByYears) {
		HousingFinanceSummaryByYear summaryByYear = summaryByYears.stream()
																  .min(Comparator.comparingLong(HousingFinanceSummaryByYear::getAmount))
																  .orElse(null);
		return new InstituteDetail(summaryByYear.getYear(),
								   summaryByYear.getInstitute().getInstituteName(),
								   summaryByYear.getAmount());
	}

	public static InstituteDetail ofYear(InstituteDetail instituteDetail) {
		return new InstituteDetail(instituteDetail.getInstituteName(), instituteDetail.getYear());
	}

	public static InstituteDetail ofAmount(HousingFinanceSummaryByYear summaryByYear) {
		return new InstituteDetail(summaryByYear.getInstitute().getInstituteName(), summaryByYear.getAmount());
	}

	public static InstituteDetail ofYearAndAmount(InstituteDetail instituteDetail) {
		return new InstituteDetail(instituteDetail.getYear(), instituteDetail.getAmount());
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
