package com.hr.web.rest;

import com.hr.domain.EmploymentApp;
import com.hr.repository.EmploymentAppRepository;
import com.hr.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.EmploymentApp}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmploymentAppResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentAppResource.class);

    private static final String ENTITY_NAME = "employmentApp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploymentAppRepository employmentAppRepository;

    public EmploymentAppResource(EmploymentAppRepository employmentAppRepository) {
        this.employmentAppRepository = employmentAppRepository;
    }

    /**
     * {@code POST  /employment-apps} : Create a new employmentApp.
     *
     * @param employmentApp the employmentApp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employmentApp, or with status {@code 400 (Bad Request)} if the employmentApp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employment-apps")
    public ResponseEntity<EmploymentApp> createEmploymentApp(@RequestBody EmploymentApp employmentApp) throws URISyntaxException {
        log.debug("REST request to save EmploymentApp : {}", employmentApp);
        if (employmentApp.getId() != null) {
            throw new BadRequestAlertException("A new employmentApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmploymentApp result = employmentAppRepository.save(employmentApp);
        return ResponseEntity.created(new URI("/api/employment-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employment-apps} : Updates an existing employmentApp.
     *
     * @param employmentApp the employmentApp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentApp,
     * or with status {@code 400 (Bad Request)} if the employmentApp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employmentApp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employment-apps")
    public ResponseEntity<EmploymentApp> updateEmploymentApp(@RequestBody EmploymentApp employmentApp) throws URISyntaxException {
        log.debug("REST request to update EmploymentApp : {}", employmentApp);
        if (employmentApp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmploymentApp result = employmentAppRepository.save(employmentApp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employmentApp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employment-apps} : get all the employmentApps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employmentApps in body.
     */
    @GetMapping("/employment-apps")
    public List<EmploymentApp> getAllEmploymentApps() {
        log.debug("REST request to get all EmploymentApps");
        return employmentAppRepository.findAll();
    }

    /**
     * {@code GET  /employment-apps/:id} : get the "id" employmentApp.
     *
     * @param id the id of the employmentApp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employmentApp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employment-apps/{id}")
    public ResponseEntity<EmploymentApp> getEmploymentApp(@PathVariable Long id) {
        log.debug("REST request to get EmploymentApp : {}", id);
        Optional<EmploymentApp> employmentApp = employmentAppRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employmentApp);
    }

    /**
     * {@code DELETE  /employment-apps/:id} : delete the "id" employmentApp.
     *
     * @param id the id of the employmentApp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employment-apps/{id}")
    public ResponseEntity<Void> deleteEmploymentApp(@PathVariable Long id) {
        log.debug("REST request to delete EmploymentApp : {}", id);
        employmentAppRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
