package com.hr.service;

import com.hr.domain.CatalogueCategory;
import com.hr.repository.CatalogueCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatalogueCategory}.
 */
@Service
@Transactional
public class CatalogueCategoryService {

    private final Logger log = LoggerFactory.getLogger(CatalogueCategoryService.class);

    private final CatalogueCategoryRepository catalogueCategoryRepository;

    public CatalogueCategoryService(CatalogueCategoryRepository catalogueCategoryRepository) {
        this.catalogueCategoryRepository = catalogueCategoryRepository;
    }

    /**
     * Save a catalogueCategory.
     *
     * @param catalogueCategory the entity to save.
     * @return the persisted entity.
     */
    public CatalogueCategory save(CatalogueCategory catalogueCategory) {
        log.debug("Request to save CatalogueCategory : {}", catalogueCategory);
        return catalogueCategoryRepository.save(catalogueCategory);
    }

    /**
     * Get all the catalogueCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatalogueCategory> findAll(Pageable pageable) {
        log.debug("Request to get all CatalogueCategories");
        return catalogueCategoryRepository.findAll(pageable);
    }


    /**
     * Get one catalogueCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatalogueCategory> findOne(Long id) {
        log.debug("Request to get CatalogueCategory : {}", id);
        return catalogueCategoryRepository.findById(id);
    }

    /**
     * Delete the catalogueCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatalogueCategory : {}", id);
        catalogueCategoryRepository.deleteById(id);
    }
}
