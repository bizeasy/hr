package com.hr.repository;

import com.hr.domain.EmplPositionGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplPositionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplPositionGroupRepository extends JpaRepository<EmplPositionGroup, Long> {
}
