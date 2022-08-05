package com.hr.web.rest;

import com.hr.domain.WorkEffortPurpose;
import com.hr.repository.WorkEffortPurposeRepository;
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
 * REST controller for managing {@link com.hr.domain.WorkEffortPurpose}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkEffortPurposeResource {

    private final Logger log = LoggerFactory.getLogger(WorkEffortPurposeResource.class);

    private static final String ENTITY_NAME = "workEffortPurpose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEffortPurposeRepository workEffortPurposeRepository;

    public WorkEffortPurposeResource(WorkEffortPurposeRepository workEffortPurposeRepository) {
        this.workEffortPurposeRepository = workEffortPurposeRepository;
    }

    /**
     * {@code POST  /work-effort-purposes} : Create a new workEffortPurpose.
     *
     * @param workEffortPurpose the workEffortPurpose to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEffortPurpose, or with status {@code 400 (Bad Request)} if the workEffortPurpose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-effort-purposes")
    public ResponseEntity<WorkEffortPurpose> createWorkEffortPurpose(@Valid @RequestBody WorkEffortPurpose workEffortPurpose) throws URISyntaxException {
        log.debug("REST request to save WorkEffortPurpose : {}", workEffortPurpose);
        if (workEffortPurpose.getId() != null) {
            throw new BadRequestAlertException("A new workEffortPurpose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEffortPurpose result = workEffortPurposeRepository.save(workEffortPurpose);
        return ResponseEntity.created(new URI("/api/work-effort-purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-effort-purposes} : Updates an existing workEffortPurpose.
     *
     * @param workEffortPurpose the workEffortPurpose to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEffortPurpose,
     * or with status {@code 400 (Bad Request)} if the workEffortPurpose is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEffortPurpose couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-effort-purposes")
    public ResponseEntity<WorkEffortPurpose> updateWorkEffortPurpose(@Valid @RequestBody WorkEffortPurpose workEffortPurpose) throws URISyntaxException {
        log.debug("REST request to update WorkEffortPurpose : {}", workEffortPurpose);
        if (workEffortPurpose.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkEffortPurpose result = workEffortPurposeRepository.save(workEffortPurpose);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEffortPurpose.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-effort-purposes} : get all the workEffortPurposes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEffortPurposes in body.
     */
    @GetMapping("/work-effort-purposes")
    public List<WorkEffortPurpose> getAllWorkEffortPurposes() {
        log.debug("REST request to get all WorkEffortPurposes");
        return workEffortPurposeRepository.findAll();
    }

    /**
     * {@code GET  /work-effort-purposes/:id} : get the "id" workEffortPurpose.
     *
     * @param id the id of the workEffortPurpose to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEffortPurpose, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-effort-purposes/{id}")
    public ResponseEntity<WorkEffortPurpose> getWorkEffortPurpose(@PathVariable Long id) {
        log.debug("REST request to get WorkEffortPurpose : {}", id);
        Optional<WorkEffortPurpose> workEffortPurpose = workEffortPurposeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workEffortPurpose);
    }

    /**
     * {@code DELETE  /work-effort-purposes/:id} : delete the "id" workEffortPurpose.
     *
     * @param id the id of the workEffortPurpose to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-effort-purposes/{id}")
    public ResponseEntity<Void> deleteWorkEffortPurpose(@PathVariable Long id) {
        log.debug("REST request to delete WorkEffortPurpose : {}", id);
        workEffortPurposeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
