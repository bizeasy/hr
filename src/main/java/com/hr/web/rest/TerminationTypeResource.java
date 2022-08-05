package com.hr.web.rest;

import com.hr.domain.TerminationType;
import com.hr.repository.TerminationTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.TerminationType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TerminationTypeResource {

    private final Logger log = LoggerFactory.getLogger(TerminationTypeResource.class);

    private static final String ENTITY_NAME = "terminationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerminationTypeRepository terminationTypeRepository;

    public TerminationTypeResource(TerminationTypeRepository terminationTypeRepository) {
        this.terminationTypeRepository = terminationTypeRepository;
    }

    /**
     * {@code POST  /termination-types} : Create a new terminationType.
     *
     * @param terminationType the terminationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terminationType, or with status {@code 400 (Bad Request)} if the terminationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/termination-types")
    public ResponseEntity<TerminationType> createTerminationType(@Valid @RequestBody TerminationType terminationType) throws URISyntaxException {
        log.debug("REST request to save TerminationType : {}", terminationType);
        if (terminationType.getId() != null) {
            throw new BadRequestAlertException("A new terminationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TerminationType result = terminationTypeRepository.save(terminationType);
        return ResponseEntity.created(new URI("/api/termination-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /termination-types} : Updates an existing terminationType.
     *
     * @param terminationType the terminationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terminationType,
     * or with status {@code 400 (Bad Request)} if the terminationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terminationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/termination-types")
    public ResponseEntity<TerminationType> updateTerminationType(@Valid @RequestBody TerminationType terminationType) throws URISyntaxException {
        log.debug("REST request to update TerminationType : {}", terminationType);
        if (terminationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TerminationType result = terminationTypeRepository.save(terminationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terminationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /termination-types} : get all the terminationTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terminationTypes in body.
     */
    @GetMapping("/termination-types")
    public List<TerminationType> getAllTerminationTypes() {
        log.debug("REST request to get all TerminationTypes");
        return terminationTypeRepository.findAll();
    }

    /**
     * {@code GET  /termination-types/:id} : get the "id" terminationType.
     *
     * @param id the id of the terminationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terminationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/termination-types/{id}")
    public ResponseEntity<TerminationType> getTerminationType(@PathVariable Long id) {
        log.debug("REST request to get TerminationType : {}", id);
        Optional<TerminationType> terminationType = terminationTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(terminationType);
    }

    /**
     * {@code DELETE  /termination-types/:id} : delete the "id" terminationType.
     *
     * @param id the id of the terminationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/termination-types/{id}")
    public ResponseEntity<Void> deleteTerminationType(@PathVariable Long id) {
        log.debug("REST request to delete TerminationType : {}", id);
        terminationTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
