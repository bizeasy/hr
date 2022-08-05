package com.hr.repository;

import com.hr.domain.PartyClassificationType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyClassificationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyClassificationTypeRepository extends JpaRepository<PartyClassificationType, Long> {
}
