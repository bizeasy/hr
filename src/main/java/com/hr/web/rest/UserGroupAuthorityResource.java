package com.hr.web.rest;

import com.hr.domain.UserGroupAuthority;
import com.hr.service.UserGroupAuthorityService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.UserGroupAuthorityCriteria;
import com.hr.service.UserGroupAuthorityQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.UserGroupAuthority}.
 */
@RestController
@RequestMapping("/api")
public class UserGroupAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupAuthorityResource.class);

    private static final String ENTITY_NAME = "userGroupAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupAuthorityService userGroupAuthorityService;

    private final UserGroupAuthorityQueryService userGroupAuthorityQueryService;

    public UserGroupAuthorityResource(UserGroupAuthorityService userGroupAuthorityService, UserGroupAuthorityQueryService userGroupAuthorityQueryService) {
        this.userGroupAuthorityService = userGroupAuthorityService;
        this.userGroupAuthorityQueryService = userGroupAuthorityQueryService;
    }

    /**
     * {@code POST  /user-group-authorities} : Create a new userGroupAuthority.
     *
     * @param userGroupAuthority the userGroupAuthority to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroupAuthority, or with status {@code 400 (Bad Request)} if the userGroupAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-group-authorities")
    public ResponseEntity<UserGroupAuthority> createUserGroupAuthority(@Valid @RequestBody UserGroupAuthority userGroupAuthority) throws URISyntaxException {
        log.debug("REST request to save UserGroupAuthority : {}", userGroupAuthority);
        if (userGroupAuthority.getId() != null) {
            throw new BadRequestAlertException("A new userGroupAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroupAuthority result = userGroupAuthorityService.save(userGroupAuthority);
        return ResponseEntity.created(new URI("/api/user-group-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-group-authorities} : Updates an existing userGroupAuthority.
     *
     * @param userGroupAuthority the userGroupAuthority to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupAuthority,
     * or with status {@code 400 (Bad Request)} if the userGroupAuthority is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroupAuthority couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-group-authorities")
    public ResponseEntity<UserGroupAuthority> updateUserGroupAuthority(@Valid @RequestBody UserGroupAuthority userGroupAuthority) throws URISyntaxException {
        log.debug("REST request to update UserGroupAuthority : {}", userGroupAuthority);
        if (userGroupAuthority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGroupAuthority result = userGroupAuthorityService.save(userGroupAuthority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGroupAuthority.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-group-authorities} : get all the userGroupAuthorities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroupAuthorities in body.
     */
    @GetMapping("/user-group-authorities")
    public ResponseEntity<List<UserGroupAuthority>> getAllUserGroupAuthorities(UserGroupAuthorityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserGroupAuthorities by criteria: {}", criteria);
        Page<UserGroupAuthority> page = userGroupAuthorityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-group-authorities/count} : count all the userGroupAuthorities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-group-authorities/count")
    public ResponseEntity<Long> countUserGroupAuthorities(UserGroupAuthorityCriteria criteria) {
        log.debug("REST request to count UserGroupAuthorities by criteria: {}", criteria);
        return ResponseEntity.ok().body(userGroupAuthorityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-group-authorities/:id} : get the "id" userGroupAuthority.
     *
     * @param id the id of the userGroupAuthority to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroupAuthority, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-group-authorities/{id}")
    public ResponseEntity<UserGroupAuthority> getUserGroupAuthority(@PathVariable Long id) {
        log.debug("REST request to get UserGroupAuthority : {}", id);
        Optional<UserGroupAuthority> userGroupAuthority = userGroupAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGroupAuthority);
    }

    /**
     * {@code DELETE  /user-group-authorities/:id} : delete the "id" userGroupAuthority.
     *
     * @param id the id of the userGroupAuthority to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-group-authorities/{id}")
    public ResponseEntity<Void> deleteUserGroupAuthority(@PathVariable Long id) {
        log.debug("REST request to delete UserGroupAuthority : {}", id);
        userGroupAuthorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
