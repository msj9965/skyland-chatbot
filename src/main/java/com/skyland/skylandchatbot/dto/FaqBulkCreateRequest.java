package com.skyland.skylandchatbot.dto;

import org.springframework.util.Assert;

import java.util.List;

/**
 * FAQ 일괄 등록 요청 DTO
 */
public record FaqBulkCreateRequest(
        List<FaqCreateRequest> faqs
) {
    /**
     * 유효성 검증
     */
    public FaqBulkCreateRequest {
        Assert.notNull(faqs, "FAQ 리스트는 null일 수 없습니다.");
        Assert.notEmpty(faqs, "FAQ 리스트는 비어있을 수 없습니다.");
    }
}

