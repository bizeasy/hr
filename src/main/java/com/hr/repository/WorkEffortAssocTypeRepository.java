package com.hr.repository;

import com.hr.domain.WorkEffortAssocType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffortAssocType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortAssocTypeRepository extends JpaRepository<WorkEffortAssocType, Long> {
}
