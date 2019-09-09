package com.kakao.hfapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kakao.hfapi.institute.service.InstituteService;

@SpringBootApplication
public class HousingFinanceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousingFinanceApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(InstituteService instituteService) {
		return (args) -> instituteService.saveAll();
	}

}
