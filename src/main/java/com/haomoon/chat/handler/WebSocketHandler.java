package com.haomoon.chat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 메시지 수신
        String clientMessage = message.getPayload();
        System.out.println("Received message: " + clientMessage);

        // 클라이언트에게 메시지 전송
        session.sendMessage(new TextMessage("Echo: " + clientMessage));
    }

}
