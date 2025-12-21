package com.skyland.skylandchatbot.service;

import org.springframework.ai.document.Document;

import java.util.List;

/**
 * 프롬프트 생성 서비스 인터페이스
 */
public interface PromptBuilderService {
    /**
     * 시스템 프롬프트를 생성합니다.
     *
     * @param context 검색된 문서들의 컨텍스트
     * @return 시스템 프롬프트 텍스트
     */
    String buildSystemPrompt(String context);

    /**
     * 문서 리스트를 컨텍스트 문자열로 변환합니다.
     *
     * @param documents 문서 리스트
     * @return 컨텍스트 문자열
     */
    String buildContextFromDocuments(List<Document> documents);
}

