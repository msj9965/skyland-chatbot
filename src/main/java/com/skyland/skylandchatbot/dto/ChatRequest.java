package com.skyland.skylandchatbot.dto;


import org.springframework.util.Assert;

public record ChatRequest(
        String message
) {
    /**
     * 유효성 검증을 통해 불변 객체의 무결성 보장
     */
    public ChatRequest {
        Assert.hasText(message, "메시지는 필수입니다.");
    }
}