package com.example.carmeet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmeet.entity.TalkRoomMessage;
import com.example.carmeet.service.TalkRoomMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/talk-rooms/{talkRoomId}/messages")
public class TalkRoomMessageController {

	private final TalkRoomMessageService messageService;

	//特定トークルームのメッセージ一覧（古い順）
	@GetMapping
	public ResponseEntity<List<TalkRoomMessage>> getMessagesAsc(@PathVariable Integer talkRoomId) {
		List<TalkRoomMessage> messages = messageService.getMessagesByTalkRoomIdAsc(talkRoomId);
		return ResponseEntity.ok(messages);
	}

	//特定トークルームのメッセージ一覧（新しい順）
	@GetMapping("/latest")
	public ResponseEntity<List<TalkRoomMessage>> getMessagesDesc(@PathVariable Integer talkRoomId) {
		List<TalkRoomMessage> messages = messageService.getMessagesByTalkRoomIdDesc(talkRoomId);
		return ResponseEntity.ok(messages);
	}

	//メッセージ投稿
	@PostMapping
	public ResponseEntity<TalkRoomMessage> createMessage(
	        @PathVariable Integer talkRoomId,
	        @RequestBody TalkRoomMessage newMessage) {

	    try {
	        TalkRoomMessage savedMessage = messageService.createMessage(talkRoomId, newMessage);
	        return ResponseEntity.ok(savedMessage);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	//メッセージ編集
	@PatchMapping("/{messageId}")
	public ResponseEntity<TalkRoomMessage> updateMessage(
			@PathVariable Integer talkRoomId,
			@PathVariable Integer messageId,
			@RequestBody String newContent) {

		return messageService.updateMessageContent(messageId, newContent)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	//メッセージ削除
	@DeleteMapping("/{messageId}")
	public ResponseEntity<TalkRoomMessage> deleteMessage(
			@PathVariable Integer talkRoomId,
			@PathVariable Integer messageId) {

		return messageService.deleteMessage(messageId)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
