package com.example.carmeet.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.carmeet.entity.TalkRoomMessage;

@Repository
public interface TalkRoomMessageRepository extends JpaRepository<TalkRoomMessage, Integer> {

	// 特定のトークルームのメッセージ一覧を古い順に取得する
	List<TalkRoomMessage> findByTalkRoom_TalkRoomIdOrderBySentAtAsc(Integer talkRoomId);

	// 特定のトークルームのメッセージ一覧を新しい順に取得する
	List<TalkRoomMessage> findByTalkRoom_TalkRoomIdOrderBySentAtDesc(Integer talkRoomId);

	// 親メッセージに紐づく返信だけを取得
	List<TalkRoomMessage> findByParentMessage_MessageIdOrderBySentAtAsc(Integer parentMessageId);

	// 最新メッセージを指定件数だけ取得（降順＋Pageable）
	Page<TalkRoomMessage> findByTalkRoom_TalkRoomIdOrderBySentAtDesc(Integer talkRoomId, Pageable pageable);
	
	// 最新メッセージを指定件数だけ取得（昇順＋Pageable）
	Page<TalkRoomMessage> findByTalkRoom_TalkRoomIdOrderBySentAtAsc(Integer talkRoomId, Pageable pageable);

	// 指定日時より前のメッセージを取得（過去ログ読み込み用）
	Page<TalkRoomMessage> findByTalkRoom_TalkRoomIdAndSentAtBeforeOrderBySentAtDesc(Integer talkRoomId, LocalDateTime before, Pageable pageable);

	// 指定日時より後のメッセージを取得（新着確認用）
	List<TalkRoomMessage> findByTalkRoom_TalkRoomIdAndSentAtAfterOrderBySentAtAsc(Integer talkRoomId, LocalDateTime after);
}
