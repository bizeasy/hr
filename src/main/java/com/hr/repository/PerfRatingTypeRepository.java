package com.hr.repository;

import com.hr.domain.PerfRatingType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PerfRatingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfRatingTypeRepository extends JpaRepository<PerfRatingType, Long> {
}
