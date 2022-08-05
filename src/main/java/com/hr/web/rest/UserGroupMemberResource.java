package com.hr.web.rest;

import com.hr.domain.UserGroupMember;
import com.hr.service.UserGroupMemberService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.UserGroupMemberCriteria;
import com.hr.service.UserGroupMemberQueryService;

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
 * REST controller for managing {@link com.hr.domain.UserGroupMember}.
 */
@RestController
@RequestMapping("/api")
public class UserGroupMemberResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupMemberResource.class);

    private static final String ENTITY_NAME = "userGroupMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupMemberService userGroupMemberService;

    private final UserGroupMemberQueryService userGroupMemberQueryService;

    public UserGroupMemberResource(UserGroupMemberService userGroupMemberService, UserGroupMemberQueryService userGroupMemberQueryService) {
        this.userGroupMemberService = userGroupMemberService;
        this.userGroupMemberQueryService = userGroupMemberQueryService;
    }

    /**
     * {@code POST  /user-group-members} : Create a new userGroupMember.
     *
     * @param userGroupMember the userGroupMember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroupMember, or with status {@code 400 (Bad Request)} if the userGroupMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-group-members")
    public ResponseEntity<UserGroupMember> createUserGroupMember(@Valid @RequestBody UserGroupMember userGroupMember) throws URISyntaxException {
        log.debug("REST request to save UserGroupMember : {}", userGroupMember);
        if (userGroupMember.getId() != null) {
            throw new BadRequestAlertException("A new userGroupMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroupMember result = userGroupMemberService.save(userGroupMember);
        return ResponseEntity.created(new URI("/api/user-group-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-group-members} : Updates an existing userGroupMember.
     *
     * @param userGroupMember the userGroupMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupMember,
     * or with status {@code 400 (Bad Request)} if the userGroupMember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroupMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-group-members")
    public ResponseEntity<UserGroupMember> updateUserGroupMember(@Valid @RequestBody UserGroupMember userGroupMember) throws URISyntaxException {
        log.debug("REST request to update UserGroupMember : {}", userGroupMember);
        if (userGroupMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGroupMember result = userGroupMemberService.save(userGroupMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGroupMember.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-group-members} : get all the userGroupMembers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroupMembers in body.
     */
    @GetMapping("/user-group-members")
    public ResponseEntity<List<UserGroupMember>> getAllUserGroupMembers(UserGroupMemberCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserGroupMembers by criteria: {}", criteria);
        Page<UserGroupMember> page = userGroupMemberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-group-members/count} : count all the userGroupMembers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-group-members/count")
    public ResponseEntity<Long> countUserGroupMembers(UserGroupMemberCriteria criteria) {
        log.debug("REST request to count UserGroupMembers by criteria: {}", criteria);
        return ResponseEntity.ok().body(userGroupMemberQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-group-members/:id} : get the "id" userGroupMember.
     *
     * @param id the id of the userGroupMember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroupMember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-group-members/{id}")
    public ResponseEntity<UserGroupMember> getUserGroupMember(@PathVariable Long id) {
        log.debug("REST request to get UserGroupMember : {}", id);
        Optional<UserGroupMember> userGroupMember = userGroupMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGroupMember);
    }

    /**
     * {@code DELETE  /user-group-members/:id} : delete the "id" userGroupMember.
     *
     * @param id the id of the userGroupMember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-group-members/{id}")
    public ResponseEntity<Void> deleteUserGroupMember(@PathVariable Long id) {
        log.debug("REST request to delete UserGroupMember : {}", id);
        userGroupMemberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
