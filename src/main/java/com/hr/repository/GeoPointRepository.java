package com.hr.repository;

import com.hr.domain.GeoPoint;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GeoPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeoPointRepository extends JpaRepository<GeoPoint, Long>, JpaSpecificationExecutor<GeoPoint> {
}
