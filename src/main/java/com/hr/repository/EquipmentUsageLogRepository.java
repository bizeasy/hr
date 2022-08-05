package com.hr.repository;

import com.hr.domain.EquipmentUsageLog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EquipmentUsageLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentUsageLogRepository extends JpaRepository<EquipmentUsageLog, Long> {

    @Query("select equipmentUsageLog from EquipmentUsageLog equipmentUsageLog where equipmentUsageLog.cleanedBy.login = ?#{principal.username}")
    List<EquipmentUsageLog> findByCleanedByIsCurrentUser();

    @Query("select equipmentUsageLog from EquipmentUsageLog equipmentUsageLog where equipmentUsageLog.checkedBy.login = ?#{principal.username}")
    List<EquipmentUsageLog> findByCheckedByIsCurrentUser();
}
