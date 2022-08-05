package com.hr.web.rest;

import com.hr.domain.JobRequisition;
import com.hr.repository.JobRequisitionRepository;
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
 * REST controller for managing {@link com.hr.domain.JobRequisition}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JobRequisitionResource {

    private final Logger log = LoggerFactory.getLogger(JobRequisitionResource.class);

    private static final String ENTITY_NAME = "jobRequisition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobRequisitionRepository jobRequisitionRepository;

    public JobRequisitionResource(JobRequisitionRepository jobRequisitionRepository) {
        this.jobRequisitionRepository = jobRequisitionRepository;
    }

    /**
     * {@code POST  /job-requisitions} : Create a new jobRequisition.
     *
     * @param jobRequisition the jobRequisition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobRequisition, or with status {@code 400 (Bad Request)} if the jobRequisition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-requisitions")
    public ResponseEntity<JobRequisition> createJobRequisition(@Valid @RequestBody JobRequisition jobRequisition) throws URISyntaxException {
        log.debug("REST request to save JobRequisition : {}", jobRequisition);
        if (jobRequisition.getId() != null) {
            throw new BadRequestAlertException("A new jobRequisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobRequisition result = jobRequisitionRepository.save(jobRequisition);
        return ResponseEntity.created(new URI("/api/job-requisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-requisitions} : Updates an existing jobRequisition.
     *
     * @param jobRequisition the jobRequisition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobRequisition,
     * or with status {@code 400 (Bad Request)} if the jobRequisition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobRequisition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-requisitions")
    public ResponseEntity<JobRequisition> updateJobRequisition(@Valid @RequestBody JobRequisition jobRequisition) throws URISyntaxException {
        log.debug("REST request to update JobRequisition : {}", jobRequisition);
        if (jobRequisition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobRequisition result = jobRequisitionRepository.save(jobRequisition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobRequisition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-requisitions} : get all the jobRequisitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobRequisitions in body.
     */
    @GetMapping("/job-requisitions")
    public List<JobRequisition> getAllJobRequisitions() {
        log.debug("REST request to get all JobRequisitions");
        return jobRequisitionRepository.findAll();
    }

    /**
     * {@code GET  /job-requisitions/:id} : get the "id" jobRequisition.
     *
     * @param id the id of the jobRequisition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobRequisition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-requisitions/{id}")
    public ResponseEntity<JobRequisition> getJobRequisition(@PathVariable Long id) {
        log.debug("REST request to get JobRequisition : {}", id);
        Optional<JobRequisition> jobRequisition = jobRequisitionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobRequisition);
    }

    /**
     * {@code DELETE  /job-requisitions/:id} : delete the "id" jobRequisition.
     *
     * @param id the id of the jobRequisition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-requisitions/{id}")
    public ResponseEntity<Void> deleteJobRequisition(@PathVariable Long id) {
        log.debug("REST request to delete JobRequisition : {}", id);
        jobRequisitionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
