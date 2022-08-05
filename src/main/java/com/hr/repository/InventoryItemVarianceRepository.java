package com.hr.repository;

import com.hr.domain.InventoryItemVariance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InventoryItemVariance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryItemVarianceRepository extends JpaRepository<InventoryItemVariance, Long>, JpaSpecificationExecutor<InventoryItemVariance> {
}
