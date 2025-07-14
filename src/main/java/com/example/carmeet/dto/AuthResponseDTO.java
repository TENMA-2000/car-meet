package com.example.carmeet.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

	private String message;
	private String token;
	private Long userId;
	private String userName;
	
	public AuthResponseDTO(String message, String token, Long userId, String userName) {
		this.message = message;
		this.token = token;
		this.userId = userId;
		this.userName = userName;
	}
}
