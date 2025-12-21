package com.skyland.skylandchatbot.service.impl;

import com.skyland.skylandchatbot.service.DocumentRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 벡터 저장소 기반 문서 검색 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VectorStoreDocumentRetrievalService implements DocumentRetrievalService {

    private final VectorStore vectorStore;

    @Override
    public List<Document> searchSimilarDocuments(String query, int topK) {
        log.info(">>> [RAG] 문서 검색 시작: query={}, topK={}", query, topK);

        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build()
        );

        log.info(">>> [RAG] 검색된 문서 개수: {}", similarDocuments.size());

        return similarDocuments;
    }
}

