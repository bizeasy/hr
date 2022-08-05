package com.hr.repository;

import com.hr.domain.PerfReviewItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PerfReviewItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfReviewItemRepository extends JpaRepository<PerfReviewItem, Long> {
}
