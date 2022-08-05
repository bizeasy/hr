package com.hr.web.rest;

import com.hr.domain.PerfReviewItemType;
import com.hr.repository.PerfReviewItemTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.PerfReviewItemType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PerfReviewItemTypeResource {

    private final Logger log = LoggerFactory.getLogger(PerfReviewItemTypeResource.class);

    private static final String ENTITY_NAME = "perfReviewItemType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfReviewItemTypeRepository perfReviewItemTypeRepository;

    public PerfReviewItemTypeResource(PerfReviewItemTypeRepository perfReviewItemTypeRepository) {
        this.perfReviewItemTypeRepository = perfReviewItemTypeRepository;
    }

    /**
     * {@code POST  /perf-review-item-types} : Create a new perfReviewItemType.
     *
     * @param perfReviewItemType the perfReviewItemType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfReviewItemType, or with status {@code 400 (Bad Request)} if the perfReviewItemType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perf-review-item-types")
    public ResponseEntity<PerfReviewItemType> createPerfReviewItemType(@Valid @RequestBody PerfReviewItemType perfReviewItemType) throws URISyntaxException {
        log.debug("REST request to save PerfReviewItemType : {}", perfReviewItemType);
        if (perfReviewItemType.getId() != null) {
            throw new BadRequestAlertException("A new perfReviewItemType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfReviewItemType result = perfReviewItemTypeRepository.save(perfReviewItemType);
        return ResponseEntity.created(new URI("/api/perf-review-item-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perf-review-item-types} : Updates an existing perfReviewItemType.
     *
     * @param perfReviewItemType the perfReviewItemType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfReviewItemType,
     * or with status {@code 400 (Bad Request)} if the perfReviewItemType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfReviewItemType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perf-review-item-types")
    public ResponseEntity<PerfReviewItemType> updatePerfReviewItemType(@Valid @RequestBody PerfReviewItemType perfReviewItemType) throws URISyntaxException {
        log.debug("REST request to update PerfReviewItemType : {}", perfReviewItemType);
        if (perfReviewItemType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerfReviewItemType result = perfReviewItemTypeRepository.save(perfReviewItemType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfReviewItemType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perf-review-item-types} : get all the perfReviewItemTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfReviewItemTypes in body.
     */
    @GetMapping("/perf-review-item-types")
    public List<PerfReviewItemType> getAllPerfReviewItemTypes() {
        log.debug("REST request to get all PerfReviewItemTypes");
        return perfReviewItemTypeRepository.findAll();
    }

    /**
     * {@code GET  /perf-review-item-types/:id} : get the "id" perfReviewItemType.
     *
     * @param id the id of the perfReviewItemType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfReviewItemType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perf-review-item-types/{id}")
    public ResponseEntity<PerfReviewItemType> getPerfReviewItemType(@PathVariable Long id) {
        log.debug("REST request to get PerfReviewItemType : {}", id);
        Optional<PerfReviewItemType> perfReviewItemType = perfReviewItemTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(perfReviewItemType);
    }

    /**
     * {@code DELETE  /perf-review-item-types/:id} : delete the "id" perfReviewItemType.
     *
     * @param id the id of the perfReviewItemType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perf-review-item-types/{id}")
    public ResponseEntity<Void> deletePerfReviewItemType(@PathVariable Long id) {
        log.debug("REST request to delete PerfReviewItemType : {}", id);
        perfReviewItemTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
