package com.haomoon.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoomDTO {

    private Long chatRoomId;        // 채팅방 고유 ID
    private Long roundId;           // 라운드 ID
    private String messageFilePath; // 메시지 파일 경로
    private boolean isArchived;     // 아카이브 여부
    private LocalDateTime archivedAt; // 아카이브 완료 시점

}
