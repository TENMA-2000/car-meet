package com.example.carmeet.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.carmeet.dto.PostRequestDTO;
import com.example.carmeet.dto.PostResponseDTO;
import com.example.carmeet.entity.User;
import com.example.carmeet.security.UserDetailsImpl;
import com.example.carmeet.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		PostResponseDTO postResponseDTO = postService.getPost(postId, user);
		return ResponseEntity.ok(postResponseDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<PostResponseDTO>> getAllPosts(){
		List<PostResponseDTO> posts = postService.getAllPosts();
		return ResponseEntity.ok(posts);
	}
	
	

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostResponseDTO> createPost(
			@RequestPart("post") @Valid PostRequestDTO postRequestDTO,
			@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

		User user = userDetailsImpl.getUser();
		PostResponseDTO postResponseDTO = postService.createPost(postRequestDTO, multipartFile, user);
		return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postId,
			@Valid @RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		
		User user = userDetailsImpl.getUser();
		PostResponseDTO postResponseDTO = postService.updatePost(postId, postRequestDTO, user);

		return ResponseEntity.ok(postResponseDTO);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		postService.deletePost(postId, user);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/sorted")
	public ResponseEntity<Page<PostResponseDTO>> getSortedPosts(@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "desc")String direction,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		return ResponseEntity.ok(postService.getSortedPosts(sortBy, direction, page, size));
	}
	

}
