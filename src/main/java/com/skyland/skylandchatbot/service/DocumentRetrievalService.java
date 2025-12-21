package com.skyland.skylandchatbot.service;

import org.springframework.ai.document.Document;

import java.util.List;

/**
 * 문서 검색 서비스 인터페이스
 */
public interface DocumentRetrievalService {
    /**
     * 사용자 질문과 유사한 문서를 검색합니다.
     *
     * @param query 검색 질의
     * @param topK 상위 K개 결과
     * @return 유사한 문서 리스트
     */
    List<Document> searchSimilarDocuments(String query, int topK);
}

