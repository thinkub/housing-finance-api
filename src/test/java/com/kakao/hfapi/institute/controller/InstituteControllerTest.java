package com.kakao.hfapi.institute.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kakao.hfapi.institute.entity.Institute;
import com.kakao.hfapi.institute.model.InstituteCode;
import com.kakao.hfapi.institute.repository.InstituteRepository;
import com.kakao.hfapi.institute.service.InstituteService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = InstituteController.class)
public class InstituteControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private InstituteService instituteService;
	@MockBean
	private InstituteRepository instituteRepository;

	@Test
	public void 금융기목록_조회() throws Exception {
		given(instituteService.findAll()).willReturn(List.of(Institute.of(InstituteCode.B000.name(), InstituteCode.B000.getBankName()),
															 Institute.of(InstituteCode.B002.name(), InstituteCode.B002.getBankName())));

		mockMvc.perform(get("/api/v1/housing/institutes"))
			   .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$").isArray())
			   .andExpect(jsonPath("$.length()").value(2))
			   .andExpect(jsonPath("$[0].instituteCode").value("B000"))
			   .andExpect(jsonPath("$[0].instituteName").value("주택도시기금"));
	}
}