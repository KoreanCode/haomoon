package com.haomoon.chat.handler;

import com.haomoon.chat.dto.ChatRoomDTO;
import com.haomoon.chat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, List<String>> chatMessages = new ConcurrentHashMap<>();  // 채팅방 메시지 저장

    @Autowired
    private ChatRoomRepository chatRoomRepository;  // DB에 저장할 Repository

    // 클라이언트와 연결될 때 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        chatMessages.putIfAbsent(roomId, new ArrayList<>());  // 새로운 채팅방 초기화
    }

    // 메시지 수신 시 호출
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");  // 채팅방 ID
        String clientMessage = message.getPayload();  // 클라이언트로부터 받은 메시지

        // 채팅방에 메시지 추가
        chatMessages.get(roomId).add(clientMessage);

        // 클라이언트에게 메시지 전송 (Echo)
        session.sendMessage(new TextMessage("Echo: " + clientMessage));
    }

    // 웹소켓 연결이 종료될 때 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");  // 채팅방 ID
        List<String> messages = chatMessages.remove(roomId);  // 채팅방 메시지 가져오기

        if (messages != null && !messages.isEmpty()) {
            saveMessagesToFile(messages, roomId);  // 파일로 저장
        }
    }

    // 채팅방 종료 시, 메시지를 파일로 저장 후 DB에 기록
    private void saveMessagesToFile(List<String> messages, String roomId) {
        String filePath = "/path/to/chat/files/" + roomId + ".txt";  // 파일 경로

        // 파일에 메시지 저장
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String message : messages) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // DB에 채팅방 정보 저장
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setRoundId(Long.valueOf(roomId));  // 채팅방 ID
        chatRoomDTO.setMessageFilePath(filePath);  // 파일 경로
        chatRoomDTO.setIsArchived(true);  // 아카이브 상태
        chatRoomDTO.setArchivedAt(Timestamp.valueOf(LocalDateTime.now()));  // 현재 시간

        chatRoomRepository.saveChatRoom(chatRoomDTO);  // DB에 저장
    }



}
