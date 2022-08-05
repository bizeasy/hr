package com.hr.web.rest;

import com.hr.domain.InterviewResult;
import com.hr.repository.InterviewResultRepository;
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
 * REST controller for managing {@link com.hr.domain.InterviewResult}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InterviewResultResource {

    private final Logger log = LoggerFactory.getLogger(InterviewResultResource.class);

    private static final String ENTITY_NAME = "interviewResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewResultRepository interviewResultRepository;

    public InterviewResultResource(InterviewResultRepository interviewResultRepository) {
        this.interviewResultRepository = interviewResultRepository;
    }

    /**
     * {@code POST  /interview-results} : Create a new interviewResult.
     *
     * @param interviewResult the interviewResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewResult, or with status {@code 400 (Bad Request)} if the interviewResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interview-results")
    public ResponseEntity<InterviewResult> createInterviewResult(@Valid @RequestBody InterviewResult interviewResult) throws URISyntaxException {
        log.debug("REST request to save InterviewResult : {}", interviewResult);
        if (interviewResult.getId() != null) {
            throw new BadRequestAlertException("A new interviewResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterviewResult result = interviewResultRepository.save(interviewResult);
        return ResponseEntity.created(new URI("/api/interview-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interview-results} : Updates an existing interviewResult.
     *
     * @param interviewResult the interviewResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewResult,
     * or with status {@code 400 (Bad Request)} if the interviewResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interview-results")
    public ResponseEntity<InterviewResult> updateInterviewResult(@Valid @RequestBody InterviewResult interviewResult) throws URISyntaxException {
        log.debug("REST request to update InterviewResult : {}", interviewResult);
        if (interviewResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterviewResult result = interviewResultRepository.save(interviewResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewResult.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interview-results} : get all the interviewResults.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewResults in body.
     */
    @GetMapping("/interview-results")
    public List<InterviewResult> getAllInterviewResults() {
        log.debug("REST request to get all InterviewResults");
        return interviewResultRepository.findAll();
    }

    /**
     * {@code GET  /interview-results/:id} : get the "id" interviewResult.
     *
     * @param id the id of the interviewResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interview-results/{id}")
    public ResponseEntity<InterviewResult> getInterviewResult(@PathVariable Long id) {
        log.debug("REST request to get InterviewResult : {}", id);
        Optional<InterviewResult> interviewResult = interviewResultRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(interviewResult);
    }

    /**
     * {@code DELETE  /interview-results/:id} : delete the "id" interviewResult.
     *
     * @param id the id of the interviewResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interview-results/{id}")
    public ResponseEntity<Void> deleteInterviewResult(@PathVariable Long id) {
        log.debug("REST request to delete InterviewResult : {}", id);
        interviewResultRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
