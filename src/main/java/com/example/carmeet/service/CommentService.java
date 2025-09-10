package com.example.carmeet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.carmeet.entity.Comment;
import com.example.carmeet.entity.Post;
import com.example.carmeet.entity.User;
import com.example.carmeet.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {

	private final CommentRepository commentRepository;
	
	public List<Comment> getRootCommentsPost(Post post, Sort sort){
		log.info("投稿ID={} のルートコメント一覧を所得します。 並び順={}", post.getPostId(), sort);
		return commentRepository.findByPostAndParentCommentIdIsNull(post, sort);
	}
	
	public List<Comment> getRepliesByParent(Comment parentCommnetId, Sort sort){
		log.debug("親コメントID={} の返信一覧を取得します。 並び順={}", parentCommnetId.getCommentId());
		return commentRepository.findByParentCommentId(parentCommnetId, sort);
	}
	
	public Comment addComment(Post post, User user, String content, Comment parentCommentId) {
		log.info("新規コメントを追加します。投稿ID={}, ユーザーID={}, 親コメントID={}",
				post.getPostId(), user.getUserId(),
				parentCommentId != null ? parentCommentId.getParentCommentId() : "なし");
		
		Comment comment = new Comment();
		comment.setPost(post);
		comment.setUser(user);
		comment.setContent(content);
		comment.setParentCommentId(parentCommentId);
		comment.setCreatedAt(LocalDateTime.now());
		comment.setUpdatedAt(LocalDateTime.now());
		Comment saved = commentRepository.save(comment);
		log.info("コメントを保存しました。コメントID={}", saved.getCommentId());
		return saved;
	}
	
	public void softDeleteComment(Comment comment) {
		log.warn("コメントID={} をソフトデリートします。", comment.getCommentId());
		comment.setDeletedAt(LocalDateTime.now());
		commentRepository.save(comment);
		log.info("コメントID={} をソフトデリート済みに更新しました。", comment.getCommentId());
	}
}
