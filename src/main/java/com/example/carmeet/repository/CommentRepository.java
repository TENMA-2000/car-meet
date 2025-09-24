package com.example.carmeet.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmeet.entity.Comment;
import com.example.carmeet.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByPost(Post post, Sort sort);
	
	List<Comment> findByPostOrderByCreatedAtDesc(Post post);
	
	List<Comment> findByPostOrderByCreatedAtAsc(Post post);
	
	List<Comment> findByPost_PostIdAndParentCommentIdIsNull(Long postId, Sort sort);
	
	List<Comment> findByParentCommentId(Comment parentCommentId, Sort sort);
}
