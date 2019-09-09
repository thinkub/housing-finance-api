package com.kakao.hfapi.finance.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;

import com.kakao.hfapi.finance.entity.HousingFinanceSummaryByYear;
import com.kakao.hfapi.institute.entity.Institute;
import com.kakao.hfapi.institute.model.InstituteCode;
import com.kakao.hfapi.institute.model.InstituteDetail;

public class SummaryByYearTest {
	private final int YEAR_2001 = 2001;
	private final int YEAR_2002 = 2002;
	private final InstituteCode instituteCode1 = InstituteCode.B000;
	private final InstituteCode instituteCode2 = InstituteCode.B002;

	private final HousingFinanceSummaryByYear summaryByYear1 =
			HousingFinanceSummaryByYear.builder()
									   .year(YEAR_2001)
									   .institute(Institute.of(instituteCode1.name(), instituteCode1.getBankName()))
									   .amount(10000)
									   .build();
	private final HousingFinanceSummaryByYear summaryByYear2 =
			HousingFinanceSummaryByYear.builder()
									   .year(YEAR_2001)
									   .institute(Institute.of(instituteCode2.name(), instituteCode2.getBankName()))
									   .amount(20000)
									   .build();
	private final HousingFinanceSummaryByYear summaryByYear3 =
			HousingFinanceSummaryByYear.builder()
									   .year(YEAR_2002)
									   .institute(Institute.of(instituteCode2.name(), instituteCode2.getBankName()))
									   .amount(50000)
									   .build();

	@Test
	public void 연도별합계_test() {
		// given
		List<HousingFinanceSummaryByYear> summaryByYears = Arrays.asList(summaryByYear1, summaryByYear2, summaryByYear3);

		// when
		SummaryByYear summaryByYear = SummaryByYear.of(summaryByYears);

		// then
		for (SummaryByYear.DetailOfYear detailOfYear : summaryByYear.getDetailOfYears()) {
			if (detailOfYear.getYear() == YEAR_2001) {
				assertThat(detailOfYear.getTotalAmount(), is(30000L));
			} else {
				assertThat(detailOfYear.getTotalAmount(), is(50000L));
			}
		}
	}

	@Test
	public void 연도별기관리스트_test() {
		// given
		List<HousingFinanceSummaryByYear> summaryByYears = Arrays.asList(summaryByYear1, summaryByYear2, summaryByYear3);

		// when
		SummaryByYear summaryByYear = SummaryByYear.of(summaryByYears);
		SummaryByYear.DetailOfYear detailOfYear = summaryByYear.getDetailOfYears()
															   .stream().filter(d -> d.getYear() == YEAR_2001)
															   .findFirst()
															   .get();

		List<InstituteDetail> actual = detailOfYear.getInstituteDetails();
		List<InstituteDetail> expect = Arrays.asList(InstituteDetail.ofAmount(summaryByYear1), InstituteDetail.ofAmount(summaryByYear2));

		// then
		assertThat(actual.get(0).getAmount(), is(expect.get(0).getAmount()));
	}
}