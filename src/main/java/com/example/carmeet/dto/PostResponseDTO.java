package com.example.carmeet.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostResponseDTO {

	private Long postId;
	private String caption;
	private String mediaUrl;
	private String mediaType;
	private Boolean isStory;
	private LocalDateTime expiresAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer viewCount;
	private Integer likeCount;
	private String locationName;
	private Double latitude;
	private Double longitude;
	private String profileImage;
	
	private Long userId;
	private String userName;
	
}
