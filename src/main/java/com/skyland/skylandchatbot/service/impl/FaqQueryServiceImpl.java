package com.skyland.skylandchatbot.service.impl;

import com.skyland.skylandchatbot.dto.FaqResponse;
import com.skyland.skylandchatbot.entity.FaqEntity;
import com.skyland.skylandchatbot.repository.FaqRepository;
import com.skyland.skylandchatbot.service.FaqQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FAQ 조회 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FaqQueryServiceImpl implements FaqQueryService {

    private final FaqRepository faqRepository;

    /**
     * 모든 FAQ 조회 (읽기 전용 트랜잭션)
     */
    @Transactional(readOnly = true)
    @Override
    public List<FaqResponse> findAllFaqs() {
        log.info("전체 FAQ 조회 요청");

        List<FaqEntity> faqEntities = faqRepository.findAll();

        log.info("전체 FAQ 조회 완료: {} 개", faqEntities.size());

        return faqEntities.stream()
                .map(FaqResponse::from)
                .toList();
    }

    /**
     * ID로 FAQ 조회 (읽기 전용 트랜잭션)
     */
    @Transactional(readOnly = true)
    @Override
    public FaqResponse findFaqById(Long faqId) {
        log.info("FAQ 단건 조회 요청: ID={}", faqId);

        FaqEntity faqEntity = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 FAQ ID: " + faqId));

        log.info("FAQ 단건 조회 완료: ID={}", faqId);

        return FaqResponse.from(faqEntity);
    }
}

