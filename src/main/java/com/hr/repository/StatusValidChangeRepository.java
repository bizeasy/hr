package com.hr.repository;

import com.hr.domain.StatusValidChange;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StatusValidChange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusValidChangeRepository extends JpaRepository<StatusValidChange, Long>, JpaSpecificationExecutor<StatusValidChange> {
}
