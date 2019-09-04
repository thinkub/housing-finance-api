package com.kakao.hfapi.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao.hfapi.finance.entity.HousingFinanceHistory;

public interface HousingFinanceHistoryRepository extends JpaRepository<HousingFinanceHistory, Integer> {
}
