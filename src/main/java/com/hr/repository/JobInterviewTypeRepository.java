package com.hr.repository;

import com.hr.domain.JobInterviewType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobInterviewType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobInterviewTypeRepository extends JpaRepository<JobInterviewType, Long> {
}
