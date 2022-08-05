package com.hr.web.rest;

import com.hr.domain.CommunicationEvent;
import com.hr.repository.CommunicationEventRepository;
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
 * REST controller for managing {@link com.hr.domain.CommunicationEvent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommunicationEventResource {

    private final Logger log = LoggerFactory.getLogger(CommunicationEventResource.class);

    private static final String ENTITY_NAME = "communicationEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommunicationEventRepository communicationEventRepository;

    public CommunicationEventResource(CommunicationEventRepository communicationEventRepository) {
        this.communicationEventRepository = communicationEventRepository;
    }

    /**
     * {@code POST  /communication-events} : Create a new communicationEvent.
     *
     * @param communicationEvent the communicationEvent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new communicationEvent, or with status {@code 400 (Bad Request)} if the communicationEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/communication-events")
    public ResponseEntity<CommunicationEvent> createCommunicationEvent(@RequestBody CommunicationEvent communicationEvent) throws URISyntaxException {
        log.debug("REST request to save CommunicationEvent : {}", communicationEvent);
        if (communicationEvent.getId() != null) {
            throw new BadRequestAlertException("A new communicationEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommunicationEvent result = communicationEventRepository.save(communicationEvent);
        return ResponseEntity.created(new URI("/api/communication-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /communication-events} : Updates an existing communicationEvent.
     *
     * @param communicationEvent the communicationEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communicationEvent,
     * or with status {@code 400 (Bad Request)} if the communicationEvent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the communicationEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/communication-events")
    public ResponseEntity<CommunicationEvent> updateCommunicationEvent(@RequestBody CommunicationEvent communicationEvent) throws URISyntaxException {
        log.debug("REST request to update CommunicationEvent : {}", communicationEvent);
        if (communicationEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommunicationEvent result = communicationEventRepository.save(communicationEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communicationEvent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /communication-events} : get all the communicationEvents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of communicationEvents in body.
     */
    @GetMapping("/communication-events")
    public List<CommunicationEvent> getAllCommunicationEvents() {
        log.debug("REST request to get all CommunicationEvents");
        return communicationEventRepository.findAll();
    }

    /**
     * {@code GET  /communication-events/:id} : get the "id" communicationEvent.
     *
     * @param id the id of the communicationEvent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the communicationEvent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/communication-events/{id}")
    public ResponseEntity<CommunicationEvent> getCommunicationEvent(@PathVariable Long id) {
        log.debug("REST request to get CommunicationEvent : {}", id);
        Optional<CommunicationEvent> communicationEvent = communicationEventRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(communicationEvent);
    }

    /**
     * {@code DELETE  /communication-events/:id} : delete the "id" communicationEvent.
     *
     * @param id the id of the communicationEvent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/communication-events/{id}")
    public ResponseEntity<Void> deleteCommunicationEvent(@PathVariable Long id) {
        log.debug("REST request to delete CommunicationEvent : {}", id);
        communicationEventRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
