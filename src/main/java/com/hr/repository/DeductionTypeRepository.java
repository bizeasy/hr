package com.hr.repository;

import com.hr.domain.DeductionType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DeductionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeductionTypeRepository extends JpaRepository<DeductionType, Long> {
}
