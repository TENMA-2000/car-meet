package com.example.carmeet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.carmeet.dto.LoginRequestDTO;
import com.example.carmeet.entity.Role;
import com.example.carmeet.entity.User;
import com.example.carmeet.repository.RoleRepository;
import com.example.carmeet.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	public void setUp() throws IllegalAccessException{
		Role role = roleRepository.findByName("ROLE_GENERAL")
				.orElseThrow(() -> new IllegalStateException("ROLE_GENERALが存在しません。"));
		
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword(passwordEncoder.encode("password123"));
		user.setName("テストユーザー");
		user.setRole(role);
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	void debugCheck() {
	    System.out.println("★ロール一覧: " + roleRepository.findAll());
	}
	
	@Test
	public void testLoginSuccess() throws Exception {
		System.out.println("★ testLoginSuccess 実行中");
		
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
		loginRequestDTO.setEmail("test@example.com");
		loginRequestDTO.setPassword("password123");
		
		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequestDTO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("ログインに成功しました。"))
		.andExpect(jsonPath("$.token").isNotEmpty())
		.andExpect(jsonPath("$.userId").isNumber())
		.andExpect(jsonPath("$.userName").value("テストユーザー"));
	}
	
	@Test
	public void testLoginFailure() throws Exception {
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
		loginRequestDTO.setEmail("wrong@exmple.com");
		loginRequestDTO.setPassword("wrongpassword");
		
		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequestDTO)))
		.andExpect(status().isUnauthorized())
		.andExpect(jsonPath("$.message").exists());
	}
}
