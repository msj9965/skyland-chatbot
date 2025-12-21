package com.skyland.skylandchatbot.service;

import org.springframework.ai.chat.prompt.Prompt;

/**
 * LLM 응답 생성 서비스 인터페이스
 */
public interface LlmResponseService {
    /**
     * LLM에 프롬프트를 전송하고 응답을 받습니다.
     *
     * @param prompt 생성된 프롬프트
     * @return LLM 응답 텍스트
     */
    String generateResponse(Prompt prompt);
}

