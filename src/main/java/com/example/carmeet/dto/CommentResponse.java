package com.example.carmeet.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {

	private Long commentId;
	private String content;
	private LocalDateTime createdAt;
	private Long userId;
	private String userName;
	private String profileImage;
}
