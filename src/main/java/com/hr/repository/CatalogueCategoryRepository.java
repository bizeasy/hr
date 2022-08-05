package com.hr.repository;

import com.hr.domain.CatalogueCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CatalogueCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogueCategoryRepository extends JpaRepository<CatalogueCategory, Long>, JpaSpecificationExecutor<CatalogueCategory> {
}
