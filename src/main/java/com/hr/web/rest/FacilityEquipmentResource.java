package com.hr.web.rest;

import com.hr.domain.FacilityEquipment;
import com.hr.repository.FacilityEquipmentRepository;
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
 * REST controller for managing {@link com.hr.domain.FacilityEquipment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FacilityEquipmentResource {

    private final Logger log = LoggerFactory.getLogger(FacilityEquipmentResource.class);

    private static final String ENTITY_NAME = "facilityEquipment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityEquipmentRepository facilityEquipmentRepository;

    public FacilityEquipmentResource(FacilityEquipmentRepository facilityEquipmentRepository) {
        this.facilityEquipmentRepository = facilityEquipmentRepository;
    }

    /**
     * {@code POST  /facility-equipments} : Create a new facilityEquipment.
     *
     * @param facilityEquipment the facilityEquipment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityEquipment, or with status {@code 400 (Bad Request)} if the facilityEquipment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facility-equipments")
    public ResponseEntity<FacilityEquipment> createFacilityEquipment(@Valid @RequestBody FacilityEquipment facilityEquipment) throws URISyntaxException {
        log.debug("REST request to save FacilityEquipment : {}", facilityEquipment);
        if (facilityEquipment.getId() != null) {
            throw new BadRequestAlertException("A new facilityEquipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityEquipment result = facilityEquipmentRepository.save(facilityEquipment);
        return ResponseEntity.created(new URI("/api/facility-equipments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facility-equipments} : Updates an existing facilityEquipment.
     *
     * @param facilityEquipment the facilityEquipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityEquipment,
     * or with status {@code 400 (Bad Request)} if the facilityEquipment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityEquipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facility-equipments")
    public ResponseEntity<FacilityEquipment> updateFacilityEquipment(@Valid @RequestBody FacilityEquipment facilityEquipment) throws URISyntaxException {
        log.debug("REST request to update FacilityEquipment : {}", facilityEquipment);
        if (facilityEquipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityEquipment result = facilityEquipmentRepository.save(facilityEquipment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityEquipment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facility-equipments} : get all the facilityEquipments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilityEquipments in body.
     */
    @GetMapping("/facility-equipments")
    public List<FacilityEquipment> getAllFacilityEquipments() {
        log.debug("REST request to get all FacilityEquipments");
        return facilityEquipmentRepository.findAll();
    }

    /**
     * {@code GET  /facility-equipments/:id} : get the "id" facilityEquipment.
     *
     * @param id the id of the facilityEquipment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityEquipment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facility-equipments/{id}")
    public ResponseEntity<FacilityEquipment> getFacilityEquipment(@PathVariable Long id) {
        log.debug("REST request to get FacilityEquipment : {}", id);
        Optional<FacilityEquipment> facilityEquipment = facilityEquipmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(facilityEquipment);
    }

    /**
     * {@code DELETE  /facility-equipments/:id} : delete the "id" facilityEquipment.
     *
     * @param id the id of the facilityEquipment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facility-equipments/{id}")
    public ResponseEntity<Void> deleteFacilityEquipment(@PathVariable Long id) {
        log.debug("REST request to delete FacilityEquipment : {}", id);
        facilityEquipmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
