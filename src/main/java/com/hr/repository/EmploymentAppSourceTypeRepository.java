package com.hr.repository;

import com.hr.domain.EmploymentAppSourceType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmploymentAppSourceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentAppSourceTypeRepository extends JpaRepository<EmploymentAppSourceType, Long> {
}
