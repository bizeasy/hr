package com.hr.repository;

import com.hr.domain.PayrollPreference;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PayrollPreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollPreferenceRepository extends JpaRepository<PayrollPreference, Long> {
}
