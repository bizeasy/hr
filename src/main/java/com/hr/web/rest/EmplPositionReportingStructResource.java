package com.hr.web.rest;

import com.hr.domain.EmplPositionReportingStruct;
import com.hr.repository.EmplPositionReportingStructRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplPositionReportingStruct}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplPositionReportingStructResource {

    private final Logger log = LoggerFactory.getLogger(EmplPositionReportingStructResource.class);

    private static final String ENTITY_NAME = "emplPositionReportingStruct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplPositionReportingStructRepository emplPositionReportingStructRepository;

    public EmplPositionReportingStructResource(EmplPositionReportingStructRepository emplPositionReportingStructRepository) {
        this.emplPositionReportingStructRepository = emplPositionReportingStructRepository;
    }

    /**
     * {@code POST  /empl-position-reporting-structs} : Create a new emplPositionReportingStruct.
     *
     * @param emplPositionReportingStruct the emplPositionReportingStruct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplPositionReportingStruct, or with status {@code 400 (Bad Request)} if the emplPositionReportingStruct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-position-reporting-structs")
    public ResponseEntity<EmplPositionReportingStruct> createEmplPositionReportingStruct(@RequestBody EmplPositionReportingStruct emplPositionReportingStruct) throws URISyntaxException {
        log.debug("REST request to save EmplPositionReportingStruct : {}", emplPositionReportingStruct);
        if (emplPositionReportingStruct.getId() != null) {
            throw new BadRequestAlertException("A new emplPositionReportingStruct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplPositionReportingStruct result = emplPositionReportingStructRepository.save(emplPositionReportingStruct);
        return ResponseEntity.created(new URI("/api/empl-position-reporting-structs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-position-reporting-structs} : Updates an existing emplPositionReportingStruct.
     *
     * @param emplPositionReportingStruct the emplPositionReportingStruct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplPositionReportingStruct,
     * or with status {@code 400 (Bad Request)} if the emplPositionReportingStruct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplPositionReportingStruct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-position-reporting-structs")
    public ResponseEntity<EmplPositionReportingStruct> updateEmplPositionReportingStruct(@RequestBody EmplPositionReportingStruct emplPositionReportingStruct) throws URISyntaxException {
        log.debug("REST request to update EmplPositionReportingStruct : {}", emplPositionReportingStruct);
        if (emplPositionReportingStruct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplPositionReportingStruct result = emplPositionReportingStructRepository.save(emplPositionReportingStruct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplPositionReportingStruct.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-position-reporting-structs} : get all the emplPositionReportingStructs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplPositionReportingStructs in body.
     */
    @GetMapping("/empl-position-reporting-structs")
    public List<EmplPositionReportingStruct> getAllEmplPositionReportingStructs() {
        log.debug("REST request to get all EmplPositionReportingStructs");
        return emplPositionReportingStructRepository.findAll();
    }

    /**
     * {@code GET  /empl-position-reporting-structs/:id} : get the "id" emplPositionReportingStruct.
     *
     * @param id the id of the emplPositionReportingStruct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplPositionReportingStruct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-position-reporting-structs/{id}")
    public ResponseEntity<EmplPositionReportingStruct> getEmplPositionReportingStruct(@PathVariable Long id) {
        log.debug("REST request to get EmplPositionReportingStruct : {}", id);
        Optional<EmplPositionReportingStruct> emplPositionReportingStruct = emplPositionReportingStructRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplPositionReportingStruct);
    }

    /**
     * {@code DELETE  /empl-position-reporting-structs/:id} : delete the "id" emplPositionReportingStruct.
     *
     * @param id the id of the emplPositionReportingStruct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-position-reporting-structs/{id}")
    public ResponseEntity<Void> deleteEmplPositionReportingStruct(@PathVariable Long id) {
        log.debug("REST request to delete EmplPositionReportingStruct : {}", id);
        emplPositionReportingStructRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
