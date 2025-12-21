package com.skyland.skylandchatbot.service.impl;

import com.skyland.skylandchatbot.entity.FaqEntity;
import com.skyland.skylandchatbot.repository.FaqRepository;
import com.skyland.skylandchatbot.service.FaqDeletionService;
import com.skyland.skylandchatbot.service.VectorDocumentMapper;
import com.skyland.skylandchatbot.service.VectorStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FAQ 삭제 서비스 구현체ㅇ
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FaqDeletionServiceImpl implements FaqDeletionService {

    private final FaqRepository faqRepository;
    private final VectorDocumentMapper documentMapper;
    private final VectorStorageService vectorStorageService;

    /**
     * FAQ 삭제 프로세스
     * 1. FAQ 존재 확인
     * 2. Vector DB에서 삭제
     * 3. RDB에서 삭제
     */
    @Transactional
    @Override
    public void deleteFaq(Long faqId) {
        log.info("FAQ 삭제 시작: ID={}", faqId);

        // 1. FAQ 존재 확인
        FaqEntity faqEntity = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 FAQ ID: " + faqId));

        // 2. Vector DB에서 삭제
        try {
            Document document = documentMapper.toDocument(faqEntity);
            vectorStorageService.delete(document.getId());
            log.info("벡터 저장소에서 FAQ 삭제 완료: ID={}", faqId);
        } catch (Exception e) {
            log.warn("벡터 저장소 삭제 중 오류 발생 (계속 진행): {}", e.getMessage());
        }

        // 3. RDB에서 삭제
        faqRepository.deleteById(faqId);
        log.info("FAQ 삭제 완료: ID={}", faqId);
    }
}

