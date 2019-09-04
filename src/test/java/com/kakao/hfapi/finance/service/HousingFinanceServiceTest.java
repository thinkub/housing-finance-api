package com.kakao.hfapi.finance.service;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import lombok.extern.slf4j.Slf4j;

import com.kakao.hfapi.finance.entity.HousingFinanceHistory;
import com.kakao.hfapi.finance.repository.HousingFinanceHistoryRepository;
import com.kakao.hfapi.finance.repository.HousingFinanceRepository;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class HousingFinanceServiceTest {
	@InjectMocks
	private HousingFinanceService housingFinanceService;
	@Mock
	private HousingFinanceRepository housingFinanceRepository;
	@Mock
	private HousingFinanceHistoryRepository housingFinanceHistoryRepository;

	private MockMultipartFile multipartFile;

	@Before
	public void init() throws Exception {
		String fileName = "sample.csv";

		File file = new ClassPathResource(fileName).getFile();
		FileInputStream inputFile = new FileInputStream(file);
		multipartFile = new MockMultipartFile("file", fileName, "multipart/form-data", inputFile);
	}

	@Test
	public void saveTest() throws Exception {
		// given
		HousingFinanceHistory housingFinanceHistory = HousingFinanceHistory.builder()
																		   .housingFinanceHistoryId(1)
																		   .registerDatetime(LocalDateTime.now())
																		   .build();

		given(housingFinanceHistoryRepository.save(HousingFinanceHistory.getInstance())).willReturn(housingFinanceHistory);

		int historyId = housingFinanceService.save(multipartFile);
		log.info("{}", historyId);
	}

	@Test
	public void findHousingFinanceByHistoryId() {
	}
}