package com.skyland.skylandchatbot.entity;

import com.skyland.skylandchatbot.dto.FaqCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "faq_master")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FaqEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;

    @CreationTimestamp
    @Column(nullable = false, updatable = false) // 생성일은 수정되지 않도록 설정
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private FaqEntity(String category, String question, String answer) {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    /**
     * 정적 팩토리 메서드 - DTO로부터 Entity 생성
     * 생성 로직을 Entity 내부로 캡슐화
     */
    public static FaqEntity createFrom(FaqCreateRequest request) {
        return FaqEntity.builder()
                .category(request.category())
                .question(request.question())
                .answer(request.answer())
                .build();
    }

    /**
     * 검색 가능한 컨텐츠로 변환
     * 도메인 로직을 Entity 내부에 캡슐화
     */
    public String toSearchableContent() {
        return String.format("""
                [카테고리] %s
                [질문] %s
                [답변] %s
                """, this.category, this.question, this.answer);
    }

    /**
     * FAQ 내용 수정
     * 불변성을 유지하면서 상태 변경 (도메인 로직)
     */
    public void updateContent(String category, String question, String answer) {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }
}