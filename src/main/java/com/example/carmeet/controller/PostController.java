package com.example.carmeet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmeet.dto.PostRequestDTO;
import com.example.carmeet.dto.PostResponseDTO;
import com.example.carmeet.entity.User;
import com.example.carmeet.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
		PostResponseDTO postResponseDTO = postService.getPost(postId, user);
		return ResponseEntity.ok(postResponseDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<PostResponseDTO>> getAllPosts(){
		List<PostResponseDTO> posts = postService.getAllPosts();
		return ResponseEntity.ok(posts);
	}
	
	

	@PostMapping
	public ResponseEntity<PostResponseDTO> createPost(
			@Valid @RequestBody PostRequestDTO postRequestDTO,
			@AuthenticationPrincipal User user) {

		PostResponseDTO postResponseDTO = postService.createPost(postRequestDTO, user);
		return ResponseEntity.ok(postResponseDTO);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postId,
			@Valid @RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal User user) {
		PostResponseDTO postResponseDTO = postService.updatePost(postId, postRequestDTO, user);

		return ResponseEntity.ok(postResponseDTO);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user) {
		postService.deletePost(postId, user);
		return ResponseEntity.noContent().build();
	}

}
