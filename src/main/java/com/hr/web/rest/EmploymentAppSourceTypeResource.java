package com.hr.web.rest;

import com.hr.domain.EmploymentAppSourceType;
import com.hr.repository.EmploymentAppSourceTypeRepository;
import com.hr.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.EmploymentAppSourceType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmploymentAppSourceTypeResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentAppSourceTypeResource.class);

    private static final String ENTITY_NAME = "employmentAppSourceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploymentAppSourceTypeRepository employmentAppSourceTypeRepository;

    public EmploymentAppSourceTypeResource(EmploymentAppSourceTypeRepository employmentAppSourceTypeRepository) {
        this.employmentAppSourceTypeRepository = employmentAppSourceTypeRepository;
    }

    /**
     * {@code POST  /employment-app-source-types} : Create a new employmentAppSourceType.
     *
     * @param employmentAppSourceType the employmentAppSourceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employmentAppSourceType, or with status {@code 400 (Bad Request)} if the employmentAppSourceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employment-app-source-types")
    public ResponseEntity<EmploymentAppSourceType> createEmploymentAppSourceType(@Valid @RequestBody EmploymentAppSourceType employmentAppSourceType) throws URISyntaxException {
        log.debug("REST request to save EmploymentAppSourceType : {}", employmentAppSourceType);
        if (employmentAppSourceType.getId() != null) {
            throw new BadRequestAlertException("A new employmentAppSourceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmploymentAppSourceType result = employmentAppSourceTypeRepository.save(employmentAppSourceType);
        return ResponseEntity.created(new URI("/api/employment-app-source-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employment-app-source-types} : Updates an existing employmentAppSourceType.
     *
     * @param employmentAppSourceType the employmentAppSourceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentAppSourceType,
     * or with status {@code 400 (Bad Request)} if the employmentAppSourceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employmentAppSourceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employment-app-source-types")
    public ResponseEntity<EmploymentAppSourceType> updateEmploymentAppSourceType(@Valid @RequestBody EmploymentAppSourceType employmentAppSourceType) throws URISyntaxException {
        log.debug("REST request to update EmploymentAppSourceType : {}", employmentAppSourceType);
        if (employmentAppSourceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmploymentAppSourceType result = employmentAppSourceTypeRepository.save(employmentAppSourceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employmentAppSourceType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employment-app-source-types} : get all the employmentAppSourceTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employmentAppSourceTypes in body.
     */
    @GetMapping("/employment-app-source-types")
    public List<EmploymentAppSourceType> getAllEmploymentAppSourceTypes() {
        log.debug("REST request to get all EmploymentAppSourceTypes");
        return employmentAppSourceTypeRepository.findAll();
    }

    /**
     * {@code GET  /employment-app-source-types/:id} : get the "id" employmentAppSourceType.
     *
     * @param id the id of the employmentAppSourceType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employmentAppSourceType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employment-app-source-types/{id}")
    public ResponseEntity<EmploymentAppSourceType> getEmploymentAppSourceType(@PathVariable Long id) {
        log.debug("REST request to get EmploymentAppSourceType : {}", id);
        Optional<EmploymentAppSourceType> employmentAppSourceType = employmentAppSourceTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employmentAppSourceType);
    }

    /**
     * {@code DELETE  /employment-app-source-types/:id} : delete the "id" employmentAppSourceType.
     *
     * @param id the id of the employmentAppSourceType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employment-app-source-types/{id}")
    public ResponseEntity<Void> deleteEmploymentAppSourceType(@PathVariable Long id) {
        log.debug("REST request to delete EmploymentAppSourceType : {}", id);
        employmentAppSourceTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
