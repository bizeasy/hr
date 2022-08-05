package com.hr.repository;

import com.hr.domain.FacilityGroupType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FacilityGroupType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityGroupTypeRepository extends JpaRepository<FacilityGroupType, Long> {
}
