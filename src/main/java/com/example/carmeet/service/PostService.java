package com.example.carmeet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.carmeet.dto.PostRequestDTO;
import com.example.carmeet.dto.PostResponseDTO;
import com.example.carmeet.entity.Post;
import com.example.carmeet.entity.User;
import com.example.carmeet.exception.PostNotFoundException;
import com.example.carmeet.exception.UnauthorizedPostAccessException;
import com.example.carmeet.repository.PostRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	
	public PostResponseDTO getPost(Long postId, User user) {
		Post post = postRepository.findById(postId)
				.orElseThrow(PostNotFoundException::new);
		
		if (!post.getUser().getUserId().equals(user.getUserId())) {
			throw new UnauthorizedPostAccessException();
		}
		
		PostResponseDTO postResponseDTO = new PostResponseDTO();
		postResponseDTO.setPostId(post.getPostId());
		postResponseDTO.setCaption(post.getCaption());
		postResponseDTO.setMediaUrl(post.getMediaUrl());
		postResponseDTO.setMediaType(post.getMediaType());
		postResponseDTO.setIsStory(post.getIsStory());
		postResponseDTO.setExpiresAt(post.getExpiresAt());
		postResponseDTO.setCreatedAt(post.getCreatedAt());
		postResponseDTO.setUpdatedAt(post.getUpdatedAt());
		postResponseDTO.setViewCount(post.getViewCount());
		postResponseDTO.setLikeCount(post.getLikeCount());
		postResponseDTO.setLocationName(post.getLocationName());
		postResponseDTO.setLatitude(post.getLatitude());
		postResponseDTO.setLongitude(post.getLongitude());
		
		postResponseDTO.setUserId(post.getUser().getUserId());
		postResponseDTO.setUserName(post.getUser().getName());
		
		return postResponseDTO;
	}
	
	public List<PostResponseDTO> getAllPosts(){
		List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
		
		return posts.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public PostResponseDTO createPost(PostRequestDTO postRequestDTO, User user) {
		Post post = new Post();
		post.setUser(user);
		post.setCaption(postRequestDTO.getCaption());
		post.setMediaUrl(postRequestDTO.getMediaUrl());
		post.setMediaType(postRequestDTO.getMediaType());
		post.setIsStory(postRequestDTO.getIsStory());
		
		if (Boolean.TRUE.equals(postRequestDTO.getIsStory())) {
			post.setExpiresAt(LocalDateTime.now().plusHours(24));
		}
		
		post.setCreatedAt(LocalDateTime.now());
		post.setUpdatedAt(LocalDateTime.now());
		post.setViewCount(0);
		post.setLikeCount(0);
		post.setLocationName(postRequestDTO.getLocationName());
		post.setLatitude(postRequestDTO.getLatitude());
		post.setLongitude(postRequestDTO.getLongitude());
		
		Post savedPost = postRepository.save(post);
		
		return mapToResponse(savedPost);
	}
	
	public PostResponseDTO updatePost(Long postId, PostRequestDTO postRequestDTO, User user) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new EntityNotFoundException());
		
		if (!post.getUser().getUserId().equals(user.getUserId())) {
			throw new SecurityException();
		}
		
		post.setCaption(postRequestDTO.getCaption());
		post.setMediaUrl(postRequestDTO.getMediaUrl());
		post.setMediaType(postRequestDTO.getMediaType());
		post.setIsStory(postRequestDTO.getIsStory());
		
		post.setUpdatedAt(LocalDateTime.now());
		post.setLocationName(postRequestDTO.getLocationName());
		post.setLatitude(postRequestDTO.getLatitude());
		post.setLongitude(postRequestDTO.getLongitude());
		
		return mapToResponse(postRepository.save(post));
	}
	
	public void deletePost(Long postId, User user) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new EntityNotFoundException());
		
		if (!post.getUser().getUserId().equals(user.getUserId())) {
			throw new SecurityException();
		}
		
	}
	
	private PostResponseDTO mapToResponse(Post post) {
		PostResponseDTO postResponseDTO = new PostResponseDTO();
		postResponseDTO.setPostId(post.getPostId());
		postResponseDTO.setCaption(post.getCaption());
		postResponseDTO.setMediaUrl(post.getMediaUrl());
		postResponseDTO.setMediaType(post.getMediaType());
		postResponseDTO.setIsStory(post.getIsStory());
		postResponseDTO.setExpiresAt(post.getExpiresAt());
		postResponseDTO.setCreatedAt(post.getCreatedAt());
		postResponseDTO.setUpdatedAt(post.getUpdatedAt());
		postResponseDTO.setViewCount(post.getViewCount());
		postResponseDTO.setLikeCount(post.getLikeCount());
		postResponseDTO.setLocationName(post.getLocationName());
		postResponseDTO.setLatitude(post.getLatitude());
		postResponseDTO.setLongitude(post.getLongitude());
		postResponseDTO.setUserId(post.getUser().getUserId());
		postResponseDTO.setUserName(post.getUser().getName());
		
		return postResponseDTO;
	}

}
