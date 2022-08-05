package com.hr.repository;

import com.hr.domain.PerfReviewItemType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PerfReviewItemType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfReviewItemTypeRepository extends JpaRepository<PerfReviewItemType, Long> {
}
