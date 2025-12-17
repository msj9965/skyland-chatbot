package com.skyland.skylandchatbot.bedrock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.bedrock.titan.BedrockTitanEmbeddingOptions;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.ai.bedrock.titan.BedrockTitanEmbeddingModel.*;

@Slf4j
@SpringBootTest
class BedrockHealthCheckTest {

    @Autowired
    private ChatModel chatModel; // Claude 3.5 연결

    @Autowired
    @Qualifier("titanEmbeddingModel")
    private EmbeddingModel embeddingModel; // Titan v2 연결

    @Test
    @DisplayName("1. [Chat] Claude 3.5 Sonnet 응답 테스트")
    void testChatModel() {
        // Given
        String question = "부천 상동에 있는 스카이랜드 사우나는 어떤 곳이야? 한 문장으로 소개해줘.";
        log.info("[Chat] 질문 전송: {}", question);

        // When
        String answer = chatModel.call(question);

        // Then
        log.info("[Chat] 답변 수신: {}", answer);
        assertThat(answer)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("2. [Embedding] Titan v2 벡터 변환 테스트")
    void testEmbeddingModel() {
        // Given
        String text = "이 텍스트를 벡터로 변환해줘.";
        log.info("[Embed] 변환 요청: {}", text);

        // When
        EmbeddingResponse response = embeddingModel.call(
                new EmbeddingRequest(List.of(text),
                        BedrockTitanEmbeddingOptions.builder()
                                .inputType(InputType.TEXT)
                                .build())
        );

        // Then
        float[] vector = response.getResults().getFirst().getOutput();
        int dimension = vector.length;

        // 벡터 데이터의 앞부분 5개만 추출하여 로깅
        String vectorPreview = IntStream.range(0, Math.min(5, vector.length))
                .mapToObj(i -> String.format("%.4f", vector[i]))
                .collect(Collectors.joining(", ", "[", ", ...]"));

        log.info("[Embed] 변환 성공! 차원 수: {}", dimension);
        log.info("[Embed] 벡터 데이터(일부): {}", vectorPreview);

        // SonarQube 권장: assertion을 체이닝으로 연결
        assertThat(vector)
                .isNotEmpty()
                .hasSize(1024); // Titan v2의 기본 차원은 1024입니다.
    }
}