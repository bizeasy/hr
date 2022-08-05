package com.hr.repository;

import com.hr.domain.JobInterview;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobInterview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobInterviewRepository extends JpaRepository<JobInterview, Long> {
}
