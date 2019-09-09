package com.kakao.hfapi.institute.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.kakao.hfapi.finance.entity.HousingFinanceSummaryByYear;
import com.kakao.hfapi.institute.entity.Institute;

public class InstituteSupportAmountTest {
	private final int YEAR_2001 = 2001;
	private final int YEAR_2002 = 2002;
	private final int YEAR_2003 = 2003;
	private final InstituteCode instituteCode = InstituteCode.B000;
	private final Institute institute = Institute.of(instituteCode.name(), instituteCode.getBankName());

	private final HousingFinanceSummaryByYear summaryByYear1 =
			HousingFinanceSummaryByYear.builder()
									   .year(YEAR_2001)
									   .institute(institute)
									   .amount(10000)
									   .build();
	private final HousingFinanceSummaryByYear summaryByYear2 =
			HousingFinanceSummaryByYear.builder()
									   .year(YEAR_2002)
									   .institute(institute)
									   .amount(20000)
									   .build();
	private final HousingFinanceSummaryByYear summaryByYear3 =
			HousingFinanceSummaryByYear.builder()
									   .year(YEAR_2003)
									   .institute(institute)
									   .amount(30000)
									   .build();

	@Test
	public void 기관별최소금액_최대금액리스트_test() {
		// given
		List<HousingFinanceSummaryByYear> summaryByYears = Arrays.asList(summaryByYear1, summaryByYear2, summaryByYear3);

		// when
		InstituteSupportAmount instituteSupportAmount = InstituteSupportAmount.of(instituteCode.getBankName(), summaryByYears);

		// then
		List<InstituteDetail> actual = instituteSupportAmount.getInstituteDetails();

		// then
		assertThat(actual.get(0).getYear(), is(YEAR_2001));
		assertThat(actual.get(1).getYear(), is(YEAR_2003));
	}

}