package com.example.carmeet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.carmeet.dto.ProfileUpdateRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void 会員詳細ページへアクセス時に未ログインの場合は401エラーを返す() throws Exception {
		mockMvc.perform(get("/api/users/me"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void 会員詳細ページへアクセス時にログイン済みの場合は200OKを返す() throws Exception {
		mockMvc.perform(get("/api/users/me"))
				.andExpect(status().isOk());
	}

	@Test
	public void 会員編集ページアクセス時に未ログインの場合は401エラーを返す() throws Exception {
		ProfileUpdateRequestDTO profileUpdateRequestDTO = new ProfileUpdateRequestDTO();
		profileUpdateRequestDTO.setName("山本 智也");

		mockMvc.perform(put("/api/users/me")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(profileUpdateRequestDTO)))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void 会員編集ページアクセス時にログイン済みの場合は200OKを返す() throws Exception{
		ProfileUpdateRequestDTO profileUpdateRequestDTO = new ProfileUpdateRequestDTO();
		profileUpdateRequestDTO.setName("山本 智也");
		
		mockMvc.perform(put("/api/users/me")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(profileUpdateRequestDTO)))
				.andExpect(status().isOk());
	}
}
