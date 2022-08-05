package com.hr.repository;

import com.hr.domain.ProductFacility;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductFacility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductFacilityRepository extends JpaRepository<ProductFacility, Long>, JpaSpecificationExecutor<ProductFacility> {
}
