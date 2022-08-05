package com.hr.repository;

import com.hr.domain.TerminationType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TerminationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerminationTypeRepository extends JpaRepository<TerminationType, Long> {
}
