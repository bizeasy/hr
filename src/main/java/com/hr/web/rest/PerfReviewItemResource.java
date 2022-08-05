package com.hr.web.rest;

import com.hr.domain.PerfReviewItem;
import com.hr.repository.PerfReviewItemRepository;
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
 * REST controller for managing {@link com.hr.domain.PerfReviewItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PerfReviewItemResource {

    private final Logger log = LoggerFactory.getLogger(PerfReviewItemResource.class);

    private static final String ENTITY_NAME = "perfReviewItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfReviewItemRepository perfReviewItemRepository;

    public PerfReviewItemResource(PerfReviewItemRepository perfReviewItemRepository) {
        this.perfReviewItemRepository = perfReviewItemRepository;
    }

    /**
     * {@code POST  /perf-review-items} : Create a new perfReviewItem.
     *
     * @param perfReviewItem the perfReviewItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfReviewItem, or with status {@code 400 (Bad Request)} if the perfReviewItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perf-review-items")
    public ResponseEntity<PerfReviewItem> createPerfReviewItem(@RequestBody PerfReviewItem perfReviewItem) throws URISyntaxException {
        log.debug("REST request to save PerfReviewItem : {}", perfReviewItem);
        if (perfReviewItem.getId() != null) {
            throw new BadRequestAlertException("A new perfReviewItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfReviewItem result = perfReviewItemRepository.save(perfReviewItem);
        return ResponseEntity.created(new URI("/api/perf-review-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perf-review-items} : Updates an existing perfReviewItem.
     *
     * @param perfReviewItem the perfReviewItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfReviewItem,
     * or with status {@code 400 (Bad Request)} if the perfReviewItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfReviewItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perf-review-items")
    public ResponseEntity<PerfReviewItem> updatePerfReviewItem(@RequestBody PerfReviewItem perfReviewItem) throws URISyntaxException {
        log.debug("REST request to update PerfReviewItem : {}", perfReviewItem);
        if (perfReviewItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerfReviewItem result = perfReviewItemRepository.save(perfReviewItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfReviewItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perf-review-items} : get all the perfReviewItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfReviewItems in body.
     */
    @GetMapping("/perf-review-items")
    public List<PerfReviewItem> getAllPerfReviewItems() {
        log.debug("REST request to get all PerfReviewItems");
        return perfReviewItemRepository.findAll();
    }

    /**
     * {@code GET  /perf-review-items/:id} : get the "id" perfReviewItem.
     *
     * @param id the id of the perfReviewItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfReviewItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perf-review-items/{id}")
    public ResponseEntity<PerfReviewItem> getPerfReviewItem(@PathVariable Long id) {
        log.debug("REST request to get PerfReviewItem : {}", id);
        Optional<PerfReviewItem> perfReviewItem = perfReviewItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(perfReviewItem);
    }

    /**
     * {@code DELETE  /perf-review-items/:id} : delete the "id" perfReviewItem.
     *
     * @param id the id of the perfReviewItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perf-review-items/{id}")
    public ResponseEntity<Void> deletePerfReviewItem(@PathVariable Long id) {
        log.debug("REST request to delete PerfReviewItem : {}", id);
        perfReviewItemRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
