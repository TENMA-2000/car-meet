package com.example.carmeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmeet.entity.Like;
import com.example.carmeet.entity.Post;



public interface LikeRepository extends JpaRepository<Like, Long>{

	List<Like> findByPost(Post post);
}
