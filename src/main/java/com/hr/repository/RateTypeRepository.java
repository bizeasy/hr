package com.hr.repository;

import com.hr.domain.RateType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateTypeRepository extends JpaRepository<RateType, Long> {
}
