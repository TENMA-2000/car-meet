package com.example.carmeet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void 投稿詳細ページへアクセス時に未ログインの場合は401エラーを返す() throws Exception {
		mockMvc.perform(get("/api/posts/{postId}", 1L))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void 投稿詳細ページへアクセス時にログイン済みの場合は200OKを返す() throws Exception {
		mockMvc.perform(get("/api/posts/{postId}", 1L))
				.andExpect(status().isOk());
	}

	@Test
	public void 投稿一覧ページへアクセス時に未ログインの場合は401エラーを返す() throws Exception {
		mockMvc.perform(get("/api/posts"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void 投稿一覧ページへアクセス時にログイン済みの場合は200OKを返す() throws Exception {
		mockMvc.perform(get("/api/posts"))
				.andExpect(status().isOk());
	}

	@Test
	public void 投稿作成ページへアクセス時に未ログインの場合は401エラーを返す() throws Exception {

		String postJsonStr = """
				{
				"caption: "投稿テスト",
				"locationName": "TEST場所"
				}
				""";

		MockMultipartFile postMockMultipartFile = new MockMultipartFile(
				"post",
				"post.json",
				MediaType.APPLICATION_JSON_VALUE,
				postJsonStr.getBytes());

		MockMultipartFile imageMockMultipartFile = new MockMultipartFile(
				"multipartFile",
				"test.jpg",
				MediaType.IMAGE_JPEG_VALUE,
				"fale-image-data".getBytes());

		mockMvc.perform(multipart("/api/posts")
				.file(postMockMultipartFile)
				.file(imageMockMultipartFile)
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void 投稿作成ページへアクセス時にログイン済みの場合は201OKを返す() throws Exception {
		String postJsonStr = """
				{
				      "caption": "TESTの投稿です。",
				      "mediaUrl": "/images/test.jpg",
				      "mediaType": "image/jpeg",
				      "isStory": false,
				      "locationName": "TESTロケーション"
				    }
				""";

		MockMultipartFile postMockMultipartFile = new MockMultipartFile(
				"post",
				"post.json",
				MediaType.APPLICATION_JSON_VALUE,
				postJsonStr.getBytes());

		MockMultipartFile imageMockMultipartFile = new MockMultipartFile(
				"multipartFile",
				"test.jpg",
				MediaType.IMAGE_JPEG_VALUE,
				"fale-image-data".getBytes());

		mockMvc.perform(multipart("/api/posts")
				.file(postMockMultipartFile)
				.file(imageMockMultipartFile)
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.caption").value("TESTの投稿です。"))
				.andExpect(jsonPath("$.locationName").value("TESTロケーション"))
				.andExpect(jsonPath("$.mediaUrl").isNotEmpty())
				.andExpect(jsonPath("$.userName").value("山田太郎"))
				.andExpect(jsonPath("$.likeCount").value(0));
	}

	@Test
	public void 投稿編集ページへアクセス時に未ログインの場合は401エラーを返す() throws Exception {
		String json = """
				{
				"caption: "編集投稿テスト",
				"locationName": "編集TEST場所"
				}
				""";

		mockMvc.perform(put("/api/posts/{postId}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))

				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUserDetails("ichiro.tanaka@example.com")
	public void 他人の投稿の編集をしようとした場合は403エラーを返す() throws Exception {
		String json = """
				{
				      "caption": "他人の投稿です。",
				      "mediaUrl": "/images/test.jpg",
				      "mediaType": "image/jpeg",
				      "isStory": false,
				      "locationName": "不正なロケーション"
				    }
				""";

		Long postId = 2L;

		mockMvc.perform(put("/api/posts/{postId}", postId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))

				.andExpect(status().isForbidden());
	}

	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void 投稿編集ページへアクセス時にログイン済みの場合は200OKを返す() throws Exception {
		String updatedJson = """
				{
				      "caption": "編集された投稿です。",
				      "mediaUrl": "/images/test.jpg",
				      "mediaType": "image/jpeg",
				      "isStory": false,
				      "locationName": "編集したロケーション"
				    }
				""";

		Long postId = 1L;

		mockMvc.perform(put("/api/posts/{postId}", postId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatedJson))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$.caption").value("編集された投稿です。"))
				.andExpect(jsonPath("$.locationName").value("編集したロケーション"));
	}

	@Test
	public void 投稿削除する時に未ログインの場合は401エラーを返す() throws Exception {

		Long postId = 1L;

		mockMvc.perform(delete("/api/posts/{postId}", postId))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUserDetails("ichiro.tanaka@example.com")
	public void 他人の投稿の削除をしようとした場合は403エラーを返す() throws Exception {
		Long postId = 1L;

		mockMvc.perform(delete("/api/posts/{postId}", postId))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void 投稿削除する時にログイン済みの場合は204NoContentを返す() throws Exception {
		Long postId = 1L;

		mockMvc.perform(delete("/api/posts/{postId}", postId))
				.andExpect(status().isNoContent());
	}


	@Test
	@WithUserDetails("taro.yamada@example.com")
	public void ソート付き投稿取得APIが正常な場合は200OKを返す() throws Exception {
		mockMvc.perform(get("/api/posts/sorted"))
				.andExpect(status().isOk());
	}

}
