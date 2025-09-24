package com.example.carmeet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmeet.dto.TalkRoomResponse;
import com.example.carmeet.entity.TalkRoom;
import com.example.carmeet.service.TalkRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/talk-rooms")
public class TalkRoomController {

	private final TalkRoomService talkRoomService;

    //トークルーム一覧取得
    @GetMapping
    public List<TalkRoomResponse> getAllTalkRooms() {
        return talkRoomService.getAllTalkRooms();
    }

    //特定トークルーム取得
    @GetMapping("/{talkRoomId}")
    public ResponseEntity<TalkRoom> getTalkRoomById(@PathVariable Integer talkRoomId) {
        return talkRoomService.findByTalkRoomId(talkRoomId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
