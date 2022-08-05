package com.hr.repository;

import com.hr.domain.WorkEffortAssoc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkEffortAssoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEffortAssocRepository extends JpaRepository<WorkEffortAssoc, Long>, JpaSpecificationExecutor<WorkEffortAssoc> {
}
