package com.hr.repository;

import com.hr.domain.EmplLeaveType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplLeaveType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplLeaveTypeRepository extends JpaRepository<EmplLeaveType, Long> {
}
