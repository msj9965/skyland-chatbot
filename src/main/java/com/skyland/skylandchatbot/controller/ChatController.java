package com.skyland.skylandchatbot.controller;


import com.skyland.skylandchatbot.dto.ChatRequest;
import com.skyland.skylandchatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 채팅 API (POST 방식)
     * [POST] /api/v1/chat
     * Body: { "message": "질문 내용" }
     */
    @PostMapping // Get -> Post 변경
    public ResponseEntity<Map<String, String>> chat(@RequestBody ChatRequest request) {
        // request.message()로 데이터 꺼내기
        String answer = chatService.chat(request.message());
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}