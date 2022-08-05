package com.hr.repository;

import com.hr.domain.ContactMechType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContactMechType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactMechTypeRepository extends JpaRepository<ContactMechType, Long> {
}
