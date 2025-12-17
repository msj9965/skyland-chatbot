package com.skyland.skylandchatbot.service.impl;

import com.skyland.skylandchatbot.dto.FaqCreateRequest;
import com.skyland.skylandchatbot.entity.FaqEntity;
import com.skyland.skylandchatbot.repository.FaqRepository;
import com.skyland.skylandchatbot.service.FaqRegistrationService;
import com.skyland.skylandchatbot.service.VectorDocumentMapper;
import com.skyland.skylandchatbot.service.VectorStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FAQ 등록 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FaqRegistrationServiceImpl implements FaqRegistrationService {

    private final FaqRepository faqRepository;
    private final VectorDocumentMapper documentMapper;
    private final VectorStorageService vectorStorageService;

    /**
     * FAQ 등록 프로세스
     * 1. RDB 저장
     * 2. Vector DB 저장
     */
    @Transactional
    @Override
    public void registerFaq(FaqCreateRequest request) {
        // 1. 도메인 객체 생성
        FaqEntity faqEntity = FaqEntity.createFrom(request);

        // 2. RDB 저장
        FaqEntity savedEntity = faqRepository.save(faqEntity);
        log.info("FAQ 원본 저장 완료: ID={}", savedEntity.getFaqId());

        // 3. 벡터 문서 변환
        Document document = documentMapper.toDocument(savedEntity);

        // 4. 벡터 저장소에 저장
        vectorStorageService.save(List.of(document));

        log.info("FAQ 등록 프로세스 완료: ID={}", savedEntity.getFaqId());
    }

    /**
     * FAQ 일괄 등록 프로세스 (배치 처리 최적화)
     * 1. RDB 일괄 저장
     * 2. Vector DB 일괄 저장
     */
    @Transactional
    @Override
    public void registerFaqs(List<FaqCreateRequest> requests) {
        log.info("FAQ 일괄 등록 시작: {} 개", requests.size());

        // 1. 도메인 객체 리스트 생성
        List<FaqEntity> faqEntities = requests.stream()
                .map(FaqEntity::createFrom)
                .toList();

        // 2. RDB 일괄 저장 (배치 처리)
        List<FaqEntity> savedEntities = faqRepository.saveAll(faqEntities);
        log.info("FAQ 원본 일괄 저장 완료: {} 개", savedEntities.size());

        // 3. 벡터 문서 리스트 변환
        List<Document> documents = savedEntities.stream()
                .map(documentMapper::toDocument)
                .toList();

        // 4. 벡터 저장소에 일괄 저장 (배치 처리)
        vectorStorageService.save(documents);

        log.info("FAQ 일괄 등록 프로세스 완료: {} 개", savedEntities.size());
    }
}

