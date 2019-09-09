package com.kakao.hfapi.util;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import lombok.extern.slf4j.Slf4j;

import com.kakao.hfapi.finance.model.HousingFinanceDto;

@Slf4j
public class CsvConverterTest {

	@Test
	public void convert() throws Exception {
		String fileName = "sample.csv";

		File file = new ClassPathResource(fileName).getFile();
		FileInputStream inputFile = new FileInputStream(file);
		MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, "multipart/form-data", inputFile);

		List<HousingFinanceDto> results = CsvConverter.makeHousingFinanceDtos(multipartFile);
		log.info("results: {}", results);

		assertThat(results.size(), is(notNullValue()));
	}
}