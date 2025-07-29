package com.example.carmeet.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

	private String message;
	private String token;
	private Long userId;
	private String userName;
	
	public AuthResponseDTO(String token, Long userId) {
		this.token = token;
		this.userId = userId;
	}
}
