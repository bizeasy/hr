package com.hr.repository;

import com.hr.domain.GeoType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GeoType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeoTypeRepository extends JpaRepository<GeoType, Long> {
}
