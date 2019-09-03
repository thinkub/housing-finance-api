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
	B000("주택도시기금"),
	B004("국민은행"),
	B020("우리은행"),
	B002("신한은행"),
	B027("힌극씨티은행"),
	B081("하나은행"),
	B011("농협은행/수협은행"),
	B005("외환은행"),
	B999("기타은행");

	private final String bankName;
	private static Map<String, InstituteCode> instituteCodeMap = new ConcurrentHashMap<>();

	static {
		Stream.of(values()).forEach(i -> instituteCodeMap.put(i.bankName, i));
	}

	public static InstituteCode getType(String instituteName) {
		return Optional.ofNullable(instituteCodeMap.get(instituteName))
					   .orElse(B999);
	}
}
