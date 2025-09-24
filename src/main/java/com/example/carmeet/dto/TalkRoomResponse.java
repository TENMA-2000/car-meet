package com.example.carmeet.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalkRoomResponse {

	private Integer talkRoomId;
	private String name;
	private LocalDateTime createdAt;
}
