package com.hr.web.rest;

import com.hr.domain.EmplLeaveType;
import com.hr.repository.EmplLeaveTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplLeaveType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplLeaveTypeResource {

    private final Logger log = LoggerFactory.getLogger(EmplLeaveTypeResource.class);

    private static final String ENTITY_NAME = "emplLeaveType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplLeaveTypeRepository emplLeaveTypeRepository;

    public EmplLeaveTypeResource(EmplLeaveTypeRepository emplLeaveTypeRepository) {
        this.emplLeaveTypeRepository = emplLeaveTypeRepository;
    }

    /**
     * {@code POST  /empl-leave-types} : Create a new emplLeaveType.
     *
     * @param emplLeaveType the emplLeaveType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplLeaveType, or with status {@code 400 (Bad Request)} if the emplLeaveType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-leave-types")
    public ResponseEntity<EmplLeaveType> createEmplLeaveType(@Valid @RequestBody EmplLeaveType emplLeaveType) throws URISyntaxException {
        log.debug("REST request to save EmplLeaveType : {}", emplLeaveType);
        if (emplLeaveType.getId() != null) {
            throw new BadRequestAlertException("A new emplLeaveType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplLeaveType result = emplLeaveTypeRepository.save(emplLeaveType);
        return ResponseEntity.created(new URI("/api/empl-leave-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-leave-types} : Updates an existing emplLeaveType.
     *
     * @param emplLeaveType the emplLeaveType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplLeaveType,
     * or with status {@code 400 (Bad Request)} if the emplLeaveType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplLeaveType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-leave-types")
    public ResponseEntity<EmplLeaveType> updateEmplLeaveType(@Valid @RequestBody EmplLeaveType emplLeaveType) throws URISyntaxException {
        log.debug("REST request to update EmplLeaveType : {}", emplLeaveType);
        if (emplLeaveType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplLeaveType result = emplLeaveTypeRepository.save(emplLeaveType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplLeaveType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-leave-types} : get all the emplLeaveTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplLeaveTypes in body.
     */
    @GetMapping("/empl-leave-types")
    public List<EmplLeaveType> getAllEmplLeaveTypes() {
        log.debug("REST request to get all EmplLeaveTypes");
        return emplLeaveTypeRepository.findAll();
    }

    /**
     * {@code GET  /empl-leave-types/:id} : get the "id" emplLeaveType.
     *
     * @param id the id of the emplLeaveType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplLeaveType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-leave-types/{id}")
    public ResponseEntity<EmplLeaveType> getEmplLeaveType(@PathVariable Long id) {
        log.debug("REST request to get EmplLeaveType : {}", id);
        Optional<EmplLeaveType> emplLeaveType = emplLeaveTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplLeaveType);
    }

    /**
     * {@code DELETE  /empl-leave-types/:id} : delete the "id" emplLeaveType.
     *
     * @param id the id of the emplLeaveType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-leave-types/{id}")
    public ResponseEntity<Void> deleteEmplLeaveType(@PathVariable Long id) {
        log.debug("REST request to delete EmplLeaveType : {}", id);
        emplLeaveTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
