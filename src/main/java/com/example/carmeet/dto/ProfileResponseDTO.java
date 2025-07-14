package com.example.carmeet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponseDTO {

	private Long userId;
	private String name;
	private String email;
}
