package com.hr.repository;

import com.hr.domain.PermissionAuthority;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PermissionAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermissionAuthorityRepository extends JpaRepository<PermissionAuthority, Long>, JpaSpecificationExecutor<PermissionAuthority> {
}
