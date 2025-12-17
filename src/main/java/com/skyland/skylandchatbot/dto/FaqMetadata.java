package com.skyland.skylandchatbot.dto;

import com.skyland.skylandchatbot.entity.FaqEntity;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Vector Store에 저장될 메타데이터의 스키마를 정의하는 불변 객체
 */
public record FaqMetadata(
        String originalId,
        String category,
        String createdAt,
        String updatedAt
) {
    // Key 이름들을 상수로 관리
    public static final String KEY_ORIGINAL_ID = "original_id";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CREATED_AT = "created_at";
    public static final String KEY_UPDATED_AT = "updated_at";

    // Entity로부터 메타데이터 객체를 생성하는 정적 팩토리 메서드
    public static FaqMetadata from(FaqEntity entity) {
        Assert.notNull(entity, "Entity must not be null");
        return new FaqMetadata(
                entity.getFaqId().toString(),
                entity.getCategory(),
                entity.getCreatedAt().toString(),
                entity.getUpdatedAt().toString()
        );
    }

    // Spring AI Document에 넘겨주기 위해 Map으로 변환하는 메서드
    public Map<String, Object> toMap() {
        return Map.of(
                KEY_ORIGINAL_ID, originalId,
                KEY_CATEGORY, category,
                KEY_CREATED_AT, createdAt,
                KEY_UPDATED_AT, updatedAt
        );
    }
}