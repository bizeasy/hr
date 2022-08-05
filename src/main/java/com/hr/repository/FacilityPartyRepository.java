package com.hr.repository;

import com.hr.domain.FacilityParty;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FacilityParty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityPartyRepository extends JpaRepository<FacilityParty, Long>, JpaSpecificationExecutor<FacilityParty> {
}
