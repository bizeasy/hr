package com.hr.repository;

import com.hr.domain.Status;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {
}
