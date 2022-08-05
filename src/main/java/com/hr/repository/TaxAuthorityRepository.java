package com.hr.repository;

import com.hr.domain.TaxAuthority;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaxAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxAuthorityRepository extends JpaRepository<TaxAuthority, Long> {
}
