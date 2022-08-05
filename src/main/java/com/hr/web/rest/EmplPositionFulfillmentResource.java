package com.hr.web.rest;

import com.hr.domain.EmplPositionFulfillment;
import com.hr.repository.EmplPositionFulfillmentRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplPositionFulfillment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplPositionFulfillmentResource {

    private final Logger log = LoggerFactory.getLogger(EmplPositionFulfillmentResource.class);

    private static final String ENTITY_NAME = "emplPositionFulfillment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplPositionFulfillmentRepository emplPositionFulfillmentRepository;

    public EmplPositionFulfillmentResource(EmplPositionFulfillmentRepository emplPositionFulfillmentRepository) {
        this.emplPositionFulfillmentRepository = emplPositionFulfillmentRepository;
    }

    /**
     * {@code POST  /empl-position-fulfillments} : Create a new emplPositionFulfillment.
     *
     * @param emplPositionFulfillment the emplPositionFulfillment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplPositionFulfillment, or with status {@code 400 (Bad Request)} if the emplPositionFulfillment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-position-fulfillments")
    public ResponseEntity<EmplPositionFulfillment> createEmplPositionFulfillment(@RequestBody EmplPositionFulfillment emplPositionFulfillment) throws URISyntaxException {
        log.debug("REST request to save EmplPositionFulfillment : {}", emplPositionFulfillment);
        if (emplPositionFulfillment.getId() != null) {
            throw new BadRequestAlertException("A new emplPositionFulfillment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplPositionFulfillment result = emplPositionFulfillmentRepository.save(emplPositionFulfillment);
        return ResponseEntity.created(new URI("/api/empl-position-fulfillments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-position-fulfillments} : Updates an existing emplPositionFulfillment.
     *
     * @param emplPositionFulfillment the emplPositionFulfillment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplPositionFulfillment,
     * or with status {@code 400 (Bad Request)} if the emplPositionFulfillment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplPositionFulfillment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-position-fulfillments")
    public ResponseEntity<EmplPositionFulfillment> updateEmplPositionFulfillment(@RequestBody EmplPositionFulfillment emplPositionFulfillment) throws URISyntaxException {
        log.debug("REST request to update EmplPositionFulfillment : {}", emplPositionFulfillment);
        if (emplPositionFulfillment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplPositionFulfillment result = emplPositionFulfillmentRepository.save(emplPositionFulfillment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplPositionFulfillment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-position-fulfillments} : get all the emplPositionFulfillments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplPositionFulfillments in body.
     */
    @GetMapping("/empl-position-fulfillments")
    public List<EmplPositionFulfillment> getAllEmplPositionFulfillments() {
        log.debug("REST request to get all EmplPositionFulfillments");
        return emplPositionFulfillmentRepository.findAll();
    }

    /**
     * {@code GET  /empl-position-fulfillments/:id} : get the "id" emplPositionFulfillment.
     *
     * @param id the id of the emplPositionFulfillment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplPositionFulfillment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-position-fulfillments/{id}")
    public ResponseEntity<EmplPositionFulfillment> getEmplPositionFulfillment(@PathVariable Long id) {
        log.debug("REST request to get EmplPositionFulfillment : {}", id);
        Optional<EmplPositionFulfillment> emplPositionFulfillment = emplPositionFulfillmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplPositionFulfillment);
    }

    /**
     * {@code DELETE  /empl-position-fulfillments/:id} : delete the "id" emplPositionFulfillment.
     *
     * @param id the id of the emplPositionFulfillment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-position-fulfillments/{id}")
    public ResponseEntity<Void> deleteEmplPositionFulfillment(@PathVariable Long id) {
        log.debug("REST request to delete EmplPositionFulfillment : {}", id);
        emplPositionFulfillmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
