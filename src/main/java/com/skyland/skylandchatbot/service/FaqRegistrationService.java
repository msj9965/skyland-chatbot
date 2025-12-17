package com.skyland.skylandchatbot.service;

import com.skyland.skylandchatbot.dto.FaqCreateRequest;

/**
 * FAQ 등록 서비스 인터페이스
 * DIP(Dependency Inversion Principle): 상위 모듈이 하위 모듈의 구현에 의존하지 않도록 추상화
 */
public interface FaqRegistrationService {
    /**
     * FAQ를 등록합니다.
     * @param request FAQ 생성 요청 정보
     */
    void registerFaq(FaqCreateRequest request);
}

