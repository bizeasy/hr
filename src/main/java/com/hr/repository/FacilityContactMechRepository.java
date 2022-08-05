package com.hr.repository;

import com.hr.domain.FacilityContactMech;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FacilityContactMech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityContactMechRepository extends JpaRepository<FacilityContactMech, Long>, JpaSpecificationExecutor<FacilityContactMech> {
}
