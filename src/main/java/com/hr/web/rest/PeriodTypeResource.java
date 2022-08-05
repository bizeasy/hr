package com.hr.web.rest;

import com.hr.domain.PeriodType;
import com.hr.repository.PeriodTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.PeriodType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PeriodTypeResource {

    private final Logger log = LoggerFactory.getLogger(PeriodTypeResource.class);

    private static final String ENTITY_NAME = "periodType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodTypeRepository periodTypeRepository;

    public PeriodTypeResource(PeriodTypeRepository periodTypeRepository) {
        this.periodTypeRepository = periodTypeRepository;
    }

    /**
     * {@code POST  /period-types} : Create a new periodType.
     *
     * @param periodType the periodType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodType, or with status {@code 400 (Bad Request)} if the periodType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/period-types")
    public ResponseEntity<PeriodType> createPeriodType(@Valid @RequestBody PeriodType periodType) throws URISyntaxException {
        log.debug("REST request to save PeriodType : {}", periodType);
        if (periodType.getId() != null) {
            throw new BadRequestAlertException("A new periodType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodType result = periodTypeRepository.save(periodType);
        return ResponseEntity.created(new URI("/api/period-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /period-types} : Updates an existing periodType.
     *
     * @param periodType the periodType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodType,
     * or with status {@code 400 (Bad Request)} if the periodType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/period-types")
    public ResponseEntity<PeriodType> updatePeriodType(@Valid @RequestBody PeriodType periodType) throws URISyntaxException {
        log.debug("REST request to update PeriodType : {}", periodType);
        if (periodType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodType result = periodTypeRepository.save(periodType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /period-types} : get all the periodTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodTypes in body.
     */
    @GetMapping("/period-types")
    public List<PeriodType> getAllPeriodTypes() {
        log.debug("REST request to get all PeriodTypes");
        return periodTypeRepository.findAll();
    }

    /**
     * {@code GET  /period-types/:id} : get the "id" periodType.
     *
     * @param id the id of the periodType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/period-types/{id}")
    public ResponseEntity<PeriodType> getPeriodType(@PathVariable Long id) {
        log.debug("REST request to get PeriodType : {}", id);
        Optional<PeriodType> periodType = periodTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(periodType);
    }

    /**
     * {@code DELETE  /period-types/:id} : delete the "id" periodType.
     *
     * @param id the id of the periodType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/period-types/{id}")
    public ResponseEntity<Void> deletePeriodType(@PathVariable Long id) {
        log.debug("REST request to delete PeriodType : {}", id);
        periodTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
