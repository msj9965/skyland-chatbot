package com.skyland.skylandchatbot.service;

import org.springframework.ai.document.Document;

import java.util.List;

/**
 * 벡터 저장소 접근 인터페이스
 */
public interface VectorStorageService {
    /**
     * 문서들을 벡터 저장소에 저장합니다.
     * @param documents 저장할 문서 리스트
     */
    void save(List<Document> documents);

    /**
     * 문서 ID로 벡터 저장소에서 삭제합니다.
     * @param documentId 삭제할 문서 ID
     */
    void delete(String documentId);
}

