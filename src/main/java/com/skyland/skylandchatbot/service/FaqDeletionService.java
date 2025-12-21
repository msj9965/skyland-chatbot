package com.skyland.skylandchatbot.service;

/**
 * FAQ 삭제 서비스 인터페이스
 */
public interface FaqDeletionService {
    /**
     * FAQ를 삭제합니다.
     * @param faqId 삭제할 FAQ ID
     */
    void deleteFaq(Long faqId);
}

