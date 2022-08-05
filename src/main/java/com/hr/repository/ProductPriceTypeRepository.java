package com.hr.repository;

import com.hr.domain.ProductPriceType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductPriceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductPriceTypeRepository extends JpaRepository<ProductPriceType, Long> {
}
