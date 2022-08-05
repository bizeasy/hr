package com.hr.web.rest;

import com.hr.domain.PermissionAuthority;
import com.hr.service.PermissionAuthorityService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.PermissionAuthorityCriteria;
import com.hr.service.PermissionAuthorityQueryService;

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
 * REST controller for managing {@link com.hr.domain.PermissionAuthority}.
 */
@RestController
@RequestMapping("/api")
public class PermissionAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(PermissionAuthorityResource.class);

    private static final String ENTITY_NAME = "permissionAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermissionAuthorityService permissionAuthorityService;

    private final PermissionAuthorityQueryService permissionAuthorityQueryService;

    public PermissionAuthorityResource(PermissionAuthorityService permissionAuthorityService, PermissionAuthorityQueryService permissionAuthorityQueryService) {
        this.permissionAuthorityService = permissionAuthorityService;
        this.permissionAuthorityQueryService = permissionAuthorityQueryService;
    }

    /**
     * {@code POST  /permission-authorities} : Create a new permissionAuthority.
     *
     * @param permissionAuthority the permissionAuthority to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permissionAuthority, or with status {@code 400 (Bad Request)} if the permissionAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/permission-authorities")
    public ResponseEntity<PermissionAuthority> createPermissionAuthority(@RequestBody PermissionAuthority permissionAuthority) throws URISyntaxException {
        log.debug("REST request to save PermissionAuthority : {}", permissionAuthority);
        if (permissionAuthority.getId() != null) {
            throw new BadRequestAlertException("A new permissionAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PermissionAuthority result = permissionAuthorityService.save(permissionAuthority);
        return ResponseEntity.created(new URI("/api/permission-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /permission-authorities} : Updates an existing permissionAuthority.
     *
     * @param permissionAuthority the permissionAuthority to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permissionAuthority,
     * or with status {@code 400 (Bad Request)} if the permissionAuthority is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permissionAuthority couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/permission-authorities")
    public ResponseEntity<PermissionAuthority> updatePermissionAuthority(@RequestBody PermissionAuthority permissionAuthority) throws URISyntaxException {
        log.debug("REST request to update PermissionAuthority : {}", permissionAuthority);
        if (permissionAuthority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PermissionAuthority result = permissionAuthorityService.save(permissionAuthority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permissionAuthority.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /permission-authorities} : get all the permissionAuthorities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permissionAuthorities in body.
     */
    @GetMapping("/permission-authorities")
    public ResponseEntity<List<PermissionAuthority>> getAllPermissionAuthorities(PermissionAuthorityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PermissionAuthorities by criteria: {}", criteria);
        Page<PermissionAuthority> page = permissionAuthorityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /permission-authorities/count} : count all the permissionAuthorities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/permission-authorities/count")
    public ResponseEntity<Long> countPermissionAuthorities(PermissionAuthorityCriteria criteria) {
        log.debug("REST request to count PermissionAuthorities by criteria: {}", criteria);
        return ResponseEntity.ok().body(permissionAuthorityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /permission-authorities/:id} : get the "id" permissionAuthority.
     *
     * @param id the id of the permissionAuthority to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permissionAuthority, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/permission-authorities/{id}")
    public ResponseEntity<PermissionAuthority> getPermissionAuthority(@PathVariable Long id) {
        log.debug("REST request to get PermissionAuthority : {}", id);
        Optional<PermissionAuthority> permissionAuthority = permissionAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permissionAuthority);
    }

    /**
     * {@code DELETE  /permission-authorities/:id} : delete the "id" permissionAuthority.
     *
     * @param id the id of the permissionAuthority to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/permission-authorities/{id}")
    public ResponseEntity<Void> deletePermissionAuthority(@PathVariable Long id) {
        log.debug("REST request to delete PermissionAuthority : {}", id);
        permissionAuthorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
