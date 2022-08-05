package com.hr.repository;

import com.hr.domain.WorkEffortInventoryAssign;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffortInventoryAssign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortInventoryAssignRepository extends JpaRepository<WorkEffortInventoryAssign, Long>, JpaSpecificationExecutor<WorkEffortInventoryAssign> {
}
