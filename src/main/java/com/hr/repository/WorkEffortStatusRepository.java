package com.hr.repository;

import com.hr.domain.WorkEffortStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffortStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortStatusRepository extends JpaRepository<WorkEffortStatus, Long>, JpaSpecificationExecutor<WorkEffortStatus> {
}
