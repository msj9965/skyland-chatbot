package com.skyland.skylandchatbot.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatModel chatModel;     // AWS Bedrock Claude
    private final VectorStore vectorStore; // Postgres pgvector
    private static final String CONTACT_INFO_MESSAGE =
            "죄송합니다. 문의하신 내용에 대한 정확한 정보를 찾을 수 없습니다.\n" +
                    "더 자세한 내용은 스카이랜드(032-327-0093 또는 032-327-0094)로 전화 주시면 친절히 안내해 드리겠습니다.";

    public String chat(String userMessage) {
        // 1. [Retrieval] 사용자 질문과 유사한 문서 검색 (상위 3개)
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.builder().query(userMessage).topK(3).build()
        );

        // 검색된 내용이 없으면 기본 답변 처리 (선택 사항)
        if (similarDocuments.isEmpty()) {
            log.info(">>> [RAG] 검색 결과 없음. 전화번호 안내로 대체.");
            return CONTACT_INFO_MESSAGE;
        }

        // 2. [Context] 검색된 문서를 하나의 문자열로 합침
        String context = similarDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        log.info(">>> [RAG] 검색된 문서 개수: {}", similarDocuments.size());
        log.debug(">>> [RAG] 검색된 컨텍스트:\n{}", context);

        // 3. [Prompt] 시스템 프롬프트 구성 (페르소나 + 컨텍스트 주입)
        String systemText = """
                당신은 '스카이랜드 사우나'의 친절하고 전문적인 AI 상담원입니다.
                반드시 아래 [Context]에 제공된 정보만을 바탕으로 답변해야 합니다.
                
                [답변 작성 규칙]
                1. [Context]에 있는 내용으로 답변이 가능하다면, 친절한 해요체(~요)로 답변하세요.
                2. 만약 [Context]에 정보가 없거나, 내용이 불확실하여 답변하기 어렵다면 절대 내용을 지어내지 마세요.
                3. 답변할 수 없는 경우에는 반드시 아래 문구 그대로 답변하세요:
                   "%s"
                
                [Context]
                %s
                """.formatted(CONTACT_INFO_MESSAGE, context);

        // 4. [Generation] LLM 호출
        Message systemMessage = new SystemMessage(systemText);
        Message userMsg = new UserMessage(userMessage);

        ChatResponse response = chatModel.call(new Prompt(List.of(systemMessage, userMsg)));

        return response.getResult().getOutput().getText();
    }
}