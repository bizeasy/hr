package com.hr.web.rest;

import com.hr.domain.WorkEffortInventoryProduced;
import com.hr.service.WorkEffortInventoryProducedService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.WorkEffortInventoryProducedCriteria;
import com.hr.service.WorkEffortInventoryProducedQueryService;

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
 * REST controller for managing {@link com.hr.domain.WorkEffortInventoryProduced}.
 */
@RestController
@RequestMapping("/api")
public class WorkEffortInventoryProducedResource {

    private final Logger log = LoggerFactory.getLogger(WorkEffortInventoryProducedResource.class);

    private static final String ENTITY_NAME = "workEffortInventoryProduced";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEffortInventoryProducedService workEffortInventoryProducedService;

    private final WorkEffortInventoryProducedQueryService workEffortInventoryProducedQueryService;

    public WorkEffortInventoryProducedResource(WorkEffortInventoryProducedService workEffortInventoryProducedService, WorkEffortInventoryProducedQueryService workEffortInventoryProducedQueryService) {
        this.workEffortInventoryProducedService = workEffortInventoryProducedService;
        this.workEffortInventoryProducedQueryService = workEffortInventoryProducedQueryService;
    }

    /**
     * {@code POST  /work-effort-inventory-produceds} : Create a new workEffortInventoryProduced.
     *
     * @param workEffortInventoryProduced the workEffortInventoryProduced to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEffortInventoryProduced, or with status {@code 400 (Bad Request)} if the workEffortInventoryProduced has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-effort-inventory-produceds")
    public ResponseEntity<WorkEffortInventoryProduced> createWorkEffortInventoryProduced(@RequestBody WorkEffortInventoryProduced workEffortInventoryProduced) throws URISyntaxException {
        log.debug("REST request to save WorkEffortInventoryProduced : {}", workEffortInventoryProduced);
        if (workEffortInventoryProduced.getId() != null) {
            throw new BadRequestAlertException("A new workEffortInventoryProduced cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEffortInventoryProduced result = workEffortInventoryProducedService.save(workEffortInventoryProduced);
        return ResponseEntity.created(new URI("/api/work-effort-inventory-produceds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-effort-inventory-produceds} : Updates an existing workEffortInventoryProduced.
     *
     * @param workEffortInventoryProduced the workEffortInventoryProduced to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEffortInventoryProduced,
     * or with status {@code 400 (Bad Request)} if the workEffortInventoryProduced is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEffortInventoryProduced couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-effort-inventory-produceds")
    public ResponseEntity<WorkEffortInventoryProduced> updateWorkEffortInventoryProduced(@RequestBody WorkEffortInventoryProduced workEffortInventoryProduced) throws URISyntaxException {
        log.debug("REST request to update WorkEffortInventoryProduced : {}", workEffortInventoryProduced);
        if (workEffortInventoryProduced.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkEffortInventoryProduced result = workEffortInventoryProducedService.save(workEffortInventoryProduced);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEffortInventoryProduced.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-effort-inventory-produceds} : get all the workEffortInventoryProduceds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEffortInventoryProduceds in body.
     */
    @GetMapping("/work-effort-inventory-produceds")
    public ResponseEntity<List<WorkEffortInventoryProduced>> getAllWorkEffortInventoryProduceds(WorkEffortInventoryProducedCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WorkEffortInventoryProduceds by criteria: {}", criteria);
        Page<WorkEffortInventoryProduced> page = workEffortInventoryProducedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-effort-inventory-produceds/count} : count all the workEffortInventoryProduceds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-effort-inventory-produceds/count")
    public ResponseEntity<Long> countWorkEffortInventoryProduceds(WorkEffortInventoryProducedCriteria criteria) {
        log.debug("REST request to count WorkEffortInventoryProduceds by criteria: {}", criteria);
        return ResponseEntity.ok().body(workEffortInventoryProducedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-effort-inventory-produceds/:id} : get the "id" workEffortInventoryProduced.
     *
     * @param id the id of the workEffortInventoryProduced to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEffortInventoryProduced, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-effort-inventory-produceds/{id}")
    public ResponseEntity<WorkEffortInventoryProduced> getWorkEffortInventoryProduced(@PathVariable Long id) {
        log.debug("REST request to get WorkEffortInventoryProduced : {}", id);
        Optional<WorkEffortInventoryProduced> workEffortInventoryProduced = workEffortInventoryProducedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workEffortInventoryProduced);
    }

    /**
     * {@code DELETE  /work-effort-inventory-produceds/:id} : delete the "id" workEffortInventoryProduced.
     *
     * @param id the id of the workEffortInventoryProduced to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-effort-inventory-produceds/{id}")
    public ResponseEntity<Void> deleteWorkEffortInventoryProduced(@PathVariable Long id) {
        log.debug("REST request to delete WorkEffortInventoryProduced : {}", id);
        workEffortInventoryProducedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
