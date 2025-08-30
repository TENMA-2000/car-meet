package com.example.carmeet.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.carmeet.entity.Like;
import com.example.carmeet.entity.Post;
import com.example.carmeet.entity.User;
import com.example.carmeet.repository.LikeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

	private final LikeRepository likeRepository;
	
	@Transactional
	public Like addLike (Post post, User user) {
		log.debug("addLike呼び出し postId=" + post.getPostId() + ", userId=" + user.getUserId());
		if (likeRepository.existsByPostAndUser(post, user)) {
			return null;
		}
		
		Like like = new Like();
		like.setPost(post);
		like.setUser(user);
		
		Like savedLike = likeRepository.save(like);
		log.debug("いいね保存成功 likeId=" + savedLike.getLikeId() + ", postId=" + post.getPostId() + ", userId=" + user.getUserId());
		
		return savedLike;
	}

	
	@Transactional
	public boolean deleteLike (Post post, User user) {
		log.debug("deleteLike呼び出し postId=" + post.getPostId() + ", userId=" + user.getUserId());
		
		Optional<Like> likeOptional = likeRepository.findByPostAndUser(post, user);
		if (likeOptional.isPresent()) {
			likeRepository.delete(likeOptional.get());
			log.debug("いいね削除成功 postId=" + post.getPostId() + ", userId=" + user.getUserId());
			return true;
		}
		
		return false;
	}
	
	public long countLikes (Post post) {
		long count = likeRepository.countByPost(post);
		log.debug("countLikes呼び出し postId=" + post.getPostId() + ", 件数=" + count);
		return count;
	}
	
	public boolean checkUserLiked(Post post, User user) {
		boolean exists = likeRepository.existsByPostAndUser(post, user);
		log.debug("checkUserLiked呼び出し postId=" + post.getPostId() + ", userId=" + user.getUserId() + ", 既にいいねしているか=" + exists);
		return exists;
	}
}
