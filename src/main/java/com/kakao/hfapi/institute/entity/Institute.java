package com.kakao.hfapi.institute.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "institute")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Institute {
	@Id
	@Column(name = "institute_code")
	private String instituteCode;

	@Column(name = "institute_name", nullable = false)
	private String instituteName;

	public static Institute of(String instituteCode, String instituteName) {
		return new Institute(instituteCode, instituteName);
	}
}
