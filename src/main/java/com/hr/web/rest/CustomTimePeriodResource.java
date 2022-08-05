package com.hr.web.rest;

import com.hr.domain.CustomTimePeriod;
import com.hr.repository.CustomTimePeriodRepository;
import com.hr.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.CustomTimePeriod}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomTimePeriodResource {

    private final Logger log = LoggerFactory.getLogger(CustomTimePeriodResource.class);

    private static final String ENTITY_NAME = "customTimePeriod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomTimePeriodRepository customTimePeriodRepository;

    public CustomTimePeriodResource(CustomTimePeriodRepository customTimePeriodRepository) {
        this.customTimePeriodRepository = customTimePeriodRepository;
    }

    /**
     * {@code POST  /custom-time-periods} : Create a new customTimePeriod.
     *
     * @param customTimePeriod the customTimePeriod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customTimePeriod, or with status {@code 400 (Bad Request)} if the customTimePeriod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-time-periods")
    public ResponseEntity<CustomTimePeriod> createCustomTimePeriod(@RequestBody CustomTimePeriod customTimePeriod) throws URISyntaxException {
        log.debug("REST request to save CustomTimePeriod : {}", customTimePeriod);
        if (customTimePeriod.getId() != null) {
            throw new BadRequestAlertException("A new customTimePeriod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomTimePeriod result = customTimePeriodRepository.save(customTimePeriod);
        return ResponseEntity.created(new URI("/api/custom-time-periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-time-periods} : Updates an existing customTimePeriod.
     *
     * @param customTimePeriod the customTimePeriod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customTimePeriod,
     * or with status {@code 400 (Bad Request)} if the customTimePeriod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customTimePeriod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-time-periods")
    public ResponseEntity<CustomTimePeriod> updateCustomTimePeriod(@RequestBody CustomTimePeriod customTimePeriod) throws URISyntaxException {
        log.debug("REST request to update CustomTimePeriod : {}", customTimePeriod);
        if (customTimePeriod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomTimePeriod result = customTimePeriodRepository.save(customTimePeriod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customTimePeriod.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /custom-time-periods} : get all the customTimePeriods.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customTimePeriods in body.
     */
    @GetMapping("/custom-time-periods")
    public List<CustomTimePeriod> getAllCustomTimePeriods() {
        log.debug("REST request to get all CustomTimePeriods");
        return customTimePeriodRepository.findAll();
    }

    /**
     * {@code GET  /custom-time-periods/:id} : get the "id" customTimePeriod.
     *
     * @param id the id of the customTimePeriod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customTimePeriod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-time-periods/{id}")
    public ResponseEntity<CustomTimePeriod> getCustomTimePeriod(@PathVariable Long id) {
        log.debug("REST request to get CustomTimePeriod : {}", id);
        Optional<CustomTimePeriod> customTimePeriod = customTimePeriodRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customTimePeriod);
    }

    /**
     * {@code DELETE  /custom-time-periods/:id} : delete the "id" customTimePeriod.
     *
     * @param id the id of the customTimePeriod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-time-periods/{id}")
    public ResponseEntity<Void> deleteCustomTimePeriod(@PathVariable Long id) {
        log.debug("REST request to delete CustomTimePeriod : {}", id);
        customTimePeriodRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
