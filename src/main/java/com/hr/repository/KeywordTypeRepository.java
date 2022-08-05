package com.hr.repository;

import com.hr.domain.KeywordType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KeywordType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeywordTypeRepository extends JpaRepository<KeywordType, Long> {
}
