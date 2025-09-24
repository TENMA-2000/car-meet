package com.example.carmeet.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.carmeet.dto.CommentResponse;
import com.example.carmeet.dto.PostRequestDTO;
import com.example.carmeet.dto.PostResponseDTO;
import com.example.carmeet.entity.Comment;
import com.example.carmeet.entity.Post;
import com.example.carmeet.entity.User;
import com.example.carmeet.security.UserDetailsImpl;
import com.example.carmeet.service.CommentService;
import com.example.carmeet.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final CommentService commentService;

	@GetMapping("/{postId}")
	public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		
		log.debug("【投稿取得】postId={} の投稿取得APIが呼ばれました（ユーザーID={}）", postId, user.getUserId());
		
		PostResponseDTO postResponseDTO = postService.getPost(postId, user);
		
		log.debug("【投稿取得成功】postId={} の投稿データを返却しました", postId);
		
		return ResponseEntity.ok(postResponseDTO);
	}

	@GetMapping
	public ResponseEntity<List<PostResponseDTO>> getLimitedPosts(
			@RequestParam(defaultValue = "10") int limit) {
		return ResponseEntity.ok(postService.getLimitedPosts(limit));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostResponseDTO> createPost(
			@RequestPart("post") @Valid PostRequestDTO postRequestDTO,
			@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

		User user = userDetailsImpl.getUser();
		
		log.debug("【投稿作成】ユーザーID={} が新規投稿を作成します。内容={}", user.getUserId(), postRequestDTO);
		
		PostResponseDTO postResponseDTO = postService.createPost(postRequestDTO, multipartFile, user);
		
		log.debug("【投稿作成成功】postId={} の投稿を作成しました", postResponseDTO.getPostId());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postId,
			@Valid @RequestBody PostRequestDTO postRequestDTO,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

		User user = userDetailsImpl.getUser();
		
		log.debug("【投稿更新】postId={} の投稿をユーザーID={} が更新します", postId, user.getUserId());
		
		PostResponseDTO postResponseDTO = postService.updatePost(postId, postRequestDTO, user);
		
		log.debug("【投稿更新成功】postId={} の投稿を更新しました", postId);

		return ResponseEntity.ok(postResponseDTO);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		
		log.debug("【投稿削除】postId={} の投稿をユーザーID={} が削除します", postId, user.getUserId());
		
		postService.deletePost(postId, user);
		
		log.debug("【投稿削除成功】postId={} の投稿を削除しました", postId);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/sorted")
	public ResponseEntity<Page<PostResponseDTO>> getSortedPosts(@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "desc") String direction,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		return ResponseEntity.ok(postService.getSortedPosts(sortBy, direction, page, size));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<PostResponseDTO>> searchPosts(
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(postService.searchPosts(keyword, page, size));
	}
	
	@GetMapping("/{postId}/comments")
	public List<CommentResponse> getRootComments(@PathVariable Long postId, @RequestParam(defaultValue = "desc") String order) {
		
		log.debug("★★ コメント一覧APIが呼ばれました！ postId={}, order={}", postId, order);
		
		Sort sort = order.equalsIgnoreCase("asc") ?
				Sort.by("createdAt").ascending() :
				Sort.by("createdAt").descending();
		
		return commentService.getRootCommentsPost(postId, sort);
	}
	
	@GetMapping("/{postId}/comments/{commentId}/replies")
	public List<CommentResponse> getReplies(@PathVariable Long postId, @PathVariable Long commentId, @RequestParam(defaultValue = "asc") String order) {
		
		log.debug("★★ 返信一覧APIが呼ばれました！ postId={}, commentId={}, order={}", postId, commentId, order);
		
		Comment parentCommentId = new Comment();
		parentCommentId.setCommentId(commentId);
		
		Sort sort = order.equalsIgnoreCase("desc") ?
				Sort.by("createdAt").descending() :
				Sort.by("createdAt").ascending();
		
		return commentService.getRepliesByParent(parentCommentId, sort);
	}
	
	@PostMapping("/{postId}/comments")
	public Comment addComment(@PathVariable Long postId,
							  @RequestParam Long userId,
							  @RequestParam String content,
							  @RequestParam(required = false) Long parentCommentId) {
		
		log.debug("【コメント追加】postId={} にコメントを追加します。ユーザーID={} 内容={}", postId, userId, content);
		
		Post post = new Post();
		post.setPostId(postId);
		
		User user = new User();
		user.setUserId(userId);
		
		Comment parentComment = null;
		if (parentCommentId != null) {
			parentComment = new Comment();
			parentComment.setCommentId(parentCommentId);
		}
		
		Comment result = commentService.addComment(post, user, content, parentComment);
		
		log.debug("【コメント追加成功】commentId={} のコメントを追加しました", result.getCommentId());
		
		return result;
	}
	
	@DeleteMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		Comment comment = new Comment();
		comment.setCommentId(commentId);
		commentService.softDeleteComment(comment);
		return ResponseEntity.noContent().build();
	}

}
