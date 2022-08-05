package com.hr.web.rest;

import com.hr.domain.CatalogueCategoryType;
import com.hr.repository.CatalogueCategoryTypeRepository;
import com.hr.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.CatalogueCategoryType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CatalogueCategoryTypeResource {

    private final Logger log = LoggerFactory.getLogger(CatalogueCategoryTypeResource.class);

    private static final String ENTITY_NAME = "catalogueCategoryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatalogueCategoryTypeRepository catalogueCategoryTypeRepository;

    public CatalogueCategoryTypeResource(CatalogueCategoryTypeRepository catalogueCategoryTypeRepository) {
        this.catalogueCategoryTypeRepository = catalogueCategoryTypeRepository;
    }

    /**
     * {@code POST  /catalogue-category-types} : Create a new catalogueCategoryType.
     *
     * @param catalogueCategoryType the catalogueCategoryType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catalogueCategoryType, or with status {@code 400 (Bad Request)} if the catalogueCategoryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/catalogue-category-types")
    public ResponseEntity<CatalogueCategoryType> createCatalogueCategoryType(@Valid @RequestBody CatalogueCategoryType catalogueCategoryType) throws URISyntaxException {
        log.debug("REST request to save CatalogueCategoryType : {}", catalogueCategoryType);
        if (catalogueCategoryType.getId() != null) {
            throw new BadRequestAlertException("A new catalogueCategoryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatalogueCategoryType result = catalogueCategoryTypeRepository.save(catalogueCategoryType);
        return ResponseEntity.created(new URI("/api/catalogue-category-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /catalogue-category-types} : Updates an existing catalogueCategoryType.
     *
     * @param catalogueCategoryType the catalogueCategoryType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalogueCategoryType,
     * or with status {@code 400 (Bad Request)} if the catalogueCategoryType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catalogueCategoryType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/catalogue-category-types")
    public ResponseEntity<CatalogueCategoryType> updateCatalogueCategoryType(@Valid @RequestBody CatalogueCategoryType catalogueCategoryType) throws URISyntaxException {
        log.debug("REST request to update CatalogueCategoryType : {}", catalogueCategoryType);
        if (catalogueCategoryType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatalogueCategoryType result = catalogueCategoryTypeRepository.save(catalogueCategoryType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catalogueCategoryType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /catalogue-category-types} : get all the catalogueCategoryTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catalogueCategoryTypes in body.
     */
    @GetMapping("/catalogue-category-types")
    public List<CatalogueCategoryType> getAllCatalogueCategoryTypes() {
        log.debug("REST request to get all CatalogueCategoryTypes");
        return catalogueCategoryTypeRepository.findAll();
    }

    /**
     * {@code GET  /catalogue-category-types/:id} : get the "id" catalogueCategoryType.
     *
     * @param id the id of the catalogueCategoryType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catalogueCategoryType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/catalogue-category-types/{id}")
    public ResponseEntity<CatalogueCategoryType> getCatalogueCategoryType(@PathVariable Long id) {
        log.debug("REST request to get CatalogueCategoryType : {}", id);
        Optional<CatalogueCategoryType> catalogueCategoryType = catalogueCategoryTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(catalogueCategoryType);
    }

    /**
     * {@code DELETE  /catalogue-category-types/:id} : delete the "id" catalogueCategoryType.
     *
     * @param id the id of the catalogueCategoryType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/catalogue-category-types/{id}")
    public ResponseEntity<Void> deleteCatalogueCategoryType(@PathVariable Long id) {
        log.debug("REST request to delete CatalogueCategoryType : {}", id);
        catalogueCategoryTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
