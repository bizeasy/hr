package com.hr.repository;

import com.hr.domain.ProductCategoryType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductCategoryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCategoryTypeRepository extends JpaRepository<ProductCategoryType, Long> {
}
