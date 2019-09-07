package com.kakao.hfapi.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao.hfapi.finance.entity.HousingFinanceSummaryByYear;
import com.kakao.hfapi.institute.entity.Institute;

public interface HousingFinanceSummaryRepository extends JpaRepository<HousingFinanceSummaryByYear, Integer> {
	List<HousingFinanceSummaryByYear> findByInstitute(Institute institute);
}
