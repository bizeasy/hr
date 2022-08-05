package com.hr.repository;

import com.hr.domain.ItemIssuance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ItemIssuance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemIssuanceRepository extends JpaRepository<ItemIssuance, Long>, JpaSpecificationExecutor<ItemIssuance> {

    @Query("select itemIssuance from ItemIssuance itemIssuance where itemIssuance.issuedByUserLogin.login = ?#{principal.username}")
    List<ItemIssuance> findByIssuedByUserLoginIsCurrentUser();
}
