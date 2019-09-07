package com.kakao.hfapi.finance.entity;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.kakao.hfapi.institute.entity.Institute;
import com.kakao.hfapi.institute.model.InstituteCode;

public class HousingFinanceSummaryByYearTest {
	private final int YEAR_2001 = 2001;
	private final int YEAR_2002 = 2002;
	private final InstituteCode instituteCode1 = InstituteCode.B000;
	private final InstituteCode instituteCode2 = InstituteCode.B002;

	private final HousingFinance housingFinance1 = HousingFinance.builder()
																 .housingFinanceYear(YEAR_2001)
																 .housingFinanceMonth(1)
																 .institute(Institute.of(instituteCode1.name(), instituteCode1.getBankName()))
																 .amount(1000)
																 .build();
	private final HousingFinance housingFinance2 = HousingFinance.builder()
																 .housingFinanceYear(YEAR_2001)
																 .housingFinanceMonth(2)
																 .institute(Institute.of(instituteCode1.name(), instituteCode1.getBankName()))
																 .amount(1000)
																 .build();
	private final HousingFinance housingFinance3 = HousingFinance.builder()
																 .housingFinanceYear(YEAR_2002)
																 .housingFinanceMonth(1)
																 .institute(Institute.of(instituteCode1.name(), instituteCode1.getBankName()))
																 .amount(100)
																 .build();
	private final HousingFinance housingFinance4 = HousingFinance.builder()
																 .housingFinanceYear(YEAR_2001)
																 .housingFinanceMonth(1)
																 .institute(Institute.of(instituteCode2.name(), instituteCode2.getBankName()))
																 .amount(100)
																 .build();

	@Test
	public void 연도별기관별합계_test() {
		// given
		List<HousingFinance> housingFinances = Arrays.asList(housingFinance1, housingFinance2, housingFinance3, housingFinance4);

		// when
		List<HousingFinanceSummaryByYear> summaryByYears =  HousingFinanceSummaryByYear.summaryByYears(housingFinances);

		// then
		for (HousingFinanceSummaryByYear summaryByYear : summaryByYears) {
			if (summaryByYear.getYear() == YEAR_2001 && InstituteCode.getType(summaryByYear.getInstitute().getInstituteName()) == instituteCode1) {
				assertThat(summaryByYear.getAmount(), is(2000L));
			}
		}
	}

}