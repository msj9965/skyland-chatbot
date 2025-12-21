package com.skyland.skylandchatbot.service.impl;

import com.skyland.skylandchatbot.service.PromptBuilderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 스카이랜드 사우나 AI 상담원용 프롬프트 생성 서비스
 */
@Slf4j
@Service
public class SkylandPromptBuilderService implements PromptBuilderService {

    private static final String CONTACT_INFO_MESSAGE =
            "죄송합니다. 문의하신 내용에 대한 정확한 정보를 찾을 수 없습니다.\\n" +
            "더 자세한 내용은 스카이랜드(032-327-0093 또는 032-327-0094)로 전화 주시면 친절히 안내해 드리겠습니다.";

    private static final String SYSTEM_PROMPT_TEMPLATE = """
            당신은 '스카이랜드 사우나'의 친절하고 전문적인 AI 상담원입니다.
            반드시 아래 [Context]에 제공된 정보만을 바탕으로 답변해야 합니다.
            
            [답변 작성 규칙]
            1. [Context]에 있는 내용으로 답변이 가능하다면, 친절한 해요체(~요)로 답변하세요.
            2. 만약 [Context]에 정보가 없거나, 내용이 불확실하여 답변하기 어렵다면 절대 내용을 지어내지 마세요.
            3. 답변할 수 없는 경우에는 반드시 아래 문구 그대로 답변하세요:
               "%s"
            
            [Context]
            %s
            """;

    @Override
    public String buildSystemPrompt(String context) {
        String prompt = String.format(SYSTEM_PROMPT_TEMPLATE, CONTACT_INFO_MESSAGE, context);
        log.debug(">>> [RAG] 시스템 프롬프트 생성 완료");
        return prompt;
    }

    @Override
    public String buildContextFromDocuments(List<Document> documents) {
        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\\n\\n"));

        log.debug(">>> [RAG] 검색된 컨텍스트:\\n{}", context);
        return context;
    }

    /**
     * 연락처 안내 메시지를 반환합니다.
     * 검색 결과가 없을 때 사용됩니다.
     */
    public String getContactInfoMessage() {
        return CONTACT_INFO_MESSAGE;
    }
}

