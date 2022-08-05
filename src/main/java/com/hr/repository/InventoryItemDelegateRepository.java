package com.hr.repository;

import com.hr.domain.InventoryItemDelegate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InventoryItemDelegate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryItemDelegateRepository extends JpaRepository<InventoryItemDelegate, Long>, JpaSpecificationExecutor<InventoryItemDelegate> {
}
