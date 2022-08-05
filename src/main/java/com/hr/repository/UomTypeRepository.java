package com.hr.repository;

import com.hr.domain.UomType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UomType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UomTypeRepository extends JpaRepository<UomType, Long> {
}
