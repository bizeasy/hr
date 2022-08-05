package com.hr.web.rest;

import com.hr.domain.PerfRatingType;
import com.hr.repository.PerfRatingTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.PerfRatingType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PerfRatingTypeResource {

    private final Logger log = LoggerFactory.getLogger(PerfRatingTypeResource.class);

    private static final String ENTITY_NAME = "perfRatingType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfRatingTypeRepository perfRatingTypeRepository;

    public PerfRatingTypeResource(PerfRatingTypeRepository perfRatingTypeRepository) {
        this.perfRatingTypeRepository = perfRatingTypeRepository;
    }

    /**
     * {@code POST  /perf-rating-types} : Create a new perfRatingType.
     *
     * @param perfRatingType the perfRatingType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfRatingType, or with status {@code 400 (Bad Request)} if the perfRatingType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perf-rating-types")
    public ResponseEntity<PerfRatingType> createPerfRatingType(@Valid @RequestBody PerfRatingType perfRatingType) throws URISyntaxException {
        log.debug("REST request to save PerfRatingType : {}", perfRatingType);
        if (perfRatingType.getId() != null) {
            throw new BadRequestAlertException("A new perfRatingType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfRatingType result = perfRatingTypeRepository.save(perfRatingType);
        return ResponseEntity.created(new URI("/api/perf-rating-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perf-rating-types} : Updates an existing perfRatingType.
     *
     * @param perfRatingType the perfRatingType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfRatingType,
     * or with status {@code 400 (Bad Request)} if the perfRatingType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfRatingType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perf-rating-types")
    public ResponseEntity<PerfRatingType> updatePerfRatingType(@Valid @RequestBody PerfRatingType perfRatingType) throws URISyntaxException {
        log.debug("REST request to update PerfRatingType : {}", perfRatingType);
        if (perfRatingType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerfRatingType result = perfRatingTypeRepository.save(perfRatingType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfRatingType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perf-rating-types} : get all the perfRatingTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfRatingTypes in body.
     */
    @GetMapping("/perf-rating-types")
    public List<PerfRatingType> getAllPerfRatingTypes() {
        log.debug("REST request to get all PerfRatingTypes");
        return perfRatingTypeRepository.findAll();
    }

    /**
     * {@code GET  /perf-rating-types/:id} : get the "id" perfRatingType.
     *
     * @param id the id of the perfRatingType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfRatingType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perf-rating-types/{id}")
    public ResponseEntity<PerfRatingType> getPerfRatingType(@PathVariable Long id) {
        log.debug("REST request to get PerfRatingType : {}", id);
        Optional<PerfRatingType> perfRatingType = perfRatingTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(perfRatingType);
    }

    /**
     * {@code DELETE  /perf-rating-types/:id} : delete the "id" perfRatingType.
     *
     * @param id the id of the perfRatingType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perf-rating-types/{id}")
    public ResponseEntity<Void> deletePerfRatingType(@PathVariable Long id) {
        log.debug("REST request to delete PerfRatingType : {}", id);
        perfRatingTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
