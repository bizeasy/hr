package com.hr.repository;

import com.hr.domain.PublicHolidays;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PublicHolidays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicHolidaysRepository extends JpaRepository<PublicHolidays, Long> {
}
