package com.hr.repository;

import com.hr.domain.InvoiceItemType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InvoiceItemType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceItemTypeRepository extends JpaRepository<InvoiceItemType, Long> {
}
