package com.kakao.hfapi.finance.controller;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kakao.hfapi.finance.entity.HousingFinance;
import com.kakao.hfapi.finance.entity.HousingFinanceHistory;
import com.kakao.hfapi.finance.repository.HousingFinanceRepository;
import com.kakao.hfapi.finance.repository.HousingFinanceSummaryRepository;
import com.kakao.hfapi.finance.service.HousingFinanceService;
import com.kakao.hfapi.institute.entity.Institute;
import com.kakao.hfapi.institute.model.InstituteCode;
import com.kakao.hfapi.institute.repository.InstituteRepository;
import com.kakao.hfapi.institute.service.InstituteService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HousingFinanceController.class)
public class HousingFinanceControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private HousingFinanceService housingFinanceService;
	@MockBean
	private HousingFinanceRepository housingFinanceRepository;
	@MockBean
	private HousingFinanceHistory housingFinanceHistory;
	@MockBean
	private HousingFinanceSummaryRepository housingFinanceSummaryRepository;
	@MockBean
	private InstituteService instituteService;
	@MockBean
	private InstituteRepository instituteRepository;

	@Test
	public void 저장한금융정보_히스토리ID로조회() throws Exception {
		InstituteCode instituteCode = InstituteCode.B000;
		Institute institute = Institute.of(instituteCode.name(), instituteCode.getBankName());
		HousingFinanceHistory housingFinanceHistory = HousingFinanceHistory.builder()
																		   .fileName("sample.csv")
																		   .housingFinanceHistoryId(1)
																		   .build();
		HousingFinance housingFinance = HousingFinance.builder()
													  .housingFinanceId(1)
													  .housingFinanceYear(2019)
													  .housingFinanceMonth(1)
													  .institute(institute)
													  .housingFinanceHistory(housingFinanceHistory)
													  .build();

		given(housingFinanceService.findHousingFinanceByHistoryId(1)).willReturn(Collections.singletonList(housingFinance));
		mockMvc.perform(get("/api/v1/housing/finances/1"))
			   .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$").isArray())
			   .andExpect(jsonPath("$.length()").value(1))
			   .andExpect(jsonPath("$[0].housingFinanceId").value(1))
			   .andExpect(jsonPath("$[0].housingFinanceYear").value(2019))
			   .andExpect(jsonPath("$[0].housingFinanceMonth").value(1))
			   .andExpect(jsonPath("$[0].institute.instituteCode").value("B000"));
	}
}