package com.haomoon.chat.entity;

import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room_tb")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long chatRoomId;          // 채팅방 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;              // 라운드 정보 (외래 키)

    @Column(name = "message_file_path")
    private String messageFilePath;   // 메시지 파일 경로

    @Column(name = "is_archived")
    private boolean isArchived = false;  // 아카이브 여부

    @Column(name = "archived_at")
    private LocalDateTime archivedAt;    // 아카이브 완료 시점


}
