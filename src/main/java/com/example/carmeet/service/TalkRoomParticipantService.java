package com.example.carmeet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.carmeet.entity.TalkRoomParticipant;
import com.example.carmeet.repository.TalkRoomParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TalkRoomParticipantService {

    private final TalkRoomParticipantRepository participantRepository;

    //特定トークルームの参加者一覧を取得
    public List<TalkRoomParticipant> getParticipantsByTalkRoomId(Integer talkRoomId) {
        return participantRepository.findByTalkRoom_TalkRoomIdOrderByJoinedAtAsc(talkRoomId);
    }
}
