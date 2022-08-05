package com.hr.web.rest;

import com.hr.domain.WorkEffortAssoc;
import com.hr.service.WorkEffortAssocService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.WorkEffortAssocCriteria;
import com.hr.service.WorkEffortAssocQueryService;

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
 * REST controller for managing {@link com.hr.domain.WorkEffortAssoc}.
 */
@RestController
@RequestMapping("/api")
public class WorkEffortAssocResource {

    private final Logger log = LoggerFactory.getLogger(WorkEffortAssocResource.class);

    private static final String ENTITY_NAME = "workEffortAssoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEffortAssocService workEffortAssocService;

    private final WorkEffortAssocQueryService workEffortAssocQueryService;

    public WorkEffortAssocResource(WorkEffortAssocService workEffortAssocService, WorkEffortAssocQueryService workEffortAssocQueryService) {
        this.workEffortAssocService = workEffortAssocService;
        this.workEffortAssocQueryService = workEffortAssocQueryService;
    }

    /**
     * {@code POST  /work-effort-assocs} : Create a new workEffortAssoc.
     *
     * @param workEffortAssoc the workEffortAssoc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEffortAssoc, or with status {@code 400 (Bad Request)} if the workEffortAssoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-effort-assocs")
    public ResponseEntity<WorkEffortAssoc> createWorkEffortAssoc(@RequestBody WorkEffortAssoc workEffortAssoc) throws URISyntaxException {
        log.debug("REST request to save WorkEffortAssoc : {}", workEffortAssoc);
        if (workEffortAssoc.getId() != null) {
            throw new BadRequestAlertException("A new workEffortAssoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEffortAssoc result = workEffortAssocService.save(workEffortAssoc);
        return ResponseEntity.created(new URI("/api/work-effort-assocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-effort-assocs} : Updates an existing workEffortAssoc.
     *
     * @param workEffortAssoc the workEffortAssoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEffortAssoc,
     * or with status {@code 400 (Bad Request)} if the workEffortAssoc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEffortAssoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-effort-assocs")
    public ResponseEntity<WorkEffortAssoc> updateWorkEffortAssoc(@RequestBody WorkEffortAssoc workEffortAssoc) throws URISyntaxException {
        log.debug("REST request to update WorkEffortAssoc : {}", workEffortAssoc);
        if (workEffortAssoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkEffortAssoc result = workEffortAssocService.save(workEffortAssoc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEffortAssoc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-effort-assocs} : get all the workEffortAssocs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEffortAssocs in body.
     */
    @GetMapping("/work-effort-assocs")
    public ResponseEntity<List<WorkEffortAssoc>> getAllWorkEffortAssocs(WorkEffortAssocCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WorkEffortAssocs by criteria: {}", criteria);
        Page<WorkEffortAssoc> page = workEffortAssocQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-effort-assocs/count} : count all the workEffortAssocs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-effort-assocs/count")
    public ResponseEntity<Long> countWorkEffortAssocs(WorkEffortAssocCriteria criteria) {
        log.debug("REST request to count WorkEffortAssocs by criteria: {}", criteria);
        return ResponseEntity.ok().body(workEffortAssocQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-effort-assocs/:id} : get the "id" workEffortAssoc.
     *
     * @param id the id of the workEffortAssoc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEffortAssoc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-effort-assocs/{id}")
    public ResponseEntity<WorkEffortAssoc> getWorkEffortAssoc(@PathVariable Long id) {
        log.debug("REST request to get WorkEffortAssoc : {}", id);
        Optional<WorkEffortAssoc> workEffortAssoc = workEffortAssocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workEffortAssoc);
    }

    /**
     * {@code DELETE  /work-effort-assocs/:id} : delete the "id" workEffortAssoc.
     *
     * @param id the id of the workEffortAssoc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-effort-assocs/{id}")
    public ResponseEntity<Void> deleteWorkEffortAssoc(@PathVariable Long id) {
        log.debug("REST request to delete WorkEffortAssoc : {}", id);
        workEffortAssocService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
