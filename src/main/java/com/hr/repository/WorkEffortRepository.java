package com.hr.repository;

import com.hr.domain.WorkEffort;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortRepository extends JpaRepository<WorkEffort, Long>, JpaSpecificationExecutor<WorkEffort> {
}
