package com.hr.repository;

import com.hr.domain.PartyResume;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyResume entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyResumeRepository extends JpaRepository<PartyResume, Long> {
}
