package com.hr.web.rest;

import com.hr.domain.EmplLeaveReasonType;
import com.hr.repository.EmplLeaveReasonTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplLeaveReasonType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplLeaveReasonTypeResource {

    private final Logger log = LoggerFactory.getLogger(EmplLeaveReasonTypeResource.class);

    private static final String ENTITY_NAME = "emplLeaveReasonType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplLeaveReasonTypeRepository emplLeaveReasonTypeRepository;

    public EmplLeaveReasonTypeResource(EmplLeaveReasonTypeRepository emplLeaveReasonTypeRepository) {
        this.emplLeaveReasonTypeRepository = emplLeaveReasonTypeRepository;
    }

    /**
     * {@code POST  /empl-leave-reason-types} : Create a new emplLeaveReasonType.
     *
     * @param emplLeaveReasonType the emplLeaveReasonType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplLeaveReasonType, or with status {@code 400 (Bad Request)} if the emplLeaveReasonType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-leave-reason-types")
    public ResponseEntity<EmplLeaveReasonType> createEmplLeaveReasonType(@Valid @RequestBody EmplLeaveReasonType emplLeaveReasonType) throws URISyntaxException {
        log.debug("REST request to save EmplLeaveReasonType : {}", emplLeaveReasonType);
        if (emplLeaveReasonType.getId() != null) {
            throw new BadRequestAlertException("A new emplLeaveReasonType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplLeaveReasonType result = emplLeaveReasonTypeRepository.save(emplLeaveReasonType);
        return ResponseEntity.created(new URI("/api/empl-leave-reason-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-leave-reason-types} : Updates an existing emplLeaveReasonType.
     *
     * @param emplLeaveReasonType the emplLeaveReasonType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplLeaveReasonType,
     * or with status {@code 400 (Bad Request)} if the emplLeaveReasonType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplLeaveReasonType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-leave-reason-types")
    public ResponseEntity<EmplLeaveReasonType> updateEmplLeaveReasonType(@Valid @RequestBody EmplLeaveReasonType emplLeaveReasonType) throws URISyntaxException {
        log.debug("REST request to update EmplLeaveReasonType : {}", emplLeaveReasonType);
        if (emplLeaveReasonType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplLeaveReasonType result = emplLeaveReasonTypeRepository.save(emplLeaveReasonType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplLeaveReasonType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-leave-reason-types} : get all the emplLeaveReasonTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplLeaveReasonTypes in body.
     */
    @GetMapping("/empl-leave-reason-types")
    public List<EmplLeaveReasonType> getAllEmplLeaveReasonTypes() {
        log.debug("REST request to get all EmplLeaveReasonTypes");
        return emplLeaveReasonTypeRepository.findAll();
    }

    /**
     * {@code GET  /empl-leave-reason-types/:id} : get the "id" emplLeaveReasonType.
     *
     * @param id the id of the emplLeaveReasonType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplLeaveReasonType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-leave-reason-types/{id}")
    public ResponseEntity<EmplLeaveReasonType> getEmplLeaveReasonType(@PathVariable Long id) {
        log.debug("REST request to get EmplLeaveReasonType : {}", id);
        Optional<EmplLeaveReasonType> emplLeaveReasonType = emplLeaveReasonTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplLeaveReasonType);
    }

    /**
     * {@code DELETE  /empl-leave-reason-types/:id} : delete the "id" emplLeaveReasonType.
     *
     * @param id the id of the emplLeaveReasonType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-leave-reason-types/{id}")
    public ResponseEntity<Void> deleteEmplLeaveReasonType(@PathVariable Long id) {
        log.debug("REST request to delete EmplLeaveReasonType : {}", id);
        emplLeaveReasonTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
