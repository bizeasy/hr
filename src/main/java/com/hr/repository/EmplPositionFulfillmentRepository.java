package com.hr.repository;

import com.hr.domain.EmplPositionFulfillment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmplPositionFulfillment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmplPositionFulfillmentRepository extends JpaRepository<EmplPositionFulfillment, Long> {
}
