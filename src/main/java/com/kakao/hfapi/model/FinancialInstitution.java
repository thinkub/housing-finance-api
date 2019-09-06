package com.kakao.hfapi.model;

import java.time.LocalDate;
import java.util.List;

import com.kakao.hfapi.institute.model.InstituteCode;

public class FinancialInstitution {
	private LocalDate year;
	private LocalDate month;
	private List<DetailAmount> detailAmounts;

	private static class DetailAmount {
		private InstituteCode instituteCode;
		private long amount;
	}
}
