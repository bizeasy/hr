package com.hr.repository;

import com.hr.domain.ProductPricePurpose;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductPricePurpose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductPricePurposeRepository extends JpaRepository<ProductPricePurpose, Long> {
}
