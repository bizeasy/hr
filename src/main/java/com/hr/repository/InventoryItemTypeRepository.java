package com.hr.repository;

import com.hr.domain.InventoryItemType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InventoryItemType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryItemTypeRepository extends JpaRepository<InventoryItemType, Long> {
}
