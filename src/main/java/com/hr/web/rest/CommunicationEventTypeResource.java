package com.hr.web.rest;

import com.hr.domain.CommunicationEventType;
import com.hr.repository.CommunicationEventTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.CommunicationEventType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommunicationEventTypeResource {

    private final Logger log = LoggerFactory.getLogger(CommunicationEventTypeResource.class);

    private static final String ENTITY_NAME = "communicationEventType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommunicationEventTypeRepository communicationEventTypeRepository;

    public CommunicationEventTypeResource(CommunicationEventTypeRepository communicationEventTypeRepository) {
        this.communicationEventTypeRepository = communicationEventTypeRepository;
    }

    /**
     * {@code POST  /communication-event-types} : Create a new communicationEventType.
     *
     * @param communicationEventType the communicationEventType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new communicationEventType, or with status {@code 400 (Bad Request)} if the communicationEventType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/communication-event-types")
    public ResponseEntity<CommunicationEventType> createCommunicationEventType(@RequestBody CommunicationEventType communicationEventType) throws URISyntaxException {
        log.debug("REST request to save CommunicationEventType : {}", communicationEventType);
        if (communicationEventType.getId() != null) {
            throw new BadRequestAlertException("A new communicationEventType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommunicationEventType result = communicationEventTypeRepository.save(communicationEventType);
        return ResponseEntity.created(new URI("/api/communication-event-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /communication-event-types} : Updates an existing communicationEventType.
     *
     * @param communicationEventType the communicationEventType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communicationEventType,
     * or with status {@code 400 (Bad Request)} if the communicationEventType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the communicationEventType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/communication-event-types")
    public ResponseEntity<CommunicationEventType> updateCommunicationEventType(@RequestBody CommunicationEventType communicationEventType) throws URISyntaxException {
        log.debug("REST request to update CommunicationEventType : {}", communicationEventType);
        if (communicationEventType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommunicationEventType result = communicationEventTypeRepository.save(communicationEventType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communicationEventType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /communication-event-types} : get all the communicationEventTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of communicationEventTypes in body.
     */
    @GetMapping("/communication-event-types")
    public List<CommunicationEventType> getAllCommunicationEventTypes() {
        log.debug("REST request to get all CommunicationEventTypes");
        return communicationEventTypeRepository.findAll();
    }

    /**
     * {@code GET  /communication-event-types/:id} : get the "id" communicationEventType.
     *
     * @param id the id of the communicationEventType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the communicationEventType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/communication-event-types/{id}")
    public ResponseEntity<CommunicationEventType> getCommunicationEventType(@PathVariable Long id) {
        log.debug("REST request to get CommunicationEventType : {}", id);
        Optional<CommunicationEventType> communicationEventType = communicationEventTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(communicationEventType);
    }

    /**
     * {@code DELETE  /communication-event-types/:id} : delete the "id" communicationEventType.
     *
     * @param id the id of the communicationEventType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/communication-event-types/{id}")
    public ResponseEntity<Void> deleteCommunicationEventType(@PathVariable Long id) {
        log.debug("REST request to delete CommunicationEventType : {}", id);
        communicationEventTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
