package com.hr.repository;

import com.hr.domain.PartyClassification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyClassification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyClassificationRepository extends JpaRepository<PartyClassification, Long>, JpaSpecificationExecutor<PartyClassification> {
}
