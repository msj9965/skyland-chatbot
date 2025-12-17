package com.skyland.skylandchatbot.repository;

import com.skyland.skylandchatbot.entity.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<FaqEntity, Long> {
}