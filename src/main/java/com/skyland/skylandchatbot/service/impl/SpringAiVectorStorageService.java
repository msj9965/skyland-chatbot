package com.skyland.skylandchatbot.service.impl;

import com.skyland.skylandchatbot.service.VectorStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Spring AI VectorStore를 사용한 벡터 저장소 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SpringAiVectorStorageService implements VectorStorageService {

    private final VectorStore vectorStore;

    @Override
    public void save(List<Document> documents) {
        vectorStore.add(documents);
        log.info("벡터 저장소에 {} 개의 문서 저장 완료", documents.size());
    }
}

