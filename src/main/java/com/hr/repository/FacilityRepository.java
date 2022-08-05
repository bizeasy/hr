package com.hr.repository;

import com.hr.domain.Facility;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Facility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long>, JpaSpecificationExecutor<Facility> {

    @Query("select facility from Facility facility where facility.reviewedBy.login = ?#{principal.username}")
    List<Facility> findByReviewedByIsCurrentUser();

    @Query("select facility from Facility facility where facility.approvedBy.login = ?#{principal.username}")
    List<Facility> findByApprovedByIsCurrentUser();
}
