package com.hr.web.rest;

import com.hr.domain.InterviewGrade;
import com.hr.repository.InterviewGradeRepository;
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
 * REST controller for managing {@link com.hr.domain.InterviewGrade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InterviewGradeResource {

    private final Logger log = LoggerFactory.getLogger(InterviewGradeResource.class);

    private static final String ENTITY_NAME = "interviewGrade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewGradeRepository interviewGradeRepository;

    public InterviewGradeResource(InterviewGradeRepository interviewGradeRepository) {
        this.interviewGradeRepository = interviewGradeRepository;
    }

    /**
     * {@code POST  /interview-grades} : Create a new interviewGrade.
     *
     * @param interviewGrade the interviewGrade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewGrade, or with status {@code 400 (Bad Request)} if the interviewGrade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interview-grades")
    public ResponseEntity<InterviewGrade> createInterviewGrade(@Valid @RequestBody InterviewGrade interviewGrade) throws URISyntaxException {
        log.debug("REST request to save InterviewGrade : {}", interviewGrade);
        if (interviewGrade.getId() != null) {
            throw new BadRequestAlertException("A new interviewGrade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterviewGrade result = interviewGradeRepository.save(interviewGrade);
        return ResponseEntity.created(new URI("/api/interview-grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interview-grades} : Updates an existing interviewGrade.
     *
     * @param interviewGrade the interviewGrade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewGrade,
     * or with status {@code 400 (Bad Request)} if the interviewGrade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewGrade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interview-grades")
    public ResponseEntity<InterviewGrade> updateInterviewGrade(@Valid @RequestBody InterviewGrade interviewGrade) throws URISyntaxException {
        log.debug("REST request to update InterviewGrade : {}", interviewGrade);
        if (interviewGrade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterviewGrade result = interviewGradeRepository.save(interviewGrade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewGrade.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interview-grades} : get all the interviewGrades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewGrades in body.
     */
    @GetMapping("/interview-grades")
    public List<InterviewGrade> getAllInterviewGrades() {
        log.debug("REST request to get all InterviewGrades");
        return interviewGradeRepository.findAll();
    }

    /**
     * {@code GET  /interview-grades/:id} : get the "id" interviewGrade.
     *
     * @param id the id of the interviewGrade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewGrade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interview-grades/{id}")
    public ResponseEntity<InterviewGrade> getInterviewGrade(@PathVariable Long id) {
        log.debug("REST request to get InterviewGrade : {}", id);
        Optional<InterviewGrade> interviewGrade = interviewGradeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(interviewGrade);
    }

    /**
     * {@code DELETE  /interview-grades/:id} : delete the "id" interviewGrade.
     *
     * @param id the id of the interviewGrade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interview-grades/{id}")
    public ResponseEntity<Void> deleteInterviewGrade(@PathVariable Long id) {
        log.debug("REST request to delete InterviewGrade : {}", id);
        interviewGradeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
