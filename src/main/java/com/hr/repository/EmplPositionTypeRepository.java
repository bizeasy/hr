package com.hr.repository;

import com.hr.domain.EmplPositionType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplPositionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplPositionTypeRepository extends JpaRepository<EmplPositionType, Long> {
}
