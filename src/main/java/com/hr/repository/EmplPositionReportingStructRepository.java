package com.hr.repository;

import com.hr.domain.EmplPositionReportingStruct;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplPositionReportingStruct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplPositionReportingStructRepository extends JpaRepository<EmplPositionReportingStruct, Long> {
}
