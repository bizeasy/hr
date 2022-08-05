package com.hr.repository;

import com.hr.domain.EmploymentApp;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmploymentApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentAppRepository extends JpaRepository<EmploymentApp, Long> {
}
