package com.hr.web.rest;

import com.hr.domain.UomType;
import com.hr.repository.UomTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.UomType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UomTypeResource {

    private final Logger log = LoggerFactory.getLogger(UomTypeResource.class);

    private static final String ENTITY_NAME = "uomType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UomTypeRepository uomTypeRepository;

    public UomTypeResource(UomTypeRepository uomTypeRepository) {
        this.uomTypeRepository = uomTypeRepository;
    }

    /**
     * {@code POST  /uom-types} : Create a new uomType.
     *
     * @param uomType the uomType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uomType, or with status {@code 400 (Bad Request)} if the uomType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uom-types")
    public ResponseEntity<UomType> createUomType(@Valid @RequestBody UomType uomType) throws URISyntaxException {
        log.debug("REST request to save UomType : {}", uomType);
        if (uomType.getId() != null) {
            throw new BadRequestAlertException("A new uomType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UomType result = uomTypeRepository.save(uomType);
        return ResponseEntity.created(new URI("/api/uom-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uom-types} : Updates an existing uomType.
     *
     * @param uomType the uomType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uomType,
     * or with status {@code 400 (Bad Request)} if the uomType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uomType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uom-types")
    public ResponseEntity<UomType> updateUomType(@Valid @RequestBody UomType uomType) throws URISyntaxException {
        log.debug("REST request to update UomType : {}", uomType);
        if (uomType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UomType result = uomTypeRepository.save(uomType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uomType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uom-types} : get all the uomTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uomTypes in body.
     */
    @GetMapping("/uom-types")
    public List<UomType> getAllUomTypes() {
        log.debug("REST request to get all UomTypes");
        return uomTypeRepository.findAll();
    }

    /**
     * {@code GET  /uom-types/:id} : get the "id" uomType.
     *
     * @param id the id of the uomType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uomType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uom-types/{id}")
    public ResponseEntity<UomType> getUomType(@PathVariable Long id) {
        log.debug("REST request to get UomType : {}", id);
        Optional<UomType> uomType = uomTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uomType);
    }

    /**
     * {@code DELETE  /uom-types/:id} : delete the "id" uomType.
     *
     * @param id the id of the uomType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uom-types/{id}")
    public ResponseEntity<Void> deleteUomType(@PathVariable Long id) {
        log.debug("REST request to delete UomType : {}", id);
        uomTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
