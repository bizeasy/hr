package com.hr.repository;

import com.hr.domain.PartyClassificationGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyClassificationGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyClassificationGroupRepository extends JpaRepository<PartyClassificationGroup, Long> {
}
