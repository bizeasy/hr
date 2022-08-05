package com.hr.web.rest;

import com.hr.domain.WorkEffort;
import com.hr.service.WorkEffortService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.WorkEffortCriteria;
import com.hr.service.WorkEffortQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.WorkEffort}.
 */
@RestController
@RequestMapping("/api")
public class WorkEffortResource {

    private final Logger log = LoggerFactory.getLogger(WorkEffortResource.class);

    private static final String ENTITY_NAME = "workEffort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEffortService workEffortService;

    private final WorkEffortQueryService workEffortQueryService;

    public WorkEffortResource(WorkEffortService workEffortService, WorkEffortQueryService workEffortQueryService) {
        this.workEffortService = workEffortService;
        this.workEffortQueryService = workEffortQueryService;
    }

    /**
     * {@code POST  /work-efforts} : Create a new workEffort.
     *
     * @param workEffort the workEffort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEffort, or with status {@code 400 (Bad Request)} if the workEffort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-efforts")
    public ResponseEntity<WorkEffort> createWorkEffort(@Valid @RequestBody WorkEffort workEffort) throws URISyntaxException {
        log.debug("REST request to save WorkEffort : {}", workEffort);
        if (workEffort.getId() != null) {
            throw new BadRequestAlertException("A new workEffort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEffort result = workEffortService.save(workEffort);
        return ResponseEntity.created(new URI("/api/work-efforts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-efforts} : Updates an existing workEffort.
     *
     * @param workEffort the workEffort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEffort,
     * or with status {@code 400 (Bad Request)} if the workEffort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEffort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-efforts")
    public ResponseEntity<WorkEffort> updateWorkEffort(@Valid @RequestBody WorkEffort workEffort) throws URISyntaxException {
        log.debug("REST request to update WorkEffort : {}", workEffort);
        if (workEffort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkEffort result = workEffortService.save(workEffort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEffort.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-efforts} : get all the workEfforts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEfforts in body.
     */
    @GetMapping("/work-efforts")
    public ResponseEntity<List<WorkEffort>> getAllWorkEfforts(WorkEffortCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WorkEfforts by criteria: {}", criteria);
        Page<WorkEffort> page = workEffortQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-efforts/count} : count all the workEfforts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-efforts/count")
    public ResponseEntity<Long> countWorkEfforts(WorkEffortCriteria criteria) {
        log.debug("REST request to count WorkEfforts by criteria: {}", criteria);
        return ResponseEntity.ok().body(workEffortQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-efforts/:id} : get the "id" workEffort.
     *
     * @param id the id of the workEffort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEffort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-efforts/{id}")
    public ResponseEntity<WorkEffort> getWorkEffort(@PathVariable Long id) {
        log.debug("REST request to get WorkEffort : {}", id);
        Optional<WorkEffort> workEffort = workEffortService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workEffort);
    }

    /**
     * {@code DELETE  /work-efforts/:id} : delete the "id" workEffort.
     *
     * @param id the id of the workEffort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-efforts/{id}")
    public ResponseEntity<Void> deleteWorkEffort(@PathVariable Long id) {
        log.debug("REST request to delete WorkEffort : {}", id);
        workEffortService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
