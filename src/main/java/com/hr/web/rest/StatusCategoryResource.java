package com.hr.web.rest;

import com.hr.domain.StatusCategory;
import com.hr.service.StatusCategoryService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.StatusCategoryCriteria;
import com.hr.service.StatusCategoryQueryService;

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
 * REST controller for managing {@link com.hr.domain.StatusCategory}.
 */
@RestController
@RequestMapping("/api")
public class StatusCategoryResource {

    private final Logger log = LoggerFactory.getLogger(StatusCategoryResource.class);

    private static final String ENTITY_NAME = "statusCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusCategoryService statusCategoryService;

    private final StatusCategoryQueryService statusCategoryQueryService;

    public StatusCategoryResource(StatusCategoryService statusCategoryService, StatusCategoryQueryService statusCategoryQueryService) {
        this.statusCategoryService = statusCategoryService;
        this.statusCategoryQueryService = statusCategoryQueryService;
    }

    /**
     * {@code POST  /status-categories} : Create a new statusCategory.
     *
     * @param statusCategory the statusCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusCategory, or with status {@code 400 (Bad Request)} if the statusCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-categories")
    public ResponseEntity<StatusCategory> createStatusCategory(@Valid @RequestBody StatusCategory statusCategory) throws URISyntaxException {
        log.debug("REST request to save StatusCategory : {}", statusCategory);
        if (statusCategory.getId() != null) {
            throw new BadRequestAlertException("A new statusCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusCategory result = statusCategoryService.save(statusCategory);
        return ResponseEntity.created(new URI("/api/status-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-categories} : Updates an existing statusCategory.
     *
     * @param statusCategory the statusCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusCategory,
     * or with status {@code 400 (Bad Request)} if the statusCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-categories")
    public ResponseEntity<StatusCategory> updateStatusCategory(@Valid @RequestBody StatusCategory statusCategory) throws URISyntaxException {
        log.debug("REST request to update StatusCategory : {}", statusCategory);
        if (statusCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatusCategory result = statusCategoryService.save(statusCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /status-categories} : get all the statusCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusCategories in body.
     */
    @GetMapping("/status-categories")
    public ResponseEntity<List<StatusCategory>> getAllStatusCategories(StatusCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StatusCategories by criteria: {}", criteria);
        Page<StatusCategory> page = statusCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /status-categories/count} : count all the statusCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/status-categories/count")
    public ResponseEntity<Long> countStatusCategories(StatusCategoryCriteria criteria) {
        log.debug("REST request to count StatusCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(statusCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /status-categories/:id} : get the "id" statusCategory.
     *
     * @param id the id of the statusCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-categories/{id}")
    public ResponseEntity<StatusCategory> getStatusCategory(@PathVariable Long id) {
        log.debug("REST request to get StatusCategory : {}", id);
        Optional<StatusCategory> statusCategory = statusCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusCategory);
    }

    /**
     * {@code DELETE  /status-categories/:id} : delete the "id" statusCategory.
     *
     * @param id the id of the statusCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-categories/{id}")
    public ResponseEntity<Void> deleteStatusCategory(@PathVariable Long id) {
        log.debug("REST request to delete StatusCategory : {}", id);
        statusCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
