package com.kakao.hfapi.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao.hfapi.finance.entity.HousingFinance;
import com.kakao.hfapi.finance.entity.HousingFinanceHistory;
import com.kakao.hfapi.institute.entity.Institute;

public interface HousingFinanceRepository extends JpaRepository<HousingFinance, Integer> {
	List<HousingFinance> findByInstitute(Institute institute);
	List<HousingFinance> findByHousingFinanceHistory(HousingFinanceHistory housingFinanceHistory);
	List<HousingFinance> findByHousingFinanceYear(int year);
}
