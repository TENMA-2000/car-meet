package com.example.carmeet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmeet.entity.Post;
import com.example.carmeet.entity.User;
import com.example.carmeet.service.LikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

	private final LikeService likeService;
	
	@PostMapping("/{postId}/{userId}")
	public ResponseEntity<Boolean> addLike(@PathVariable Long postId, @PathVariable Long userId) {
		log.debug("addLikeリクエスト受信 postId=" + postId + ", userId=" + userId);
		
		Post post = new Post();
		post.setPostId(postId);
		User user = new User();
		user.setUserId(userId);
		
		boolean addSuccess = likeService.addLike(post, user) != null;
		log.debug("addLike処理結果=" + addSuccess);
		
		return ResponseEntity.ok(addSuccess);
	}
	
	@GetMapping("/count/{postId}")
	public ResponseEntity<Long> countLikes(@PathVariable Long postId) {
		log.debug("countLikesリクエスト受信 postId=" + postId);
		
		Post post = new Post();
		post.setPostId(postId);
		long count = likeService.countLikes(post);
		
		log.debug("countLikes結果=" + count);
		return ResponseEntity.ok(count);
	}
	
	@DeleteMapping("/{postId}/{userId}")
	public ResponseEntity<Boolean> deleteLike(@PathVariable Long postId, @PathVariable Long userId){
		log.debug("deleteLikeリクエスト受信 postId=" + postId + ", userId=" + userId);
		
		Post post = new Post();
		post.setPostId(postId);
		User user = new User();
		user.setUserId(userId);
		
		boolean deleteSuccess = likeService.deleteLike(post, user);
		log.debug("deleteLike処理結果=" + deleteSuccess);
		
		return ResponseEntity.ok(deleteSuccess);
	}
}
