package com.hr.repository;

import com.hr.domain.WorkEffortType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffortType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortTypeRepository extends JpaRepository<WorkEffortType, Long> {
}
