package com.hr.repository;

import com.hr.domain.EmplPositionTypeRate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplPositionTypeRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplPositionTypeRateRepository extends JpaRepository<EmplPositionTypeRate, Long> {
}
