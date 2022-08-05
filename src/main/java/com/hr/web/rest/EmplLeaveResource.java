package com.hr.web.rest;

import com.hr.domain.EmplLeave;
import com.hr.repository.EmplLeaveRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplLeave}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplLeaveResource {

    private final Logger log = LoggerFactory.getLogger(EmplLeaveResource.class);

    private static final String ENTITY_NAME = "emplLeave";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplLeaveRepository emplLeaveRepository;

    public EmplLeaveResource(EmplLeaveRepository emplLeaveRepository) {
        this.emplLeaveRepository = emplLeaveRepository;
    }

    /**
     * {@code POST  /empl-leaves} : Create a new emplLeave.
     *
     * @param emplLeave the emplLeave to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplLeave, or with status {@code 400 (Bad Request)} if the emplLeave has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-leaves")
    public ResponseEntity<EmplLeave> createEmplLeave(@RequestBody EmplLeave emplLeave) throws URISyntaxException {
        log.debug("REST request to save EmplLeave : {}", emplLeave);
        if (emplLeave.getId() != null) {
            throw new BadRequestAlertException("A new emplLeave cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplLeave result = emplLeaveRepository.save(emplLeave);
        return ResponseEntity.created(new URI("/api/empl-leaves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-leaves} : Updates an existing emplLeave.
     *
     * @param emplLeave the emplLeave to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplLeave,
     * or with status {@code 400 (Bad Request)} if the emplLeave is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplLeave couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-leaves")
    public ResponseEntity<EmplLeave> updateEmplLeave(@RequestBody EmplLeave emplLeave) throws URISyntaxException {
        log.debug("REST request to update EmplLeave : {}", emplLeave);
        if (emplLeave.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplLeave result = emplLeaveRepository.save(emplLeave);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplLeave.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-leaves} : get all the emplLeaves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplLeaves in body.
     */
    @GetMapping("/empl-leaves")
    public List<EmplLeave> getAllEmplLeaves() {
        log.debug("REST request to get all EmplLeaves");
        return emplLeaveRepository.findAll();
    }

    /**
     * {@code GET  /empl-leaves/:id} : get the "id" emplLeave.
     *
     * @param id the id of the emplLeave to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplLeave, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-leaves/{id}")
    public ResponseEntity<EmplLeave> getEmplLeave(@PathVariable Long id) {
        log.debug("REST request to get EmplLeave : {}", id);
        Optional<EmplLeave> emplLeave = emplLeaveRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplLeave);
    }

    /**
     * {@code DELETE  /empl-leaves/:id} : delete the "id" emplLeave.
     *
     * @param id the id of the emplLeave to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-leaves/{id}")
    public ResponseEntity<Void> deleteEmplLeave(@PathVariable Long id) {
        log.debug("REST request to delete EmplLeave : {}", id);
        emplLeaveRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
