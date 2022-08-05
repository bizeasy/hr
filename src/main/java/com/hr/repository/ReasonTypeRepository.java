package com.hr.repository;

import com.hr.domain.ReasonType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReasonType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReasonTypeRepository extends JpaRepository<ReasonType, Long> {
}
