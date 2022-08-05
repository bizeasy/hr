package com.hr.web.rest;

import com.hr.domain.EquipmentUsageLog;
import com.hr.repository.EquipmentUsageLogRepository;
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
 * REST controller for managing {@link com.hr.domain.EquipmentUsageLog}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EquipmentUsageLogResource {

    private final Logger log = LoggerFactory.getLogger(EquipmentUsageLogResource.class);

    private static final String ENTITY_NAME = "equipmentUsageLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentUsageLogRepository equipmentUsageLogRepository;

    public EquipmentUsageLogResource(EquipmentUsageLogRepository equipmentUsageLogRepository) {
        this.equipmentUsageLogRepository = equipmentUsageLogRepository;
    }

    /**
     * {@code POST  /equipment-usage-logs} : Create a new equipmentUsageLog.
     *
     * @param equipmentUsageLog the equipmentUsageLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipmentUsageLog, or with status {@code 400 (Bad Request)} if the equipmentUsageLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipment-usage-logs")
    public ResponseEntity<EquipmentUsageLog> createEquipmentUsageLog(@RequestBody EquipmentUsageLog equipmentUsageLog) throws URISyntaxException {
        log.debug("REST request to save EquipmentUsageLog : {}", equipmentUsageLog);
        if (equipmentUsageLog.getId() != null) {
            throw new BadRequestAlertException("A new equipmentUsageLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipmentUsageLog result = equipmentUsageLogRepository.save(equipmentUsageLog);
        return ResponseEntity.created(new URI("/api/equipment-usage-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipment-usage-logs} : Updates an existing equipmentUsageLog.
     *
     * @param equipmentUsageLog the equipmentUsageLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentUsageLog,
     * or with status {@code 400 (Bad Request)} if the equipmentUsageLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipmentUsageLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipment-usage-logs")
    public ResponseEntity<EquipmentUsageLog> updateEquipmentUsageLog(@RequestBody EquipmentUsageLog equipmentUsageLog) throws URISyntaxException {
        log.debug("REST request to update EquipmentUsageLog : {}", equipmentUsageLog);
        if (equipmentUsageLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EquipmentUsageLog result = equipmentUsageLogRepository.save(equipmentUsageLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipmentUsageLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipment-usage-logs} : get all the equipmentUsageLogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipmentUsageLogs in body.
     */
    @GetMapping("/equipment-usage-logs")
    public List<EquipmentUsageLog> getAllEquipmentUsageLogs() {
        log.debug("REST request to get all EquipmentUsageLogs");
        return equipmentUsageLogRepository.findAll();
    }

    /**
     * {@code GET  /equipment-usage-logs/:id} : get the "id" equipmentUsageLog.
     *
     * @param id the id of the equipmentUsageLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipmentUsageLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipment-usage-logs/{id}")
    public ResponseEntity<EquipmentUsageLog> getEquipmentUsageLog(@PathVariable Long id) {
        log.debug("REST request to get EquipmentUsageLog : {}", id);
        Optional<EquipmentUsageLog> equipmentUsageLog = equipmentUsageLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipmentUsageLog);
    }

    /**
     * {@code DELETE  /equipment-usage-logs/:id} : delete the "id" equipmentUsageLog.
     *
     * @param id the id of the equipmentUsageLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipment-usage-logs/{id}")
    public ResponseEntity<Void> deleteEquipmentUsageLog(@PathVariable Long id) {
        log.debug("REST request to delete EquipmentUsageLog : {}", id);
        equipmentUsageLogRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
