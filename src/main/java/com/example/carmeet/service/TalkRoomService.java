package com.example.carmeet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.carmeet.dto.TalkRoomResponse;
import com.example.carmeet.entity.TalkRoom;
import com.example.carmeet.repository.TalkRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TalkRoomService {

    private final TalkRoomRepository talkRoomRepository;

    //トークルーム一覧を取得
    public List<TalkRoomResponse> getAllTalkRooms() {
        List<TalkRoom> rooms = talkRoomRepository.findAll();
        
        List<TalkRoomResponse> responses = new ArrayList<>();
        for (TalkRoom room : rooms) {
        	TalkRoomResponse talkRoomResponse = new TalkRoomResponse();
        	talkRoomResponse.setTalkRoomId(room.getTalkRoomId());
        	talkRoomResponse.setName(room.getName());
        	
        	responses.add(talkRoomResponse);
        }
        return responses;
    }

    //ID指定でトークルームを取得
    public Optional<TalkRoom> findByTalkRoomId(Integer talkRoomId) {
        return talkRoomRepository.findById(talkRoomId);
    }
}