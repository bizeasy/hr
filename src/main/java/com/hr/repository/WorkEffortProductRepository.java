package com.hr.repository;

import com.hr.domain.WorkEffortProduct;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffortProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortProductRepository extends JpaRepository<WorkEffortProduct, Long>, JpaSpecificationExecutor<WorkEffortProduct> {
}
