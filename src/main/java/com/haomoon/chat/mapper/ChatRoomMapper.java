package com.haomoon.chat.mapper;


import com.haomoon.chat.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChatRoomMapper {

    @Insert("INSERT INTO chat_room_tb (round_id, message_file_path, is_archived, archived_at) " +
            "VALUES (#{roundId}, #{messageFilePath}, #{isArchived}, #{archivedAt})")
    void saveChatRoom(ChatRoomDTO chatRoomDTO);

    @Select("SELECT * FROM chat_room_tb WHERE round_id = #{roundId}")
    List<ChatRoomDTO> getChatRoomsByRoundId(Long roundId);
}
