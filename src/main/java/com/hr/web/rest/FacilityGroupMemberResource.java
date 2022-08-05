package com.hr.web.rest;

import com.hr.domain.FacilityGroupMember;
import com.hr.service.FacilityGroupMemberService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.FacilityGroupMemberCriteria;
import com.hr.service.FacilityGroupMemberQueryService;

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
 * REST controller for managing {@link com.hr.domain.FacilityGroupMember}.
 */
@RestController
@RequestMapping("/api")
public class FacilityGroupMemberResource {

    private final Logger log = LoggerFactory.getLogger(FacilityGroupMemberResource.class);

    private static final String ENTITY_NAME = "facilityGroupMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityGroupMemberService facilityGroupMemberService;

    private final FacilityGroupMemberQueryService facilityGroupMemberQueryService;

    public FacilityGroupMemberResource(FacilityGroupMemberService facilityGroupMemberService, FacilityGroupMemberQueryService facilityGroupMemberQueryService) {
        this.facilityGroupMemberService = facilityGroupMemberService;
        this.facilityGroupMemberQueryService = facilityGroupMemberQueryService;
    }

    /**
     * {@code POST  /facility-group-members} : Create a new facilityGroupMember.
     *
     * @param facilityGroupMember the facilityGroupMember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityGroupMember, or with status {@code 400 (Bad Request)} if the facilityGroupMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facility-group-members")
    public ResponseEntity<FacilityGroupMember> createFacilityGroupMember(@RequestBody FacilityGroupMember facilityGroupMember) throws URISyntaxException {
        log.debug("REST request to save FacilityGroupMember : {}", facilityGroupMember);
        if (facilityGroupMember.getId() != null) {
            throw new BadRequestAlertException("A new facilityGroupMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityGroupMember result = facilityGroupMemberService.save(facilityGroupMember);
        return ResponseEntity.created(new URI("/api/facility-group-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facility-group-members} : Updates an existing facilityGroupMember.
     *
     * @param facilityGroupMember the facilityGroupMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityGroupMember,
     * or with status {@code 400 (Bad Request)} if the facilityGroupMember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityGroupMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facility-group-members")
    public ResponseEntity<FacilityGroupMember> updateFacilityGroupMember(@RequestBody FacilityGroupMember facilityGroupMember) throws URISyntaxException {
        log.debug("REST request to update FacilityGroupMember : {}", facilityGroupMember);
        if (facilityGroupMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityGroupMember result = facilityGroupMemberService.save(facilityGroupMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityGroupMember.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facility-group-members} : get all the facilityGroupMembers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilityGroupMembers in body.
     */
    @GetMapping("/facility-group-members")
    public ResponseEntity<List<FacilityGroupMember>> getAllFacilityGroupMembers(FacilityGroupMemberCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FacilityGroupMembers by criteria: {}", criteria);
        Page<FacilityGroupMember> page = facilityGroupMemberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facility-group-members/count} : count all the facilityGroupMembers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/facility-group-members/count")
    public ResponseEntity<Long> countFacilityGroupMembers(FacilityGroupMemberCriteria criteria) {
        log.debug("REST request to count FacilityGroupMembers by criteria: {}", criteria);
        return ResponseEntity.ok().body(facilityGroupMemberQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /facility-group-members/:id} : get the "id" facilityGroupMember.
     *
     * @param id the id of the facilityGroupMember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityGroupMember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facility-group-members/{id}")
    public ResponseEntity<FacilityGroupMember> getFacilityGroupMember(@PathVariable Long id) {
        log.debug("REST request to get FacilityGroupMember : {}", id);
        Optional<FacilityGroupMember> facilityGroupMember = facilityGroupMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facilityGroupMember);
    }

    /**
     * {@code DELETE  /facility-group-members/:id} : delete the "id" facilityGroupMember.
     *
     * @param id the id of the facilityGroupMember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facility-group-members/{id}")
    public ResponseEntity<Void> deleteFacilityGroupMember(@PathVariable Long id) {
        log.debug("REST request to delete FacilityGroupMember : {}", id);
        facilityGroupMemberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
