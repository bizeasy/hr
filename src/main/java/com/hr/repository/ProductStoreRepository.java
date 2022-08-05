package com.hr.repository;

import com.hr.domain.ProductStore;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductStore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductStoreRepository extends JpaRepository<ProductStore, Long>, JpaSpecificationExecutor<ProductStore> {
}
