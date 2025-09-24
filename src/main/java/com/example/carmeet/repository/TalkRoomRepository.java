package com.example.carmeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.carmeet.entity.TalkRoom;

@Repository
public interface TalkRoomRepository extends JpaRepository<TalkRoom, Integer> {
	@Query("SELECT tr FROM TalkRoom tr JOIN FETCH tr.user")
    List<TalkRoom> findAllWithUser();
}
