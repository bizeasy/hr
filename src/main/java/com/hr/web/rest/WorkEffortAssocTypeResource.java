package com.hr.web.rest;

import com.hr.domain.WorkEffortAssocType;
import com.hr.repository.WorkEffortAssocTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.WorkEffortAssocType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkEffortAssocTypeResource {

    private final Logger log = LoggerFactory.getLogger(WorkEffortAssocTypeResource.class);

    private static final String ENTITY_NAME = "workEffortAssocType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEffortAssocTypeRepository workEffortAssocTypeRepository;

    public WorkEffortAssocTypeResource(WorkEffortAssocTypeRepository workEffortAssocTypeRepository) {
        this.workEffortAssocTypeRepository = workEffortAssocTypeRepository;
    }

    /**
     * {@code POST  /work-effort-assoc-types} : Create a new workEffortAssocType.
     *
     * @param workEffortAssocType the workEffortAssocType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEffortAssocType, or with status {@code 400 (Bad Request)} if the workEffortAssocType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-effort-assoc-types")
    public ResponseEntity<WorkEffortAssocType> createWorkEffortAssocType(@Valid @RequestBody WorkEffortAssocType workEffortAssocType) throws URISyntaxException {
        log.debug("REST request to save WorkEffortAssocType : {}", workEffortAssocType);
        if (workEffortAssocType.getId() != null) {
            throw new BadRequestAlertException("A new workEffortAssocType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEffortAssocType result = workEffortAssocTypeRepository.save(workEffortAssocType);
        return ResponseEntity.created(new URI("/api/work-effort-assoc-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-effort-assoc-types} : Updates an existing workEffortAssocType.
     *
     * @param workEffortAssocType the workEffortAssocType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEffortAssocType,
     * or with status {@code 400 (Bad Request)} if the workEffortAssocType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEffortAssocType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-effort-assoc-types")
    public ResponseEntity<WorkEffortAssocType> updateWorkEffortAssocType(@Valid @RequestBody WorkEffortAssocType workEffortAssocType) throws URISyntaxException {
        log.debug("REST request to update WorkEffortAssocType : {}", workEffortAssocType);
        if (workEffortAssocType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkEffortAssocType result = workEffortAssocTypeRepository.save(workEffortAssocType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEffortAssocType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-effort-assoc-types} : get all the workEffortAssocTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEffortAssocTypes in body.
     */
    @GetMapping("/work-effort-assoc-types")
    public List<WorkEffortAssocType> getAllWorkEffortAssocTypes() {
        log.debug("REST request to get all WorkEffortAssocTypes");
        return workEffortAssocTypeRepository.findAll();
    }

    /**
     * {@code GET  /work-effort-assoc-types/:id} : get the "id" workEffortAssocType.
     *
     * @param id the id of the workEffortAssocType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEffortAssocType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-effort-assoc-types/{id}")
    public ResponseEntity<WorkEffortAssocType> getWorkEffortAssocType(@PathVariable Long id) {
        log.debug("REST request to get WorkEffortAssocType : {}", id);
        Optional<WorkEffortAssocType> workEffortAssocType = workEffortAssocTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workEffortAssocType);
    }

    /**
     * {@code DELETE  /work-effort-assoc-types/:id} : delete the "id" workEffortAssocType.
     *
     * @param id the id of the workEffortAssocType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-effort-assoc-types/{id}")
    public ResponseEntity<Void> deleteWorkEffortAssocType(@PathVariable Long id) {
        log.debug("REST request to delete WorkEffortAssocType : {}", id);
        workEffortAssocTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
