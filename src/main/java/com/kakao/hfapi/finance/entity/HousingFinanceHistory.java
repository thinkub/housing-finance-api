package com.kakao.hfapi.finance.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "institute_finance_history")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class HousingFinanceHistory {
	@Id
	@Column(name = "housing_finance_history_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int housingFinanceHistoryId;

	@Column(name = "register_datetime")
	private LocalDateTime registerDatetime;

	public static HousingFinanceHistory getInstance() {
		return HousingFinanceHistory.builder()
									.registerDatetime(LocalDateTime.now())
									.build();
	}
}
