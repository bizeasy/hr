package com.hr.repository;

import com.hr.domain.InvoiceType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InvoiceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceTypeRepository extends JpaRepository<InvoiceType, Long> {
}
