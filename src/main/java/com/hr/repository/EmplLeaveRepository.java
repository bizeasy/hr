package com.hr.repository;

import com.hr.domain.EmplLeave;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplLeave entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplLeaveRepository extends JpaRepository<EmplLeave, Long> {
}
