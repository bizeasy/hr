package com.hr.repository;

import com.hr.domain.FacilityGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FacilityGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityGroupRepository extends JpaRepository<FacilityGroup, Long> {
}
