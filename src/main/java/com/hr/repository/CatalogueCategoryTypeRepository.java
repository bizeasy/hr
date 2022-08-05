package com.hr.repository;

import com.hr.domain.CatalogueCategoryType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CatalogueCategoryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogueCategoryTypeRepository extends JpaRepository<CatalogueCategoryType, Long> {
}
