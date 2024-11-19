package com.haomoon.chat.repository;


import com.haomoon.chat.dto.ChatRoomDTO;
import java.util.List;

public interface ChatRoomRepository {


    void saveChatRoom(ChatRoomDTO chatRoomDTO);  // SQL 쿼리는 XML에서 처리

    List<ChatRoomDTO> getChatRoomsByRoundId(Long roundId);  // SQL 쿼리는 XML에서 처리
}
