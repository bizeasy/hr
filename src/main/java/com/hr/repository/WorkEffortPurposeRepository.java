package com.hr.repository;

import com.hr.domain.WorkEffortPurpose;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffortPurpose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortPurposeRepository extends JpaRepository<WorkEffortPurpose, Long> {
}
