package com.hr.repository;

import com.hr.domain.ShiftHolidays;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShiftHolidays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShiftHolidaysRepository extends JpaRepository<ShiftHolidays, Long> {
}
