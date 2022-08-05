package com.hr.web.rest;

import com.hr.domain.FacilityUsageLog;
import com.hr.repository.FacilityUsageLogRepository;
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
 * REST controller for managing {@link com.hr.domain.FacilityUsageLog}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FacilityUsageLogResource {

    private final Logger log = LoggerFactory.getLogger(FacilityUsageLogResource.class);

    private static final String ENTITY_NAME = "facilityUsageLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityUsageLogRepository facilityUsageLogRepository;

    public FacilityUsageLogResource(FacilityUsageLogRepository facilityUsageLogRepository) {
        this.facilityUsageLogRepository = facilityUsageLogRepository;
    }

    /**
     * {@code POST  /facility-usage-logs} : Create a new facilityUsageLog.
     *
     * @param facilityUsageLog the facilityUsageLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityUsageLog, or with status {@code 400 (Bad Request)} if the facilityUsageLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facility-usage-logs")
    public ResponseEntity<FacilityUsageLog> createFacilityUsageLog(@RequestBody FacilityUsageLog facilityUsageLog) throws URISyntaxException {
        log.debug("REST request to save FacilityUsageLog : {}", facilityUsageLog);
        if (facilityUsageLog.getId() != null) {
            throw new BadRequestAlertException("A new facilityUsageLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityUsageLog result = facilityUsageLogRepository.save(facilityUsageLog);
        return ResponseEntity.created(new URI("/api/facility-usage-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facility-usage-logs} : Updates an existing facilityUsageLog.
     *
     * @param facilityUsageLog the facilityUsageLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityUsageLog,
     * or with status {@code 400 (Bad Request)} if the facilityUsageLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityUsageLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facility-usage-logs")
    public ResponseEntity<FacilityUsageLog> updateFacilityUsageLog(@RequestBody FacilityUsageLog facilityUsageLog) throws URISyntaxException {
        log.debug("REST request to update FacilityUsageLog : {}", facilityUsageLog);
        if (facilityUsageLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityUsageLog result = facilityUsageLogRepository.save(facilityUsageLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityUsageLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facility-usage-logs} : get all the facilityUsageLogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilityUsageLogs in body.
     */
    @GetMapping("/facility-usage-logs")
    public List<FacilityUsageLog> getAllFacilityUsageLogs() {
        log.debug("REST request to get all FacilityUsageLogs");
        return facilityUsageLogRepository.findAll();
    }

    /**
     * {@code GET  /facility-usage-logs/:id} : get the "id" facilityUsageLog.
     *
     * @param id the id of the facilityUsageLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityUsageLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facility-usage-logs/{id}")
    public ResponseEntity<FacilityUsageLog> getFacilityUsageLog(@PathVariable Long id) {
        log.debug("REST request to get FacilityUsageLog : {}", id);
        Optional<FacilityUsageLog> facilityUsageLog = facilityUsageLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(facilityUsageLog);
    }

    /**
     * {@code DELETE  /facility-usage-logs/:id} : delete the "id" facilityUsageLog.
     *
     * @param id the id of the facilityUsageLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facility-usage-logs/{id}")
    public ResponseEntity<Void> deleteFacilityUsageLog(@PathVariable Long id) {
        log.debug("REST request to delete FacilityUsageLog : {}", id);
        facilityUsageLogRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
