package com.kakao.hfapi.institute.model;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakao.hfapi.finance.entity.HousingFinance;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InstituteSupportAmount {
	@JsonProperty("bank")
	private String instituteName;
	@JsonProperty("support_amount")
	private List<InstituteDetail> instituteDetails;

	public static InstituteSupportAmount of(String instituteName, List<HousingFinance> housingFinances) {
		return new InstituteSupportAmount(instituteName, Arrays.asList(InstituteDetail.ofSmallestAmountByInstitute(housingFinances),
																	   InstituteDetail.ofLargestAmountByInstitute(housingFinances)));
	}
}
