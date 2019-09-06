package com.kakao.hfapi.finance.entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.kakao.hfapi.finance.model.HousingFinanceDto;
import com.kakao.hfapi.institute.entity.Institute;

@Entity
@Table(name = "institute_finance",
	   indexes = {@Index(columnList = "institute_code"),
				  @Index(columnList = "housing_finance_year, housing_finance_month")})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class HousingFinance {
	@Id
	@Column(name = "housing_finance_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int housingFinanceId;

	@Column(name = "housing_finance_year")
	private int housingFinanceYear;

	@Column(name = "housing_finance_month")
	private int housingFinanceMonth;

	@JoinColumn(name = "institute_code")
	@ManyToOne(fetch = FetchType.EAGER)
	private Institute institute;

	@Column(name = "amount")
	private long amount;

	@JoinColumn(name = "housing_finance_history_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private HousingFinanceHistory housingFinanceHistory;

	public static HousingFinance ofDtos(HousingFinanceDto dto, HousingFinanceHistory housingFinanceHistory) {
		return HousingFinance.builder()
							 .housingFinanceYear(dto.getYear())
							 .housingFinanceMonth(dto.getMonth())
							 .institute(Institute.of(dto.getInstituteCode().name(), dto.getInstituteCode().getBankName()))
							 .amount(dto.getAmount())
							 .housingFinanceHistory(housingFinanceHistory)
							 .build();

	}
}
