package com.hr.repository;

import com.hr.domain.FacilityEquipment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FacilityEquipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityEquipmentRepository extends JpaRepository<FacilityEquipment, Long> {
}
