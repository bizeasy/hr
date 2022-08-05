package com.hr.web.rest;

import com.hr.domain.FacilityParty;
import com.hr.service.FacilityPartyService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.FacilityPartyCriteria;
import com.hr.service.FacilityPartyQueryService;

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
 * REST controller for managing {@link com.hr.domain.FacilityParty}.
 */
@RestController
@RequestMapping("/api")
public class FacilityPartyResource {

    private final Logger log = LoggerFactory.getLogger(FacilityPartyResource.class);

    private static final String ENTITY_NAME = "facilityParty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityPartyService facilityPartyService;

    private final FacilityPartyQueryService facilityPartyQueryService;

    public FacilityPartyResource(FacilityPartyService facilityPartyService, FacilityPartyQueryService facilityPartyQueryService) {
        this.facilityPartyService = facilityPartyService;
        this.facilityPartyQueryService = facilityPartyQueryService;
    }

    /**
     * {@code POST  /facility-parties} : Create a new facilityParty.
     *
     * @param facilityParty the facilityParty to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityParty, or with status {@code 400 (Bad Request)} if the facilityParty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facility-parties")
    public ResponseEntity<FacilityParty> createFacilityParty(@RequestBody FacilityParty facilityParty) throws URISyntaxException {
        log.debug("REST request to save FacilityParty : {}", facilityParty);
        if (facilityParty.getId() != null) {
            throw new BadRequestAlertException("A new facilityParty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityParty result = facilityPartyService.save(facilityParty);
        return ResponseEntity.created(new URI("/api/facility-parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facility-parties} : Updates an existing facilityParty.
     *
     * @param facilityParty the facilityParty to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityParty,
     * or with status {@code 400 (Bad Request)} if the facilityParty is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityParty couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facility-parties")
    public ResponseEntity<FacilityParty> updateFacilityParty(@RequestBody FacilityParty facilityParty) throws URISyntaxException {
        log.debug("REST request to update FacilityParty : {}", facilityParty);
        if (facilityParty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityParty result = facilityPartyService.save(facilityParty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityParty.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facility-parties} : get all the facilityParties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilityParties in body.
     */
    @GetMapping("/facility-parties")
    public ResponseEntity<List<FacilityParty>> getAllFacilityParties(FacilityPartyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FacilityParties by criteria: {}", criteria);
        Page<FacilityParty> page = facilityPartyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facility-parties/count} : count all the facilityParties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/facility-parties/count")
    public ResponseEntity<Long> countFacilityParties(FacilityPartyCriteria criteria) {
        log.debug("REST request to count FacilityParties by criteria: {}", criteria);
        return ResponseEntity.ok().body(facilityPartyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /facility-parties/:id} : get the "id" facilityParty.
     *
     * @param id the id of the facilityParty to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityParty, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facility-parties/{id}")
    public ResponseEntity<FacilityParty> getFacilityParty(@PathVariable Long id) {
        log.debug("REST request to get FacilityParty : {}", id);
        Optional<FacilityParty> facilityParty = facilityPartyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facilityParty);
    }

    /**
     * {@code DELETE  /facility-parties/:id} : delete the "id" facilityParty.
     *
     * @param id the id of the facilityParty to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facility-parties/{id}")
    public ResponseEntity<Void> deleteFacilityParty(@PathVariable Long id) {
        log.debug("REST request to delete FacilityParty : {}", id);
        facilityPartyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
