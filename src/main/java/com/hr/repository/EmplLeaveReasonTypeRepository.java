package com.hr.repository;

import com.hr.domain.EmplLeaveReasonType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplLeaveReasonType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplLeaveReasonTypeRepository extends JpaRepository<EmplLeaveReasonType, Long> {
}
