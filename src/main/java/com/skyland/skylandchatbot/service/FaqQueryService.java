package com.skyland.skylandchatbot.service;

import com.skyland.skylandchatbot.dto.FaqResponse;

import java.util.List;

/**
 * FAQ 조회 서비스 인터페이스
 */
public interface FaqQueryService {
    /**
     * 모든 FAQ를 조회합니다.
     * @return FAQ 응답 리스트
     */
    List<FaqResponse> findAllFaqs();

    /**
     * ID로 FAQ를 조회합니다.
     * @param faqId FAQ ID
     * @return FAQ 응답
     */
    FaqResponse findFaqById(Long faqId);
}

