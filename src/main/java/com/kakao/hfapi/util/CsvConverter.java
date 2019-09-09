package com.kakao.hfapi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import com.kakao.hfapi.finance.model.HousingFinanceDto;

@Slf4j
public class CsvConverter {
	public static List<HousingFinanceDto> makeHousingFinanceDtos(MultipartFile file) throws Exception {
		try (InputStream in = file.getInputStream()) {
			BufferedReader input = new BufferedReader(new InputStreamReader(in));
			input.readLine();
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(input);
			List<HousingFinanceDto> results = new ArrayList<>();
			for (CSVRecord record : records) {
				List<HousingFinanceDto> dtos = HousingFinanceDto.ofLists(record);
				results.addAll(dtos);
			}
			return results;
		} catch (IOException e) {
			log.error("csv convering IOException: {}", e.getMessage());
			throw new IOException(e.getMessage());
		}
	}
}
