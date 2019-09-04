package com.kakao.hfapi.finance.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import com.kakao.hfapi.institute.model.InstituteCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HousingFinanceDto {
	private int year;
	private int month;
	private InstituteCode instituteCode;
	private long amount;

	public static List<HousingFinanceDto> ofLists(CSVRecord csvRecord) {
		List<HousingFinanceDto> housingFinanceDtos = new ArrayList<>();
		for (int i = 2; i < csvRecord.size() - 2; i++) {
			if (StringUtils.isEmpty(csvRecord.get(i))) {
				continue;
			}
			housingFinanceDtos.add(new HousingFinanceDto(convert(csvRecord.get(0)),
														 convert(csvRecord.get(1)),
														 InstituteCode.getTypeByExcelOrder(i),
														 convert(csvRecord.get(i))));
		}
		return housingFinanceDtos;
	}

	private static int convert(String s) {
		return Integer.valueOf(s.replaceAll(",", ""));
	}
}
