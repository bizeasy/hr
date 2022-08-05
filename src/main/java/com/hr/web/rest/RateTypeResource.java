package com.hr.web.rest;

import com.hr.domain.RateType;
import com.hr.repository.RateTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.RateType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RateTypeResource {

    private final Logger log = LoggerFactory.getLogger(RateTypeResource.class);

    private static final String ENTITY_NAME = "rateType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RateTypeRepository rateTypeRepository;

    public RateTypeResource(RateTypeRepository rateTypeRepository) {
        this.rateTypeRepository = rateTypeRepository;
    }

    /**
     * {@code POST  /rate-types} : Create a new rateType.
     *
     * @param rateType the rateType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rateType, or with status {@code 400 (Bad Request)} if the rateType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rate-types")
    public ResponseEntity<RateType> createRateType(@Valid @RequestBody RateType rateType) throws URISyntaxException {
        log.debug("REST request to save RateType : {}", rateType);
        if (rateType.getId() != null) {
            throw new BadRequestAlertException("A new rateType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RateType result = rateTypeRepository.save(rateType);
        return ResponseEntity.created(new URI("/api/rate-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rate-types} : Updates an existing rateType.
     *
     * @param rateType the rateType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rateType,
     * or with status {@code 400 (Bad Request)} if the rateType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rateType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rate-types")
    public ResponseEntity<RateType> updateRateType(@Valid @RequestBody RateType rateType) throws URISyntaxException {
        log.debug("REST request to update RateType : {}", rateType);
        if (rateType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RateType result = rateTypeRepository.save(rateType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rateType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rate-types} : get all the rateTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rateTypes in body.
     */
    @GetMapping("/rate-types")
    public List<RateType> getAllRateTypes() {
        log.debug("REST request to get all RateTypes");
        return rateTypeRepository.findAll();
    }

    /**
     * {@code GET  /rate-types/:id} : get the "id" rateType.
     *
     * @param id the id of the rateType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rateType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rate-types/{id}")
    public ResponseEntity<RateType> getRateType(@PathVariable Long id) {
        log.debug("REST request to get RateType : {}", id);
        Optional<RateType> rateType = rateTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rateType);
    }

    /**
     * {@code DELETE  /rate-types/:id} : delete the "id" rateType.
     *
     * @param id the id of the rateType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rate-types/{id}")
    public ResponseEntity<Void> deleteRateType(@PathVariable Long id) {
        log.debug("REST request to delete RateType : {}", id);
        rateTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
