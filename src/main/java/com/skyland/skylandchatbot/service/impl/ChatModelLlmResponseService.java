package com.skyland.skylandchatbot.service.impl;

import com.skyland.skylandchatbot.service.LlmResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

/**
 * ChatModel 기반 LLM 응답 생성 서비스

 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatModelLlmResponseService implements LlmResponseService {

    private final ChatModel chatModel;

    @Override
    public String generateResponse(Prompt prompt) {
        log.info(">>> [RAG] LLM 호출 시작");

        ChatResponse response = chatModel.call(prompt);
        String responseText = response.getResult().getOutput().getText();

        log.info(">>> [RAG] LLM 응답 생성 완료");

        return responseText;
    }
}

