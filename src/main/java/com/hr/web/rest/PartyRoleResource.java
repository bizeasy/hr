package com.hr.web.rest;

import com.hr.domain.PartyRole;
import com.hr.service.PartyRoleService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.PartyRoleCriteria;
import com.hr.service.PartyRoleQueryService;

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
 * REST controller for managing {@link com.hr.domain.PartyRole}.
 */
@RestController
@RequestMapping("/api")
public class PartyRoleResource {

    private final Logger log = LoggerFactory.getLogger(PartyRoleResource.class);

    private static final String ENTITY_NAME = "partyRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRoleService partyRoleService;

    private final PartyRoleQueryService partyRoleQueryService;

    public PartyRoleResource(PartyRoleService partyRoleService, PartyRoleQueryService partyRoleQueryService) {
        this.partyRoleService = partyRoleService;
        this.partyRoleQueryService = partyRoleQueryService;
    }

    /**
     * {@code POST  /party-roles} : Create a new partyRole.
     *
     * @param partyRole the partyRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyRole, or with status {@code 400 (Bad Request)} if the partyRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-roles")
    public ResponseEntity<PartyRole> createPartyRole(@RequestBody PartyRole partyRole) throws URISyntaxException {
        log.debug("REST request to save PartyRole : {}", partyRole);
        if (partyRole.getId() != null) {
            throw new BadRequestAlertException("A new partyRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyRole result = partyRoleService.save(partyRole);
        return ResponseEntity.created(new URI("/api/party-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-roles} : Updates an existing partyRole.
     *
     * @param partyRole the partyRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRole,
     * or with status {@code 400 (Bad Request)} if the partyRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-roles")
    public ResponseEntity<PartyRole> updatePartyRole(@RequestBody PartyRole partyRole) throws URISyntaxException {
        log.debug("REST request to update PartyRole : {}", partyRole);
        if (partyRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyRole result = partyRoleService.save(partyRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-roles} : get all the partyRoles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyRoles in body.
     */
    @GetMapping("/party-roles")
    public ResponseEntity<List<PartyRole>> getAllPartyRoles(PartyRoleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PartyRoles by criteria: {}", criteria);
        Page<PartyRole> page = partyRoleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /party-roles/count} : count all the partyRoles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/party-roles/count")
    public ResponseEntity<Long> countPartyRoles(PartyRoleCriteria criteria) {
        log.debug("REST request to count PartyRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(partyRoleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /party-roles/:id} : get the "id" partyRole.
     *
     * @param id the id of the partyRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-roles/{id}")
    public ResponseEntity<PartyRole> getPartyRole(@PathVariable Long id) {
        log.debug("REST request to get PartyRole : {}", id);
        Optional<PartyRole> partyRole = partyRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyRole);
    }

    /**
     * {@code DELETE  /party-roles/:id} : delete the "id" partyRole.
     *
     * @param id the id of the partyRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-roles/{id}")
    public ResponseEntity<Void> deletePartyRole(@PathVariable Long id) {
        log.debug("REST request to delete PartyRole : {}", id);
        partyRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
