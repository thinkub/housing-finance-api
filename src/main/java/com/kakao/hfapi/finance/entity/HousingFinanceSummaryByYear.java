package com.kakao.hfapi.finance.entity;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.kakao.hfapi.institute.entity.Institute;
import com.kakao.hfapi.institute.model.InstituteCode;

@Entity
@Table(name = "housing_finance_summary_year",
	   indexes = {@Index(columnList = "year, institute_code", unique = true)})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class HousingFinanceSummaryByYear {
	@Id
	@Column(name = "housing_finance_summary_year_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int housingFinanceSummaryByYearId;

	@Column(name = "year")
	private int year;

	@JoinColumn(name = "institute_code")
	@ManyToOne(fetch = FetchType.EAGER)
	private Institute institute;

	@Column(name = "amount")
	private long amount;

	public static List<HousingFinanceSummaryByYear> summaryByYears(List<HousingFinance> housingFinances) {
		Map<Integer, List<HousingFinance>> housingsByYearMap =
				housingFinances.stream()
							   .collect(groupingBy(HousingFinance::getHousingFinanceYear));

		List<HousingFinanceSummaryByYear> results = new ArrayList<>();
		for (Map.Entry<Integer, List<HousingFinance>> entry : housingsByYearMap.entrySet()) {
			int year = entry.getKey();
			List<HousingFinance> instituteByYear = entry.getValue();
			Map<String, Long> summaryByInstitutesMap = instituteByYear.stream()
																	  .collect(groupingBy(h -> h.getInstitute().getInstituteName(),
																						  summingLong(HousingFinance::getAmount)));
			results.addAll(summaryByInstitutes(year, summaryByInstitutesMap));
		}
		return results;
	}

	private static List<HousingFinanceSummaryByYear> summaryByInstitutes(int year, Map<String, Long> instituteSummaryMap) {
		return instituteSummaryMap.entrySet()
								  .stream()
								  .map(i -> HousingFinanceSummaryByYear.of(year, i.getKey(), i.getValue()))
								  .collect(Collectors.toList());
	}

	private static HousingFinanceSummaryByYear of(int year, String instituteName, long amount) {
		InstituteCode instituteCode = InstituteCode.getType(instituteName);
		return HousingFinanceSummaryByYear.builder()
										  .year(year)
										  .institute(Institute.of(instituteCode.name(), instituteName))
										  .amount(amount)
										  .build();
	}
}
