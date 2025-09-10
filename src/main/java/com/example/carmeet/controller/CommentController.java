package com.example.carmeet.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmeet.dto.CommentResponse;
import com.example.carmeet.entity.Comment;
import com.example.carmeet.entity.Post;
import com.example.carmeet.entity.User;
import com.example.carmeet.service.CommentService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	
	@GetMapping("/post/{postId}")
	public List<Comment> getRootComments(@PathVariable Long postId, @RequestParam(defaultValue = "desc") String order) {
		Post post = new Post();
		post.setPostId(postId);
		
		Sort sort = order.equalsIgnoreCase("asc") ?
				Sort.by("createdAt").ascending() :
				Sort.by("createdAt").descending();
		
		return commentService.getRootCommentsPost(post, sort);
	}
	
	@GetMapping("/{commentId}/reqlies")
	public List<Comment> getReqlies(@PathVariable Long commentId, @RequestParam(defaultValue = "asc") String order) {
		Comment parentCommentId = new Comment();
		parentCommentId.setCommentId(commentId);
		
		Sort sort = order.equalsIgnoreCase("desc") ?
				Sort.by("createdAt").descending() :
				Sort.by("createdAt").ascending();
		
		return commentService.getRepliesByParent(parentCommentId, sort);
	}
	
	@PostMapping("/post/{postId}")
	public Comment addComment(@PathVariable Long postId,
							  @RequestParam Long userId,
							  @RequestParam String content,
							  @RequestParam(required = false) Long parentCommentId) {
		
		Post post = new Post();
		post.setPostId(postId);
		
		User user = new User();
		user.setUserId(userId);
		
		Comment parentComment = null;
		if (parentCommentId != null) {
			parentComment = new Comment();
			parentComment.setCommentId(parentCommentId);
		}
		
		return commentService.addComment(post, user, content, parentComment);
	}
	
	@DeleteMapping("/{commentId}")
	public CommentResponse deleteComment(@PathVariable Long commentId) {
		Comment comment = new Comment();
		comment.setCommentId(commentId);
		commentService.softDeleteComment(comment);
		return new CommentResponse(true, commentId);
	}
	
}
