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



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class LikeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void いいねボタンを未ログインで押した場合は401エラーを返す() throws Exception {
		Long postId = 1L;
		Long userId = 2L;
		
		mockMvc.perform(post("/api/likes/{postId}/{userId}", postId, userId))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void いいねボタンをログイン済みで押した場合は200OKエラーを返す() throws Exception {
		Long postId = 1L;
		Long userId = 2L;
		
		mockMvc.perform(post("/api/likes/{postId}/{userId}", postId, userId))
		.andExpect(status().isOk());
	}
	
	@Test
	public void いいねを未ログインで削除した場合は401エラーを返す() throws Exception {
		Long postId = 1L;
		Long userId = 2L;
		
		mockMvc.perform(delete("/api/likes/{postId}/{userId}", postId, userId))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void いいねをログイン済みで削除した場合は200OKエラーを返す() throws Exception {
		Long postId = 1L;
		Long userId = 2L;
		
		mockMvc.perform(delete("/api/likes/{postId}/{userId}", postId, userId))
		.andExpect(status().isOk());
	}
}
