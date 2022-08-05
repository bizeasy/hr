package com.hr.web.rest;

import com.hr.domain.StatusValidChange;
import com.hr.service.StatusValidChangeService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.StatusValidChangeCriteria;
import com.hr.service.StatusValidChangeQueryService;

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
 * REST controller for managing {@link com.hr.domain.StatusValidChange}.
 */
@RestController
@RequestMapping("/api")
public class StatusValidChangeResource {

    private final Logger log = LoggerFactory.getLogger(StatusValidChangeResource.class);

    private static final String ENTITY_NAME = "statusValidChange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusValidChangeService statusValidChangeService;

    private final StatusValidChangeQueryService statusValidChangeQueryService;

    public StatusValidChangeResource(StatusValidChangeService statusValidChangeService, StatusValidChangeQueryService statusValidChangeQueryService) {
        this.statusValidChangeService = statusValidChangeService;
        this.statusValidChangeQueryService = statusValidChangeQueryService;
    }

    /**
     * {@code POST  /status-valid-changes} : Create a new statusValidChange.
     *
     * @param statusValidChange the statusValidChange to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusValidChange, or with status {@code 400 (Bad Request)} if the statusValidChange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-valid-changes")
    public ResponseEntity<StatusValidChange> createStatusValidChange(@Valid @RequestBody StatusValidChange statusValidChange) throws URISyntaxException {
        log.debug("REST request to save StatusValidChange : {}", statusValidChange);
        if (statusValidChange.getId() != null) {
            throw new BadRequestAlertException("A new statusValidChange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusValidChange result = statusValidChangeService.save(statusValidChange);
        return ResponseEntity.created(new URI("/api/status-valid-changes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-valid-changes} : Updates an existing statusValidChange.
     *
     * @param statusValidChange the statusValidChange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusValidChange,
     * or with status {@code 400 (Bad Request)} if the statusValidChange is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusValidChange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-valid-changes")
    public ResponseEntity<StatusValidChange> updateStatusValidChange(@Valid @RequestBody StatusValidChange statusValidChange) throws URISyntaxException {
        log.debug("REST request to update StatusValidChange : {}", statusValidChange);
        if (statusValidChange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatusValidChange result = statusValidChangeService.save(statusValidChange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusValidChange.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /status-valid-changes} : get all the statusValidChanges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusValidChanges in body.
     */
    @GetMapping("/status-valid-changes")
    public ResponseEntity<List<StatusValidChange>> getAllStatusValidChanges(StatusValidChangeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StatusValidChanges by criteria: {}", criteria);
        Page<StatusValidChange> page = statusValidChangeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /status-valid-changes/count} : count all the statusValidChanges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/status-valid-changes/count")
    public ResponseEntity<Long> countStatusValidChanges(StatusValidChangeCriteria criteria) {
        log.debug("REST request to count StatusValidChanges by criteria: {}", criteria);
        return ResponseEntity.ok().body(statusValidChangeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /status-valid-changes/:id} : get the "id" statusValidChange.
     *
     * @param id the id of the statusValidChange to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusValidChange, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-valid-changes/{id}")
    public ResponseEntity<StatusValidChange> getStatusValidChange(@PathVariable Long id) {
        log.debug("REST request to get StatusValidChange : {}", id);
        Optional<StatusValidChange> statusValidChange = statusValidChangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusValidChange);
    }

    /**
     * {@code DELETE  /status-valid-changes/:id} : delete the "id" statusValidChange.
     *
     * @param id the id of the statusValidChange to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-valid-changes/{id}")
    public ResponseEntity<Void> deleteStatusValidChange(@PathVariable Long id) {
        log.debug("REST request to delete StatusValidChange : {}", id);
        statusValidChangeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
