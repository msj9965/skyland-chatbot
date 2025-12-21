package com.skyland.skylandchatbot.controller;

import com.skyland.skylandchatbot.dto.FaqBulkCreateRequest;
import com.skyland.skylandchatbot.dto.FaqCreateRequest;
import com.skyland.skylandchatbot.dto.FaqResponse;
import com.skyland.skylandchatbot.service.FaqDeletionService;
import com.skyland.skylandchatbot.service.FaqQueryService;
import com.skyland.skylandchatbot.service.FaqRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FAQ 관련 REST API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/faqs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqRegistrationService faqRegistrationService;
    private final FaqQueryService faqQueryService;
    private final FaqDeletionService faqDeletionService;

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

    /**
     * FAQ 전체 조회 API
     * [GET] /api/v1/faqs
     *
     * @return 200 OK with FAQ 리스트
     */
    @GetMapping
    public ResponseEntity<List<FaqResponse>> getAllFaqs() {
        log.info("FAQ 전체 조회 요청 수신");

        List<FaqResponse> faqs = faqQueryService.findAllFaqs();

        return ResponseEntity.ok(faqs);
    }

    /**
     * FAQ 단건 조회 API
     * [GET] /api/v1/faqs/{faqId}
     *
     * @param faqId FAQ ID
     * @return 200 OK with FAQ 정보
     */
    @GetMapping("/{faqId}")
    public ResponseEntity<FaqResponse> getFaqById(@PathVariable Long faqId) {
        log.info("FAQ 단건 조회 요청 수신: ID={}", faqId);

        FaqResponse faq = faqQueryService.findFaqById(faqId);

        return ResponseEntity.ok(faq);
    }

    /**
     * FAQ 단건 삭제 API
     * [DELETE] /api/v1/faqs/{faqId}
     *
     * @param faqId 삭제할 FAQ ID
     * @return 204 No Content
     */
    @DeleteMapping("/{faqId}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long faqId) {
        log.info("FAQ 단건 삭제 요청 수신: ID={}", faqId);

        faqDeletionService.deleteFaq(faqId);

        return ResponseEntity.noContent().build();
    }
}