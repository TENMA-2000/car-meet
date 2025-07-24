package com.example.carmeet.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "caption", columnDefinition = "TEXT")
	private String caption;
	
	@Column(name = "media_url")
	private String mediaUrl;
	
	@Column(name = "media_type")
	private String mediaType;
	
	@Column(name = "is_story")
	private Boolean isStory;
	
	@Column(name = "expires_at")
	private LocalDateTime expiresAt;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
	
	@Column(name = "view_count")
	private Integer viewCount;
	
	@Column(name = "like_count")
	private Integer likeCount;
	
	@Column(name = "location_name")
	private String locationName;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
}
