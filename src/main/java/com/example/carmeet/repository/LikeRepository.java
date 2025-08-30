package com.example.carmeet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmeet.entity.Like;
import com.example.carmeet.entity.Post;
import com.example.carmeet.entity.User;

public interface LikeRepository extends JpaRepository<Like, Long>{

	List<Like> findByPost(Post post);
	boolean existsByPostAndUser (Post post, User user);
	Optional<Like> findByPostAndUser (Post post, User user);
	long countByPost (Post post);
	
}