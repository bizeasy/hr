package com.hr.repository;

import com.hr.domain.ShiftWeekends;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShiftWeekends entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShiftWeekendsRepository extends JpaRepository<ShiftWeekends, Long> {
}
