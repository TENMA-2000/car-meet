package com.example.carmeet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmeet.entity.TalkRoomParticipant;
import com.example.carmeet.service.TalkRoomParticipantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/talk-rooms/{talkRoomId}/participants")
public class TalkRoomParticipantController {

	private final TalkRoomParticipantService participantService;

    //特定トークルームの参加者一覧取得
    @GetMapping
    public ResponseEntity<List<TalkRoomParticipant>> getParticipants(@PathVariable Integer talkRoomId) {
        List<TalkRoomParticipant> participants = participantService.getParticipantsByTalkRoomId(talkRoomId);
        return ResponseEntity.ok(participants);
    }
}
