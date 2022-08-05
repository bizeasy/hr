package com.hr.repository;

import com.hr.domain.ProductStoreUserGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ProductStoreUserGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductStoreUserGroupRepository extends JpaRepository<ProductStoreUserGroup, Long>, JpaSpecificationExecutor<ProductStoreUserGroup> {

    @Query("select productStoreUserGroup from ProductStoreUserGroup productStoreUserGroup where productStoreUserGroup.user.login = ?#{principal.username}")
    List<ProductStoreUserGroup> findByUserIsCurrentUser();
}
