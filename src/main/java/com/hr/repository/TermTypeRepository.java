package com.hr.repository;

import com.hr.domain.TermType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TermType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TermTypeRepository extends JpaRepository<TermType, Long> {
}
