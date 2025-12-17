package com.skyland.skylandchatbot.dto;

import org.springframework.util.Assert;

/**
 * FAQ 생성 요청 DTO (불변 객체)
 *
 * Record를 사용하여 불변성 보장:
 * - 모든 필드는 final
 * - setter 메서드가 없음
 * - 방어적 복사가 자동으로 적용됨
 *
 * Compact Constructor를 통한 유효성 검증:
 * - 생성 시점에 유효성 검증 수행 (Fail-Fast)
 * - 유효하지 않은 객체는 생성되지 않음
 */
public record FaqCreateRequest(
        String category,
        String question,
        String answer
) {
    /**
     * Compact Constructor: Record 생성 시 자동으로 호출됨
     * 유효성 검증을 통해 불변 객체의 무결성 보장
     */
    public FaqCreateRequest {
        Assert.hasText(category, "카테고리는 필수입니다.");
        Assert.hasText(question, "질문은 필수입니다.");
        Assert.hasText(answer, "답변은 필수입니다.");
    }
}
