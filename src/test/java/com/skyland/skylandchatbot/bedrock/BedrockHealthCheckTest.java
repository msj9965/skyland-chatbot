package com.skyland.skylandchatbot.bedrock;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.ai.bedrock.titan.BedrockTitanEmbeddingModel.*;

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

        // When
        System.out.println(">>> [Chat] 질문 전송: " + question);
        String answer = chatModel.call(question);

        // Then
        System.out.println(">>> [Chat] 답변 수신: " + answer);
        assertThat(answer)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("2. [Embedding] Titan v2 벡터 변환 테스트")
    void testEmbeddingModel() {
        // Given
        String text = "이 텍스트를 벡터로 변환해줘.";

        // When
        System.out.println(">>> [Embed] 변환 요청: " + text);
        EmbeddingResponse response = embeddingModel.call(
                new EmbeddingRequest(List.of(text),
                        BedrockTitanEmbeddingOptions.builder()
                                .inputType(InputType.TEXT)
                                .build())
        );

        // Then
        // [해결 3] 반환 타입을 List<Double>에서 float[]로 변경 (최신 버전 스펙)
        float[] vector = response.getResults().getFirst().getOutput();
        int dimension = vector.length; // size() 대신 length 사용

        System.out.println(">>> [Embed] 변환 성공!");
        System.out.println(">>> [Embed] 차원 수(Dimension): " + dimension);

        // 출력 확인용 (앞에 5개만)
        System.out.print(">>> [Embed] 벡터 데이터(일부): [");
        for(int i=0; i<5; i++) System.out.print(vector[i] + ", ");
        System.out.println("...]");

        assertThat(vector)
                .isNotEmpty()
                .hasSize(1024); // Titan v2의 기본 차원은 1024입니다. (설정에 따라 256, 512도 가능하나 default는 1024)
    }
}