package com.example.carmeet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.carmeet.entity.TalkRoom;
import com.example.carmeet.entity.TalkRoomMessage;
import com.example.carmeet.repository.TalkRoomMessageRepository;
import com.example.carmeet.repository.TalkRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TalkRoomMessageService {

    private final TalkRoomMessageRepository messageRepository;
    private final TalkRoomRepository talkRoomRepository;

    //特定トークルームのメッセージ一覧を古い順（昇順）で取得
    public List<TalkRoomMessage> getMessagesByTalkRoomIdAsc(Integer talkRoomId) {
        return messageRepository.findByTalkRoom_TalkRoomIdOrderBySentAtAsc(talkRoomId);
    }

    //特定トークルームのメッセージ一覧を新しい順（降順）で取得
    public List<TalkRoomMessage> getMessagesByTalkRoomIdDesc(Integer talkRoomId) {
        return messageRepository.findByTalkRoom_TalkRoomIdOrderBySentAtDesc(talkRoomId);
    }
    
    //最新メッセージを指定件数だけ取得（降順)
    public List<TalkRoomMessage> getLatestMessages(Integer talkRoomId, int limit) {
        return messageRepository
                .findByTalkRoom_TalkRoomIdOrderBySentAtDesc(talkRoomId, PageRequest.of(0, limit))
                .getContent();
    }

    //古い順で指定件数だけ取得（昇順)
    public List<TalkRoomMessage> getOldestMessages(Integer talkRoomId, int limit) {
        return messageRepository
                .findByTalkRoom_TalkRoomIdOrderBySentAtAsc(talkRoomId, PageRequest.of(0, limit))
                .getContent();
    }

    //過去のメッセージの追加取得
    public List<TalkRoomMessage> getOlderMessages(Integer talkRoomId, LocalDateTime before, int limit) {
        return messageRepository
                .findByTalkRoom_TalkRoomIdAndSentAtBeforeOrderBySentAtDesc(
                        talkRoomId, before, PageRequest.of(0, limit))
                .getContent();
    }

    //新着メッセージの追加取得
    public List<TalkRoomMessage> getNewMessages(Integer talkRoomId, LocalDateTime after) {
        return messageRepository.findByTalkRoom_TalkRoomIdAndSentAtAfterOrderBySentAtAsc(talkRoomId, after);
    }

    //メッセージ投稿 
    public TalkRoomMessage createMessage(Integer talkRoomId, TalkRoomMessage message) {
    	TalkRoom room = talkRoomRepository.findById(talkRoomId)
                .orElseThrow(() -> new IllegalArgumentException());
        message.setTalkRoom(room);
        message.setSentAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    //メッセージ編集
    public Optional<TalkRoomMessage> updateMessageContent(Integer messageId, String newContent) {
        return messageRepository.findById(messageId).map(message -> {
        	message.setContent(newContent);
            return messageRepository.save(message);
        });
    }

    //メッセージ削除
    public Optional<TalkRoomMessage> deleteMessage(Integer messageId) {
        return messageRepository.findById(messageId).map(message -> {
        	message.setIsDeletedBySender(true);
            return messageRepository.save(message);
        });
    }
}