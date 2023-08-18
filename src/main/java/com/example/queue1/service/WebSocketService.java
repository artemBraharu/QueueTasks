package com.example.queue1.service;

import com.example.queue1.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendToUser(String username, Task task) {
        simpMessagingTemplate.convertAndSend("/queue/tasks/"+username,task);
    }
}
