package com.hr.repository;

import com.hr.domain.EmplPosition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplPositionRepository extends JpaRepository<EmplPosition, Long> {
}
