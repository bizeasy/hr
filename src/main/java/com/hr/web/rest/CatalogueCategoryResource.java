package com.hr.web.rest;

import com.hr.domain.CatalogueCategory;
import com.hr.service.CatalogueCategoryService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.CatalogueCategoryCriteria;
import com.hr.service.CatalogueCategoryQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.CatalogueCategory}.
 */
@RestController
@RequestMapping("/api")
public class CatalogueCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CatalogueCategoryResource.class);

    private static final String ENTITY_NAME = "catalogueCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatalogueCategoryService catalogueCategoryService;

    private final CatalogueCategoryQueryService catalogueCategoryQueryService;

    public CatalogueCategoryResource(CatalogueCategoryService catalogueCategoryService, CatalogueCategoryQueryService catalogueCategoryQueryService) {
        this.catalogueCategoryService = catalogueCategoryService;
        this.catalogueCategoryQueryService = catalogueCategoryQueryService;
    }

    /**
     * {@code POST  /catalogue-categories} : Create a new catalogueCategory.
     *
     * @param catalogueCategory the catalogueCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catalogueCategory, or with status {@code 400 (Bad Request)} if the catalogueCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/catalogue-categories")
    public ResponseEntity<CatalogueCategory> createCatalogueCategory(@RequestBody CatalogueCategory catalogueCategory) throws URISyntaxException {
        log.debug("REST request to save CatalogueCategory : {}", catalogueCategory);
        if (catalogueCategory.getId() != null) {
            throw new BadRequestAlertException("A new catalogueCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatalogueCategory result = catalogueCategoryService.save(catalogueCategory);
        return ResponseEntity.created(new URI("/api/catalogue-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /catalogue-categories} : Updates an existing catalogueCategory.
     *
     * @param catalogueCategory the catalogueCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalogueCategory,
     * or with status {@code 400 (Bad Request)} if the catalogueCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catalogueCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/catalogue-categories")
    public ResponseEntity<CatalogueCategory> updateCatalogueCategory(@RequestBody CatalogueCategory catalogueCategory) throws URISyntaxException {
        log.debug("REST request to update CatalogueCategory : {}", catalogueCategory);
        if (catalogueCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatalogueCategory result = catalogueCategoryService.save(catalogueCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catalogueCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /catalogue-categories} : get all the catalogueCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catalogueCategories in body.
     */
    @GetMapping("/catalogue-categories")
    public ResponseEntity<List<CatalogueCategory>> getAllCatalogueCategories(CatalogueCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatalogueCategories by criteria: {}", criteria);
        Page<CatalogueCategory> page = catalogueCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /catalogue-categories/count} : count all the catalogueCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/catalogue-categories/count")
    public ResponseEntity<Long> countCatalogueCategories(CatalogueCategoryCriteria criteria) {
        log.debug("REST request to count CatalogueCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(catalogueCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /catalogue-categories/:id} : get the "id" catalogueCategory.
     *
     * @param id the id of the catalogueCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catalogueCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/catalogue-categories/{id}")
    public ResponseEntity<CatalogueCategory> getCatalogueCategory(@PathVariable Long id) {
        log.debug("REST request to get CatalogueCategory : {}", id);
        Optional<CatalogueCategory> catalogueCategory = catalogueCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catalogueCategory);
    }

    /**
     * {@code DELETE  /catalogue-categories/:id} : delete the "id" catalogueCategory.
     *
     * @param id the id of the catalogueCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/catalogue-categories/{id}")
    public ResponseEntity<Void> deleteCatalogueCategory(@PathVariable Long id) {
        log.debug("REST request to delete CatalogueCategory : {}", id);
        catalogueCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
