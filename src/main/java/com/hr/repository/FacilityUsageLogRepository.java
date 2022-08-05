package com.hr.repository;

import com.hr.domain.FacilityUsageLog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FacilityUsageLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityUsageLogRepository extends JpaRepository<FacilityUsageLog, Long> {

    @Query("select facilityUsageLog from FacilityUsageLog facilityUsageLog where facilityUsageLog.cleanedBy.login = ?#{principal.username}")
    List<FacilityUsageLog> findByCleanedByIsCurrentUser();

    @Query("select facilityUsageLog from FacilityUsageLog facilityUsageLog where facilityUsageLog.checkedBy.login = ?#{principal.username}")
    List<FacilityUsageLog> findByCheckedByIsCurrentUser();
}
