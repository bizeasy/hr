package com.hr.repository;

import com.hr.domain.ContactMechPurpose;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContactMechPurpose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactMechPurposeRepository extends JpaRepository<ContactMechPurpose, Long> {
}
