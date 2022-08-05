package com.hr.web.rest;

import com.hr.domain.WorkEffortType;
import com.hr.repository.WorkEffortTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.WorkEffortType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkEffortTypeResource {

    private final Logger log = LoggerFactory.getLogger(WorkEffortTypeResource.class);

    private static final String ENTITY_NAME = "workEffortType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEffortTypeRepository workEffortTypeRepository;

    public WorkEffortTypeResource(WorkEffortTypeRepository workEffortTypeRepository) {
        this.workEffortTypeRepository = workEffortTypeRepository;
    }

    /**
     * {@code POST  /work-effort-types} : Create a new workEffortType.
     *
     * @param workEffortType the workEffortType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEffortType, or with status {@code 400 (Bad Request)} if the workEffortType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-effort-types")
    public ResponseEntity<WorkEffortType> createWorkEffortType(@Valid @RequestBody WorkEffortType workEffortType) throws URISyntaxException {
        log.debug("REST request to save WorkEffortType : {}", workEffortType);
        if (workEffortType.getId() != null) {
            throw new BadRequestAlertException("A new workEffortType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEffortType result = workEffortTypeRepository.save(workEffortType);
        return ResponseEntity.created(new URI("/api/work-effort-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-effort-types} : Updates an existing workEffortType.
     *
     * @param workEffortType the workEffortType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEffortType,
     * or with status {@code 400 (Bad Request)} if the workEffortType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEffortType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-effort-types")
    public ResponseEntity<WorkEffortType> updateWorkEffortType(@Valid @RequestBody WorkEffortType workEffortType) throws URISyntaxException {
        log.debug("REST request to update WorkEffortType : {}", workEffortType);
        if (workEffortType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkEffortType result = workEffortTypeRepository.save(workEffortType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEffortType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-effort-types} : get all the workEffortTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEffortTypes in body.
     */
    @GetMapping("/work-effort-types")
    public List<WorkEffortType> getAllWorkEffortTypes() {
        log.debug("REST request to get all WorkEffortTypes");
        return workEffortTypeRepository.findAll();
    }

    /**
     * {@code GET  /work-effort-types/:id} : get the "id" workEffortType.
     *
     * @param id the id of the workEffortType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEffortType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-effort-types/{id}")
    public ResponseEntity<WorkEffortType> getWorkEffortType(@PathVariable Long id) {
        log.debug("REST request to get WorkEffortType : {}", id);
        Optional<WorkEffortType> workEffortType = workEffortTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workEffortType);
    }

    /**
     * {@code DELETE  /work-effort-types/:id} : delete the "id" workEffortType.
     *
     * @param id the id of the workEffortType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-effort-types/{id}")
    public ResponseEntity<Void> deleteWorkEffortType(@PathVariable Long id) {
        log.debug("REST request to delete WorkEffortType : {}", id);
        workEffortTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
