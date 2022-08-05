package com.hr.web.rest;

import com.hr.domain.FacilityContactMech;
import com.hr.service.FacilityContactMechService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.FacilityContactMechCriteria;
import com.hr.service.FacilityContactMechQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.FacilityContactMech}.
 */
@RestController
@RequestMapping("/api")
public class FacilityContactMechResource {

    private final Logger log = LoggerFactory.getLogger(FacilityContactMechResource.class);

    private static final String ENTITY_NAME = "facilityContactMech";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityContactMechService facilityContactMechService;

    private final FacilityContactMechQueryService facilityContactMechQueryService;

    public FacilityContactMechResource(FacilityContactMechService facilityContactMechService, FacilityContactMechQueryService facilityContactMechQueryService) {
        this.facilityContactMechService = facilityContactMechService;
        this.facilityContactMechQueryService = facilityContactMechQueryService;
    }

    /**
     * {@code POST  /facility-contact-meches} : Create a new facilityContactMech.
     *
     * @param facilityContactMech the facilityContactMech to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityContactMech, or with status {@code 400 (Bad Request)} if the facilityContactMech has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facility-contact-meches")
    public ResponseEntity<FacilityContactMech> createFacilityContactMech(@RequestBody FacilityContactMech facilityContactMech) throws URISyntaxException {
        log.debug("REST request to save FacilityContactMech : {}", facilityContactMech);
        if (facilityContactMech.getId() != null) {
            throw new BadRequestAlertException("A new facilityContactMech cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityContactMech result = facilityContactMechService.save(facilityContactMech);
        return ResponseEntity.created(new URI("/api/facility-contact-meches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facility-contact-meches} : Updates an existing facilityContactMech.
     *
     * @param facilityContactMech the facilityContactMech to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityContactMech,
     * or with status {@code 400 (Bad Request)} if the facilityContactMech is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityContactMech couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facility-contact-meches")
    public ResponseEntity<FacilityContactMech> updateFacilityContactMech(@RequestBody FacilityContactMech facilityContactMech) throws URISyntaxException {
        log.debug("REST request to update FacilityContactMech : {}", facilityContactMech);
        if (facilityContactMech.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityContactMech result = facilityContactMechService.save(facilityContactMech);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityContactMech.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facility-contact-meches} : get all the facilityContactMeches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilityContactMeches in body.
     */
    @GetMapping("/facility-contact-meches")
    public ResponseEntity<List<FacilityContactMech>> getAllFacilityContactMeches(FacilityContactMechCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FacilityContactMeches by criteria: {}", criteria);
        Page<FacilityContactMech> page = facilityContactMechQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facility-contact-meches/count} : count all the facilityContactMeches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/facility-contact-meches/count")
    public ResponseEntity<Long> countFacilityContactMeches(FacilityContactMechCriteria criteria) {
        log.debug("REST request to count FacilityContactMeches by criteria: {}", criteria);
        return ResponseEntity.ok().body(facilityContactMechQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /facility-contact-meches/:id} : get the "id" facilityContactMech.
     *
     * @param id the id of the facilityContactMech to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityContactMech, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facility-contact-meches/{id}")
    public ResponseEntity<FacilityContactMech> getFacilityContactMech(@PathVariable Long id) {
        log.debug("REST request to get FacilityContactMech : {}", id);
        Optional<FacilityContactMech> facilityContactMech = facilityContactMechService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facilityContactMech);
    }

    /**
     * {@code DELETE  /facility-contact-meches/:id} : delete the "id" facilityContactMech.
     *
     * @param id the id of the facilityContactMech to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facility-contact-meches/{id}")
    public ResponseEntity<Void> deleteFacilityContactMech(@PathVariable Long id) {
        log.debug("REST request to delete FacilityContactMech : {}", id);
        facilityContactMechService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
