package com.hr.web.rest;

import com.hr.domain.JobInterview;
import com.hr.repository.JobInterviewRepository;
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
 * REST controller for managing {@link com.hr.domain.JobInterview}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JobInterviewResource {

    private final Logger log = LoggerFactory.getLogger(JobInterviewResource.class);

    private static final String ENTITY_NAME = "jobInterview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobInterviewRepository jobInterviewRepository;

    public JobInterviewResource(JobInterviewRepository jobInterviewRepository) {
        this.jobInterviewRepository = jobInterviewRepository;
    }

    /**
     * {@code POST  /job-interviews} : Create a new jobInterview.
     *
     * @param jobInterview the jobInterview to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobInterview, or with status {@code 400 (Bad Request)} if the jobInterview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-interviews")
    public ResponseEntity<JobInterview> createJobInterview(@RequestBody JobInterview jobInterview) throws URISyntaxException {
        log.debug("REST request to save JobInterview : {}", jobInterview);
        if (jobInterview.getId() != null) {
            throw new BadRequestAlertException("A new jobInterview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobInterview result = jobInterviewRepository.save(jobInterview);
        return ResponseEntity.created(new URI("/api/job-interviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-interviews} : Updates an existing jobInterview.
     *
     * @param jobInterview the jobInterview to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobInterview,
     * or with status {@code 400 (Bad Request)} if the jobInterview is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobInterview couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-interviews")
    public ResponseEntity<JobInterview> updateJobInterview(@RequestBody JobInterview jobInterview) throws URISyntaxException {
        log.debug("REST request to update JobInterview : {}", jobInterview);
        if (jobInterview.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobInterview result = jobInterviewRepository.save(jobInterview);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobInterview.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-interviews} : get all the jobInterviews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobInterviews in body.
     */
    @GetMapping("/job-interviews")
    public List<JobInterview> getAllJobInterviews() {
        log.debug("REST request to get all JobInterviews");
        return jobInterviewRepository.findAll();
    }

    /**
     * {@code GET  /job-interviews/:id} : get the "id" jobInterview.
     *
     * @param id the id of the jobInterview to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobInterview, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-interviews/{id}")
    public ResponseEntity<JobInterview> getJobInterview(@PathVariable Long id) {
        log.debug("REST request to get JobInterview : {}", id);
        Optional<JobInterview> jobInterview = jobInterviewRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobInterview);
    }

    /**
     * {@code DELETE  /job-interviews/:id} : delete the "id" jobInterview.
     *
     * @param id the id of the jobInterview to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-interviews/{id}")
    public ResponseEntity<Void> deleteJobInterview(@PathVariable Long id) {
        log.debug("REST request to delete JobInterview : {}", id);
        jobInterviewRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
