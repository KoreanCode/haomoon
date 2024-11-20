package com.haomoon.chat.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ChatRoomService {

    public void saveMessageToFile(String message) {
        String filePath = "path/to/chat/messages/chat_" + System.currentTimeMillis() + ".txt"; // 유니크한 파일 이름 생성

        try (FileWriter writer = new FileWriter(new File(filePath), true)) {
            writer.write(message + "\n");
            System.out.println("Message saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
