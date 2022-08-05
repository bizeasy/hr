package com.hr.repository;

import com.hr.domain.ProductStoreFacility;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductStoreFacility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductStoreFacilityRepository extends JpaRepository<ProductStoreFacility, Long>, JpaSpecificationExecutor<ProductStoreFacility> {
}
