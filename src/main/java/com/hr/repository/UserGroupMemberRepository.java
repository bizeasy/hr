package com.hr.repository;

import com.hr.domain.UserGroupMember;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserGroupMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGroupMemberRepository extends JpaRepository<UserGroupMember, Long>, JpaSpecificationExecutor<UserGroupMember> {

    @Query("select userGroupMember from UserGroupMember userGroupMember where userGroupMember.user.login = ?#{principal.username}")
    List<UserGroupMember> findByUserIsCurrentUser();
}
