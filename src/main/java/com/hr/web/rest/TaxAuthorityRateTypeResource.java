package com.hr.web.rest;

import com.hr.domain.TaxAuthorityRateType;
import com.hr.repository.TaxAuthorityRateTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.TaxAuthorityRateType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaxAuthorityRateTypeResource {

    private final Logger log = LoggerFactory.getLogger(TaxAuthorityRateTypeResource.class);

    private static final String ENTITY_NAME = "taxAuthorityRateType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxAuthorityRateTypeRepository taxAuthorityRateTypeRepository;

    public TaxAuthorityRateTypeResource(TaxAuthorityRateTypeRepository taxAuthorityRateTypeRepository) {
        this.taxAuthorityRateTypeRepository = taxAuthorityRateTypeRepository;
    }

    /**
     * {@code POST  /tax-authority-rate-types} : Create a new taxAuthorityRateType.
     *
     * @param taxAuthorityRateType the taxAuthorityRateType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxAuthorityRateType, or with status {@code 400 (Bad Request)} if the taxAuthorityRateType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-authority-rate-types")
    public ResponseEntity<TaxAuthorityRateType> createTaxAuthorityRateType(@Valid @RequestBody TaxAuthorityRateType taxAuthorityRateType) throws URISyntaxException {
        log.debug("REST request to save TaxAuthorityRateType : {}", taxAuthorityRateType);
        if (taxAuthorityRateType.getId() != null) {
            throw new BadRequestAlertException("A new taxAuthorityRateType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxAuthorityRateType result = taxAuthorityRateTypeRepository.save(taxAuthorityRateType);
        return ResponseEntity.created(new URI("/api/tax-authority-rate-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-authority-rate-types} : Updates an existing taxAuthorityRateType.
     *
     * @param taxAuthorityRateType the taxAuthorityRateType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxAuthorityRateType,
     * or with status {@code 400 (Bad Request)} if the taxAuthorityRateType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxAuthorityRateType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-authority-rate-types")
    public ResponseEntity<TaxAuthorityRateType> updateTaxAuthorityRateType(@Valid @RequestBody TaxAuthorityRateType taxAuthorityRateType) throws URISyntaxException {
        log.debug("REST request to update TaxAuthorityRateType : {}", taxAuthorityRateType);
        if (taxAuthorityRateType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxAuthorityRateType result = taxAuthorityRateTypeRepository.save(taxAuthorityRateType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxAuthorityRateType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tax-authority-rate-types} : get all the taxAuthorityRateTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxAuthorityRateTypes in body.
     */
    @GetMapping("/tax-authority-rate-types")
    public List<TaxAuthorityRateType> getAllTaxAuthorityRateTypes() {
        log.debug("REST request to get all TaxAuthorityRateTypes");
        return taxAuthorityRateTypeRepository.findAll();
    }

    /**
     * {@code GET  /tax-authority-rate-types/:id} : get the "id" taxAuthorityRateType.
     *
     * @param id the id of the taxAuthorityRateType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxAuthorityRateType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-authority-rate-types/{id}")
    public ResponseEntity<TaxAuthorityRateType> getTaxAuthorityRateType(@PathVariable Long id) {
        log.debug("REST request to get TaxAuthorityRateType : {}", id);
        Optional<TaxAuthorityRateType> taxAuthorityRateType = taxAuthorityRateTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxAuthorityRateType);
    }

    /**
     * {@code DELETE  /tax-authority-rate-types/:id} : delete the "id" taxAuthorityRateType.
     *
     * @param id the id of the taxAuthorityRateType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-authority-rate-types/{id}")
    public ResponseEntity<Void> deleteTaxAuthorityRateType(@PathVariable Long id) {
        log.debug("REST request to delete TaxAuthorityRateType : {}", id);
        taxAuthorityRateTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
