package com.hr.repository;

import com.hr.domain.PartyContactMech;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyContactMech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyContactMechRepository extends JpaRepository<PartyContactMech, Long>, JpaSpecificationExecutor<PartyContactMech> {
}
