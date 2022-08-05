package com.hr.web.rest;

import com.hr.domain.PerfReview;
import com.hr.repository.PerfReviewRepository;
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
 * REST controller for managing {@link com.hr.domain.PerfReview}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PerfReviewResource {

    private final Logger log = LoggerFactory.getLogger(PerfReviewResource.class);

    private static final String ENTITY_NAME = "perfReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfReviewRepository perfReviewRepository;

    public PerfReviewResource(PerfReviewRepository perfReviewRepository) {
        this.perfReviewRepository = perfReviewRepository;
    }

    /**
     * {@code POST  /perf-reviews} : Create a new perfReview.
     *
     * @param perfReview the perfReview to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfReview, or with status {@code 400 (Bad Request)} if the perfReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perf-reviews")
    public ResponseEntity<PerfReview> createPerfReview(@RequestBody PerfReview perfReview) throws URISyntaxException {
        log.debug("REST request to save PerfReview : {}", perfReview);
        if (perfReview.getId() != null) {
            throw new BadRequestAlertException("A new perfReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfReview result = perfReviewRepository.save(perfReview);
        return ResponseEntity.created(new URI("/api/perf-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perf-reviews} : Updates an existing perfReview.
     *
     * @param perfReview the perfReview to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfReview,
     * or with status {@code 400 (Bad Request)} if the perfReview is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfReview couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perf-reviews")
    public ResponseEntity<PerfReview> updatePerfReview(@RequestBody PerfReview perfReview) throws URISyntaxException {
        log.debug("REST request to update PerfReview : {}", perfReview);
        if (perfReview.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerfReview result = perfReviewRepository.save(perfReview);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfReview.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perf-reviews} : get all the perfReviews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfReviews in body.
     */
    @GetMapping("/perf-reviews")
    public List<PerfReview> getAllPerfReviews() {
        log.debug("REST request to get all PerfReviews");
        return perfReviewRepository.findAll();
    }

    /**
     * {@code GET  /perf-reviews/:id} : get the "id" perfReview.
     *
     * @param id the id of the perfReview to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfReview, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perf-reviews/{id}")
    public ResponseEntity<PerfReview> getPerfReview(@PathVariable Long id) {
        log.debug("REST request to get PerfReview : {}", id);
        Optional<PerfReview> perfReview = perfReviewRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(perfReview);
    }

    /**
     * {@code DELETE  /perf-reviews/:id} : delete the "id" perfReview.
     *
     * @param id the id of the perfReview to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perf-reviews/{id}")
    public ResponseEntity<Void> deletePerfReview(@PathVariable Long id) {
        log.debug("REST request to delete PerfReview : {}", id);
        perfReviewRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
