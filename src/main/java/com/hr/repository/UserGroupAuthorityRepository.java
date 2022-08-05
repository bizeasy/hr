package com.hr.repository;

import com.hr.domain.UserGroupAuthority;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserGroupAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGroupAuthorityRepository extends JpaRepository<UserGroupAuthority, Long>, JpaSpecificationExecutor<UserGroupAuthority> {
}
