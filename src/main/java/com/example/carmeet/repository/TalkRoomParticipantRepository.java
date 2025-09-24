package com.example.carmeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.carmeet.entity.TalkRoomParticipant;

@Repository
public interface TalkRoomParticipantRepository extends JpaRepository<TalkRoomParticipant, Integer> {

    // 特定のトークルームに参加してるユーザー一覧を取得
    List<TalkRoomParticipant> findByTalkRoom_TalkRoomIdOrderByJoinedAtAsc(Integer talkRoomId);
}
