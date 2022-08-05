package com.hr.web.rest;

import com.hr.domain.Reason;
import com.hr.service.ReasonService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.ReasonCriteria;
import com.hr.service.ReasonQueryService;

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
 * REST controller for managing {@link com.hr.domain.Reason}.
 */
@RestController
@RequestMapping("/api")
public class ReasonResource {

    private final Logger log = LoggerFactory.getLogger(ReasonResource.class);

    private static final String ENTITY_NAME = "reason";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReasonService reasonService;

    private final ReasonQueryService reasonQueryService;

    public ReasonResource(ReasonService reasonService, ReasonQueryService reasonQueryService) {
        this.reasonService = reasonService;
        this.reasonQueryService = reasonQueryService;
    }

    /**
     * {@code POST  /reasons} : Create a new reason.
     *
     * @param reason the reason to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reason, or with status {@code 400 (Bad Request)} if the reason has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reasons")
    public ResponseEntity<Reason> createReason(@Valid @RequestBody Reason reason) throws URISyntaxException {
        log.debug("REST request to save Reason : {}", reason);
        if (reason.getId() != null) {
            throw new BadRequestAlertException("A new reason cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reason result = reasonService.save(reason);
        return ResponseEntity.created(new URI("/api/reasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reasons} : Updates an existing reason.
     *
     * @param reason the reason to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reason,
     * or with status {@code 400 (Bad Request)} if the reason is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reason couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reasons")
    public ResponseEntity<Reason> updateReason(@Valid @RequestBody Reason reason) throws URISyntaxException {
        log.debug("REST request to update Reason : {}", reason);
        if (reason.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reason result = reasonService.save(reason);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reason.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reasons} : get all the reasons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reasons in body.
     */
    @GetMapping("/reasons")
    public ResponseEntity<List<Reason>> getAllReasons(ReasonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Reasons by criteria: {}", criteria);
        Page<Reason> page = reasonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reasons/count} : count all the reasons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reasons/count")
    public ResponseEntity<Long> countReasons(ReasonCriteria criteria) {
        log.debug("REST request to count Reasons by criteria: {}", criteria);
        return ResponseEntity.ok().body(reasonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reasons/:id} : get the "id" reason.
     *
     * @param id the id of the reason to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reason, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reasons/{id}")
    public ResponseEntity<Reason> getReason(@PathVariable Long id) {
        log.debug("REST request to get Reason : {}", id);
        Optional<Reason> reason = reasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reason);
    }

    /**
     * {@code DELETE  /reasons/:id} : delete the "id" reason.
     *
     * @param id the id of the reason to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reasons/{id}")
    public ResponseEntity<Void> deleteReason(@PathVariable Long id) {
        log.debug("REST request to delete Reason : {}", id);
        reasonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
