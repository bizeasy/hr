package com.hr.web.rest;

import com.hr.domain.JobInterviewType;
import com.hr.repository.JobInterviewTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.JobInterviewType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JobInterviewTypeResource {

    private final Logger log = LoggerFactory.getLogger(JobInterviewTypeResource.class);

    private static final String ENTITY_NAME = "jobInterviewType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobInterviewTypeRepository jobInterviewTypeRepository;

    public JobInterviewTypeResource(JobInterviewTypeRepository jobInterviewTypeRepository) {
        this.jobInterviewTypeRepository = jobInterviewTypeRepository;
    }

    /**
     * {@code POST  /job-interview-types} : Create a new jobInterviewType.
     *
     * @param jobInterviewType the jobInterviewType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobInterviewType, or with status {@code 400 (Bad Request)} if the jobInterviewType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-interview-types")
    public ResponseEntity<JobInterviewType> createJobInterviewType(@Valid @RequestBody JobInterviewType jobInterviewType) throws URISyntaxException {
        log.debug("REST request to save JobInterviewType : {}", jobInterviewType);
        if (jobInterviewType.getId() != null) {
            throw new BadRequestAlertException("A new jobInterviewType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobInterviewType result = jobInterviewTypeRepository.save(jobInterviewType);
        return ResponseEntity.created(new URI("/api/job-interview-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-interview-types} : Updates an existing jobInterviewType.
     *
     * @param jobInterviewType the jobInterviewType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobInterviewType,
     * or with status {@code 400 (Bad Request)} if the jobInterviewType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobInterviewType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-interview-types")
    public ResponseEntity<JobInterviewType> updateJobInterviewType(@Valid @RequestBody JobInterviewType jobInterviewType) throws URISyntaxException {
        log.debug("REST request to update JobInterviewType : {}", jobInterviewType);
        if (jobInterviewType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobInterviewType result = jobInterviewTypeRepository.save(jobInterviewType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobInterviewType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-interview-types} : get all the jobInterviewTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobInterviewTypes in body.
     */
    @GetMapping("/job-interview-types")
    public List<JobInterviewType> getAllJobInterviewTypes() {
        log.debug("REST request to get all JobInterviewTypes");
        return jobInterviewTypeRepository.findAll();
    }

    /**
     * {@code GET  /job-interview-types/:id} : get the "id" jobInterviewType.
     *
     * @param id the id of the jobInterviewType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobInterviewType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-interview-types/{id}")
    public ResponseEntity<JobInterviewType> getJobInterviewType(@PathVariable Long id) {
        log.debug("REST request to get JobInterviewType : {}", id);
        Optional<JobInterviewType> jobInterviewType = jobInterviewTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobInterviewType);
    }

    /**
     * {@code DELETE  /job-interview-types/:id} : delete the "id" jobInterviewType.
     *
     * @param id the id of the jobInterviewType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-interview-types/{id}")
    public ResponseEntity<Void> deleteJobInterviewType(@PathVariable Long id) {
        log.debug("REST request to delete JobInterviewType : {}", id);
        jobInterviewTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
