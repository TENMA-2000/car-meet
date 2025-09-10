package com.example.carmeet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {

	private boolean success;
	private Long commnetId;
}
