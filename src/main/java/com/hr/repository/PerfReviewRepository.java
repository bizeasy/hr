package com.hr.repository;

import com.hr.domain.PerfReview;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PerfReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfReviewRepository extends JpaRepository<PerfReview, Long> {
}
