package com.hr.web.rest;

import com.hr.domain.EmplPosition;
import com.hr.repository.EmplPositionRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplPosition}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplPositionResource {

    private final Logger log = LoggerFactory.getLogger(EmplPositionResource.class);

    private static final String ENTITY_NAME = "emplPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplPositionRepository emplPositionRepository;

    public EmplPositionResource(EmplPositionRepository emplPositionRepository) {
        this.emplPositionRepository = emplPositionRepository;
    }

    /**
     * {@code POST  /empl-positions} : Create a new emplPosition.
     *
     * @param emplPosition the emplPosition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplPosition, or with status {@code 400 (Bad Request)} if the emplPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-positions")
    public ResponseEntity<EmplPosition> createEmplPosition(@RequestBody EmplPosition emplPosition) throws URISyntaxException {
        log.debug("REST request to save EmplPosition : {}", emplPosition);
        if (emplPosition.getId() != null) {
            throw new BadRequestAlertException("A new emplPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplPosition result = emplPositionRepository.save(emplPosition);
        return ResponseEntity.created(new URI("/api/empl-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-positions} : Updates an existing emplPosition.
     *
     * @param emplPosition the emplPosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplPosition,
     * or with status {@code 400 (Bad Request)} if the emplPosition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplPosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-positions")
    public ResponseEntity<EmplPosition> updateEmplPosition(@RequestBody EmplPosition emplPosition) throws URISyntaxException {
        log.debug("REST request to update EmplPosition : {}", emplPosition);
        if (emplPosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplPosition result = emplPositionRepository.save(emplPosition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplPosition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-positions} : get all the emplPositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplPositions in body.
     */
    @GetMapping("/empl-positions")
    public List<EmplPosition> getAllEmplPositions() {
        log.debug("REST request to get all EmplPositions");
        return emplPositionRepository.findAll();
    }

    /**
     * {@code GET  /empl-positions/:id} : get the "id" emplPosition.
     *
     * @param id the id of the emplPosition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplPosition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-positions/{id}")
    public ResponseEntity<EmplPosition> getEmplPosition(@PathVariable Long id) {
        log.debug("REST request to get EmplPosition : {}", id);
        Optional<EmplPosition> emplPosition = emplPositionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplPosition);
    }

    /**
     * {@code DELETE  /empl-positions/:id} : delete the "id" emplPosition.
     *
     * @param id the id of the emplPosition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-positions/{id}")
    public ResponseEntity<Void> deleteEmplPosition(@PathVariable Long id) {
        log.debug("REST request to delete EmplPosition : {}", id);
        emplPositionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
