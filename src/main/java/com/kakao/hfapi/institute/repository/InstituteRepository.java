package com.kakao.hfapi.institute.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao.hfapi.institute.entity.Institute;

public interface InstituteRepository extends JpaRepository<Institute, String> {
}
