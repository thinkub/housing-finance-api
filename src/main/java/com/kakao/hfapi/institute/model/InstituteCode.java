package com.kakao.hfapi.institute.model;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InstituteCode {
	B000("주택도시기금", 2),
	B004("국민은행", 3),
	B020("우리은행", 4),
	B002("신한은행", 5),
	B027("힌극씨티은행", 6),
	B081("하나은행", 7),
	B011("농협은행/수협은행", 8),
	B005("외환은행", 9),
	B999("기타은행", 10);

	private final String bankName;
	private final int excelOrder;
	private static Map<String, InstituteCode> instituteCodeMap = new ConcurrentHashMap<>();
	private static Map<Integer, InstituteCode> instituteCodeExcelOrderMap = new ConcurrentHashMap<>();

	static {
		Stream.of(values()).forEach(i -> instituteCodeMap.put(i.bankName, i));
		Stream.of(values()).forEach(i -> instituteCodeExcelOrderMap.put(i.excelOrder, i));
	}

	public static InstituteCode getType(String instituteName) {
		return Optional.ofNullable(instituteCodeMap.get(instituteName))
					   .orElse(B999);
	}

	public static InstituteCode getTypeByExcelOrder(int excelOrder) {
		return Optional.ofNullable(instituteCodeExcelOrderMap.get(excelOrder))
					   .orElse(B999);
	}
}
