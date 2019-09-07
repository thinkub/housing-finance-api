package com.kakao.hfapi.finance.model;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakao.hfapi.finance.entity.HousingFinanceSummaryByYear;
import com.kakao.hfapi.institute.model.InstituteCode;
import com.kakao.hfapi.institute.model.InstituteDetail;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SummaryByYear {
	private String name;
	private List<DetailOfYear> detailOfYears;

	public static SummaryByYear of(List<HousingFinanceSummaryByYear> summaryByYears) {
		return new SummaryByYear("주택금융 공급현황", DetailOfYear.of(summaryByYears));
	}

	public static SummaryByYear empty() {
		return new SummaryByYear("주택금융 공급현황", Collections.emptyList());
	}


	@Getter
	public static class DetailOfYear {
		private int year;
		@JsonProperty("total_amount")
		private long totalAmount;
		@JsonProperty("detail_amount")
		private List<InstituteDetail> instituteDetails;

		private static List<DetailOfYear> of(List<HousingFinanceSummaryByYear> summaryByYears) {
			Map<Integer, List<HousingFinanceSummaryByYear>> summaryByYearsMap =
					summaryByYears.stream()
								  .collect(groupingBy(HousingFinanceSummaryByYear::getYear));

			return summaryByYearsMap.entrySet().stream()
									.map(e -> new DetailOfYear(e.getKey(), e.getValue()))
									.sorted(Comparator.comparingInt(c -> c.year))
									.collect(Collectors.toList());
		}

		private DetailOfYear(Integer year, List<HousingFinanceSummaryByYear> summaryByYears) {
			summaryByYears.sort(Comparator.comparingInt(s -> InstituteCode.getType(s.getInstitute().getInstituteName()).getExcelOrder()));
			this.year = year;
			this.totalAmount = summaryByYears.stream()
											 .mapToLong(HousingFinanceSummaryByYear::getAmount).sum();
			this.instituteDetails = summaryByYears.stream()
												  .map(InstituteDetail::ofAmount)
												  .collect(toList());
		}

	}
}
