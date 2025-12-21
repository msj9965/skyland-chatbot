package com.skyland.skylandchatbot.service;

import com.skyland.skylandchatbot.dto.FaqCreateRequest;

import java.util.List;

/**
 * FAQ 등록 서비스 인터페이스
 */
public interface FaqRegistrationService {
    /**
     * FAQ를 등록합니다.
     * @param request FAQ 생성 요청 정보
     */
    void registerFaq(FaqCreateRequest request);

    /**
     * 여러 개의 FAQ를 일괄 등록합니다.
     * @param requests FAQ 생성 요청 리스트
     */
    void registerFaqs(List<FaqCreateRequest> requests);
}

