package com.hr.repository;

import com.hr.domain.InventoryTransfer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InventoryTransfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryTransferRepository extends JpaRepository<InventoryTransfer, Long>, JpaSpecificationExecutor<InventoryTransfer> {
}
