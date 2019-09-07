package com.kakao.hfapi.institute.model;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakao.hfapi.finance.entity.HousingFinanceSummaryByYear;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InstituteSupportAmount {
	@JsonProperty("bank")
	private String instituteName;
	@JsonProperty("support_amount")
	private List<InstituteDetail> instituteDetails;

	public static InstituteSupportAmount of(String instituteName, List<HousingFinanceSummaryByYear> summaryByYears) {
		InstituteDetail smallest = InstituteDetail.ofSmallestAmountByYear(summaryByYears);
		InstituteDetail largest = InstituteDetail.ofLargestAmountYear(summaryByYears);

		return new InstituteSupportAmount(instituteName, Arrays.asList(InstituteDetail.ofYearAndAmount(smallest),
																	   InstituteDetail.ofYearAndAmount(largest)));
	}
}
