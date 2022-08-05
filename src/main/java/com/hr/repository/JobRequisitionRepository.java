package com.hr.repository;

import com.hr.domain.JobRequisition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobRequisition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobRequisitionRepository extends JpaRepository<JobRequisition, Long> {
}
