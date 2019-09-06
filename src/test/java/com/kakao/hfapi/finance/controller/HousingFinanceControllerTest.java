package com.kakao.hfapi.finance.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kakao.hfapi.finance.entity.HousingFinanceHistory;
import com.kakao.hfapi.finance.repository.HousingFinanceRepository;
import com.kakao.hfapi.finance.service.HousingFinanceService;


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

	@Test
	public void uploadTest() throws Exception {
		String fileName = "sample.csv";
		File file = new ClassPathResource(fileName).getFile();
		FileInputStream inputFile = new FileInputStream(file);
		MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, "multipart/form-data", inputFile);

		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.multipart("/api/v1/housing/finances").file(multipartFile);

		mockMvc.perform(builder)
			   .andDo(print())
			   .andExpect(status().isOk());
	}

	@Test
	public void 저장된금융정보_historyId로조회() {


	}

	@Test
	public void getHousingFinanceSummaryOfYear() {
	}

	@Test
	public void getAllHousingFinanceSummaryOfYear() {
	}
}