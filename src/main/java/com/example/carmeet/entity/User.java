package com.example.carmeet.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "profile_image")
	private String profileImage;
	
	@Column(name = "car_life_year")
	private Integer carLifeYear;
	
	@Column(name = "gender")
	private Integer gender;
	
	@Column(name = "introduction")
	private String introduction;
	
	@Column(name = "hobbies")
	private String hobbies;
	
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	@Column(name = "enabled", nullable = false)
	private Boolean enabled;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
	
}
