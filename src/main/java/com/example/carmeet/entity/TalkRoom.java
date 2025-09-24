package com.example.carmeet.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "talk_rooms")
@Data
public class TalkRoom {

	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "talk_room_id")
	    private Integer talkRoomId;

	    @Column(name = "name", nullable = false)
	    private String name;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "owner_user_id", nullable = false)
	    private User user;

	    @Column(name = "created_at", insertable = false, updatable = false)
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at", insertable = false, updatable = false)
	    private LocalDateTime updatedAt;

	    @Column(name = "deleted_at")
	    private LocalDateTime deletedAt;
}
