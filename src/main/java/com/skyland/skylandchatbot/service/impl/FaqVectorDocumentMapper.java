package com.skyland.skylandchatbot.service.impl;

import com.skyland.skylandchatbot.dto.FaqMetadata;
import com.skyland.skylandchatbot.entity.FaqEntity;
import com.skyland.skylandchatbot.service.VectorDocumentMapper;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

/**
 * FAQ를 벡터 문서로 변환하는 구현체
 */
@Component
public class FaqVectorDocumentMapper implements VectorDocumentMapper {

    @Override
    public Document toDocument(FaqEntity faqEntity) {
        String embeddingContent = faqEntity.toSearchableContent();
        FaqMetadata metadata = FaqMetadata.from(faqEntity);

        // Document 생성

        return new Document(embeddingContent, metadata.toMap());
    }
}

