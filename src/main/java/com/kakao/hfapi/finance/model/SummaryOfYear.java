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
import com.kakao.hfapi.finance.entity.HousingFinance;
import com.kakao.hfapi.institute.model.InstituteDetail;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SummaryOfYear {
	private String name;
	private List<DetailOfYear> detailOfYears;

	public static SummaryOfYear of(List<HousingFinance> housingFinances) {
		return new SummaryOfYear("주택금융 공급현황", DetailOfYear.of(housingFinances));
	}

	public static SummaryOfYear empty() {
		return new SummaryOfYear("주택금융 공급현황", Collections.emptyList());
	}


	@Getter
	private static class DetailOfYear {
		private int year;
		@JsonProperty("total_amount")
		private long totalAmount;
		@JsonProperty("detail_amount")
		private List<InstituteDetail> instituteDetails;

		private static List<DetailOfYear> of(List<HousingFinance> housingFinances) {
			Map<Integer, List<HousingFinance>> housingFinanceMapOfYearMap =
					housingFinances.stream()
								   .collect(groupingBy(HousingFinance::getHousingFinanceYear));

			return housingFinanceMapOfYearMap.entrySet().stream()
											 .map(e -> new DetailOfYear(e.getKey(), e.getValue()))
											 .sorted(Comparator.comparingInt(c -> c.year))
											 .collect(Collectors.toList());
		}

		private DetailOfYear(Integer year, List<HousingFinance> housingFinances) {
			this.year = year;
			this.totalAmount = housingFinances.stream().mapToLong(HousingFinance::getAmount).sum();
			this.instituteDetails = InstituteDetail.ofList(housingFinances);
		}

	}
}
