package com.hr.repository;

import com.hr.domain.ProductKeyword;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductKeyword entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Long> {
}
