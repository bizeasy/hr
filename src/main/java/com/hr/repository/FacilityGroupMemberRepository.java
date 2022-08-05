package com.hr.repository;

import com.hr.domain.FacilityGroupMember;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FacilityGroupMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityGroupMemberRepository extends JpaRepository<FacilityGroupMember, Long>, JpaSpecificationExecutor<FacilityGroupMember> {
}
