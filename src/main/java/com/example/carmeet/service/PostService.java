package com.example.carmeet.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.carmeet.constant.ErrorMessages;
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

	public List<PostResponseDTO> getAllPosts() {
		List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

		return posts.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public PostResponseDTO createPost(PostRequestDTO postRequestDTO, MultipartFile multipartFile, User user) {
		Post post = new Post();
		post.setUser(user);
		post.setCaption(postRequestDTO.getCaption());

		if (multipartFile != null && !multipartFile.isEmpty()) {
			Path uploadPath = Paths.get("upload-dir");

			try {
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
				Files.copy(multipartFile.getInputStream(), uploadPath.resolve(fileName));

				post.setMediaUrl("/uploads/" + fileName);

			} catch (IOException e) {
				throw new RuntimeException(ErrorMessages.FILE_SAVE_ERROR, e);
			}

		}

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
	
	public Page<PostResponseDTO> getSortedPosts(String sortBy, String direction, int page, int size){
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		
		return postRepository.findAll(pageable).map(this::mapToResponse);
	}
	
	public Page<PostResponseDTO> searchPosts(String keyword, int page, int size){
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		
		if (keyword == null || keyword.trim().isEmpty()) {
			return postRepository.findAll(pageable).map(this::mapToResponse);
		}
		
		return postRepository.findByCaptionContainingIgnoreCase(keyword, pageable)
				.map(this::mapToResponse);
	}

}
