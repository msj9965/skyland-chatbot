package com.skyland.skylandchatbot.service;

import com.skyland.skylandchatbot.service.impl.SkylandPromptBuilderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RAG 기반 채팅 서비스

 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final DocumentRetrievalService documentRetrievalService;
    private final PromptBuilderService promptBuilderService;
    private final LlmResponseService llmResponseService;

    private static final int TOP_K_DOCUMENTS = 3;

    /**
     * RAG 기반 채팅 응답 생성
     * 1. 문서 검색 (Retrieval)
     * 2. 프롬프트 생성 (Augmented)
     * 3. LLM 응답 생성 (Generation)
     */
    public String chat(String userMessage) {
        log.info(">>> [RAG] 채팅 요청 수신: {}", userMessage);

        // 1. [Retrieval] 사용자 질문과 유사한 문서 검색
        List<Document> similarDocuments = documentRetrievalService.searchSimilarDocuments(
                userMessage,
                TOP_K_DOCUMENTS
        );

        // 검색된 내용이 없으면 기본 답변 처리
        if (similarDocuments.isEmpty()) {
            log.info(">>> [RAG] 검색 결과 없음. 전화번호 안내로 대체.");
            return ((SkylandPromptBuilderService) promptBuilderService).getContactInfoMessage();
        }

        // 2. [Augmented] 검색된 문서를 컨텍스트로 변환
        String context = promptBuilderService.buildContextFromDocuments(similarDocuments);

        // 3. [Prompt] 시스템 프롬프트 구성
        String systemText = promptBuilderService.buildSystemPrompt(context);

        // 4. [Generation] LLM 호출
        Message systemMessage = new SystemMessage(systemText);
        Message userMsg = new UserMessage(userMessage);
        Prompt prompt = new Prompt(List.of(systemMessage, userMsg));

        String response = llmResponseService.generateResponse(prompt);

        log.info(">>> [RAG] 채팅 응답 생성 완료");
        return response;
    }
}