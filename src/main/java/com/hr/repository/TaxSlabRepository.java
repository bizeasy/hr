package com.hr.repository;

import com.hr.domain.TaxSlab;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaxSlab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxSlabRepository extends JpaRepository<TaxSlab, Long> {
}
