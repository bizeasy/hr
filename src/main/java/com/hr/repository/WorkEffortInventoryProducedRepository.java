package com.hr.repository;

import com.hr.domain.WorkEffortInventoryProduced;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffortInventoryProduced entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortInventoryProducedRepository extends JpaRepository<WorkEffortInventoryProduced, Long>, JpaSpecificationExecutor<WorkEffortInventoryProduced> {
}
