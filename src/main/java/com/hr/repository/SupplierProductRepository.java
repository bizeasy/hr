package com.hr.repository;

import com.hr.domain.SupplierProduct;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupplierProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long>, JpaSpecificationExecutor<SupplierProduct> {
}
