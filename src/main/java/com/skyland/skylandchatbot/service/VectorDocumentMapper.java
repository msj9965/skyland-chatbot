package com.skyland.skylandchatbot.service;

import com.skyland.skylandchatbot.entity.FaqEntity;
import org.springframework.ai.document.Document;

/**
 * FAQ를 벡터 문서로 변환하는 책임을 가진 인터페이스
 */
public interface VectorDocumentMapper {
    /**
     * FAQ 엔티티를 벡터 저장소용 Document로 변환합니다.
     * @param faqEntity FAQ 엔티티
     * @return 벡터 저장소에 저장할 Document
     */
    Document toDocument(FaqEntity faqEntity);
}

