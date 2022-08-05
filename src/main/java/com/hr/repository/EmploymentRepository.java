package com.hr.repository;

import com.hr.domain.Employment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Employment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {
}
