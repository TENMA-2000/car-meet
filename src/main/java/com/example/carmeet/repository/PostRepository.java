package com.example.carmeet.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmeet.entity.Post;
import com.example.carmeet.entity.User;

public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findByUser(User user);
	
	List<Post> findByIsStoryTrue();
	
	List<Post> findByIsStoryFalse();
	
	List<Post> findByDeletedAtIsNull();
	
	List<Post> findAllByOrderByCreatedAtDesc();
	
	Page<Post> findAll(Pageable pageable);
}
