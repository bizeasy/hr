package com.hr.repository;

import com.hr.domain.ProductStoreType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductStoreType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductStoreTypeRepository extends JpaRepository<ProductStoreType, Long> {
}
