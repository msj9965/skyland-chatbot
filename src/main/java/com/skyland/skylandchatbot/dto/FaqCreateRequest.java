package com.skyland.skylandchatbot.dto;

import org.springframework.util.Assert;

/**
 * FAQ 생성 요청 DTO
 */
public record FaqCreateRequest(
        String category,
        String question,
        String answer
) {
    /**
     * 유효성 검증을 통해 불변 객체의 무결성 보장
     */
    public FaqCreateRequest {
        Assert.hasText(category, "카테고리는 필수입니다.");
        Assert.hasText(question, "질문은 필수입니다.");
        Assert.hasText(answer, "답변은 필수입니다.");
    }
}
