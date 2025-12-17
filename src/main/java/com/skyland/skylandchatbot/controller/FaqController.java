package com.skyland.skylandchatbot.controller;

import com.skyland.skylandchatbot.dto.FaqBulkCreateRequest;
import com.skyland.skylandchatbot.dto.FaqCreateRequest;
import com.skyland.skylandchatbot.service.FaqRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * FAQ 관련 REST API 컨트롤러
 * SOLID 원칙 적용:
 * - DIP: 구체 클래스(FaqService)가 아닌 인터페이스(FaqRegistrationService)에 의존
 * - SRP: HTTP 요청/응답 처리라는 단일 책임만 수행
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqRegistrationService faqRegistrationService;

    /**
     * FAQ 등록 API
     * [POST] /api/v1/faqs
     *
     * @param request FAQ 생성 요청 (유효성 검증은 Record Compact Constructor에서 수행)
     * @return 201 Created
     */
    @PostMapping
    public ResponseEntity<Void> registerFaq(@RequestBody FaqCreateRequest request) {
        log.info("FAQ 등록 요청 수신: category={}, question={}", request.category(), request.question());

        faqRegistrationService.registerFaq(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * FAQ 일괄 등록 API
     * [POST] /api/v1/faqs/bulk
     *
     * @param request FAQ 일괄 생성 요청
     * @return 201 Created
     */
    @PostMapping("/bulk")
    public ResponseEntity<Void> registerFaqs(@RequestBody FaqBulkCreateRequest request) {
        log.info("FAQ 일괄 등록 요청 수신: {} 개", request.faqs().size());

        faqRegistrationService.registerFaqs(request.faqs());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}