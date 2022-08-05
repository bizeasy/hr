package com.hr.web.rest;

import com.hr.domain.PartyResume;
import com.hr.repository.PartyResumeRepository;
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
 * REST controller for managing {@link com.hr.domain.PartyResume}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyResumeResource {

    private final Logger log = LoggerFactory.getLogger(PartyResumeResource.class);

    private static final String ENTITY_NAME = "partyResume";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyResumeRepository partyResumeRepository;

    public PartyResumeResource(PartyResumeRepository partyResumeRepository) {
        this.partyResumeRepository = partyResumeRepository;
    }

    /**
     * {@code POST  /party-resumes} : Create a new partyResume.
     *
     * @param partyResume the partyResume to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyResume, or with status {@code 400 (Bad Request)} if the partyResume has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-resumes")
    public ResponseEntity<PartyResume> createPartyResume(@RequestBody PartyResume partyResume) throws URISyntaxException {
        log.debug("REST request to save PartyResume : {}", partyResume);
        if (partyResume.getId() != null) {
            throw new BadRequestAlertException("A new partyResume cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyResume result = partyResumeRepository.save(partyResume);
        return ResponseEntity.created(new URI("/api/party-resumes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-resumes} : Updates an existing partyResume.
     *
     * @param partyResume the partyResume to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyResume,
     * or with status {@code 400 (Bad Request)} if the partyResume is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyResume couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-resumes")
    public ResponseEntity<PartyResume> updatePartyResume(@RequestBody PartyResume partyResume) throws URISyntaxException {
        log.debug("REST request to update PartyResume : {}", partyResume);
        if (partyResume.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyResume result = partyResumeRepository.save(partyResume);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyResume.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-resumes} : get all the partyResumes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyResumes in body.
     */
    @GetMapping("/party-resumes")
    public List<PartyResume> getAllPartyResumes() {
        log.debug("REST request to get all PartyResumes");
        return partyResumeRepository.findAll();
    }

    /**
     * {@code GET  /party-resumes/:id} : get the "id" partyResume.
     *
     * @param id the id of the partyResume to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyResume, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-resumes/{id}")
    public ResponseEntity<PartyResume> getPartyResume(@PathVariable Long id) {
        log.debug("REST request to get PartyResume : {}", id);
        Optional<PartyResume> partyResume = partyResumeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyResume);
    }

    /**
     * {@code DELETE  /party-resumes/:id} : delete the "id" partyResume.
     *
     * @param id the id of the partyResume to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-resumes/{id}")
    public ResponseEntity<Void> deletePartyResume(@PathVariable Long id) {
        log.debug("REST request to delete PartyResume : {}", id);
        partyResumeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
