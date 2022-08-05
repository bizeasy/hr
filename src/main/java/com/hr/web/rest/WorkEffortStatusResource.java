package com.hr.web.rest;

import com.hr.domain.WorkEffortStatus;
import com.hr.service.WorkEffortStatusService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.WorkEffortStatusCriteria;
import com.hr.service.WorkEffortStatusQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.WorkEffortStatus}.
 */
@RestController
@RequestMapping("/api")
public class WorkEffortStatusResource {

    private final Logger log = LoggerFactory.getLogger(WorkEffortStatusResource.class);

    private static final String ENTITY_NAME = "workEffortStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEffortStatusService workEffortStatusService;

    private final WorkEffortStatusQueryService workEffortStatusQueryService;

    public WorkEffortStatusResource(WorkEffortStatusService workEffortStatusService, WorkEffortStatusQueryService workEffortStatusQueryService) {
        this.workEffortStatusService = workEffortStatusService;
        this.workEffortStatusQueryService = workEffortStatusQueryService;
    }

    /**
     * {@code POST  /work-effort-statuses} : Create a new workEffortStatus.
     *
     * @param workEffortStatus the workEffortStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEffortStatus, or with status {@code 400 (Bad Request)} if the workEffortStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-effort-statuses")
    public ResponseEntity<WorkEffortStatus> createWorkEffortStatus(@RequestBody WorkEffortStatus workEffortStatus) throws URISyntaxException {
        log.debug("REST request to save WorkEffortStatus : {}", workEffortStatus);
        if (workEffortStatus.getId() != null) {
            throw new BadRequestAlertException("A new workEffortStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEffortStatus result = workEffortStatusService.save(workEffortStatus);
        return ResponseEntity.created(new URI("/api/work-effort-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-effort-statuses} : Updates an existing workEffortStatus.
     *
     * @param workEffortStatus the workEffortStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEffortStatus,
     * or with status {@code 400 (Bad Request)} if the workEffortStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEffortStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-effort-statuses")
    public ResponseEntity<WorkEffortStatus> updateWorkEffortStatus(@RequestBody WorkEffortStatus workEffortStatus) throws URISyntaxException {
        log.debug("REST request to update WorkEffortStatus : {}", workEffortStatus);
        if (workEffortStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkEffortStatus result = workEffortStatusService.save(workEffortStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEffortStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-effort-statuses} : get all the workEffortStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEffortStatuses in body.
     */
    @GetMapping("/work-effort-statuses")
    public ResponseEntity<List<WorkEffortStatus>> getAllWorkEffortStatuses(WorkEffortStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WorkEffortStatuses by criteria: {}", criteria);
        Page<WorkEffortStatus> page = workEffortStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-effort-statuses/count} : count all the workEffortStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-effort-statuses/count")
    public ResponseEntity<Long> countWorkEffortStatuses(WorkEffortStatusCriteria criteria) {
        log.debug("REST request to count WorkEffortStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(workEffortStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-effort-statuses/:id} : get the "id" workEffortStatus.
     *
     * @param id the id of the workEffortStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEffortStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-effort-statuses/{id}")
    public ResponseEntity<WorkEffortStatus> getWorkEffortStatus(@PathVariable Long id) {
        log.debug("REST request to get WorkEffortStatus : {}", id);
        Optional<WorkEffortStatus> workEffortStatus = workEffortStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workEffortStatus);
    }

    /**
     * {@code DELETE  /work-effort-statuses/:id} : delete the "id" workEffortStatus.
     *
     * @param id the id of the workEffortStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-effort-statuses/{id}")
    public ResponseEntity<Void> deleteWorkEffortStatus(@PathVariable Long id) {
        log.debug("REST request to delete WorkEffortStatus : {}", id);
        workEffortStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
