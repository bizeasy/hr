package com.hr.repository;

import com.hr.domain.TaxAuthorityRateType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaxAuthorityRateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxAuthorityRateTypeRepository extends JpaRepository<TaxAuthorityRateType, Long> {
}
