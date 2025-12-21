package com.skyland.skylandchatbot.dto;

import com.skyland.skylandchatbot.entity.FaqEntity;

import java.time.LocalDateTime;

/**
 * FAQ 응답 DTO
 */
public record FaqResponse(
        Long faqId,
        String category,
        String question,
        String answer,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    /**
     * 엔티티로부터 DTO 생성
     */
    public static FaqResponse from(FaqEntity entity) {
        return new FaqResponse(
                entity.getFaqId(),
                entity.getCategory(),
                entity.getQuestion(),
                entity.getAnswer(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

