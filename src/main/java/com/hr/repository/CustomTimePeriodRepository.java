package com.hr.repository;

import com.hr.domain.CustomTimePeriod;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomTimePeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomTimePeriodRepository extends JpaRepository<CustomTimePeriod, Long> {
}
