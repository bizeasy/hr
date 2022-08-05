package com.hr.repository;

import com.hr.domain.ProductStoreProduct;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductStoreProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductStoreProductRepository extends JpaRepository<ProductStoreProduct, Long> {
}
