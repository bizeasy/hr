package com.hr.web.rest;

import com.hr.domain.EmplPositionTypeRate;
import com.hr.repository.EmplPositionTypeRateRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplPositionTypeRate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplPositionTypeRateResource {

    private final Logger log = LoggerFactory.getLogger(EmplPositionTypeRateResource.class);

    private static final String ENTITY_NAME = "emplPositionTypeRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplPositionTypeRateRepository emplPositionTypeRateRepository;

    public EmplPositionTypeRateResource(EmplPositionTypeRateRepository emplPositionTypeRateRepository) {
        this.emplPositionTypeRateRepository = emplPositionTypeRateRepository;
    }

    /**
     * {@code POST  /empl-position-type-rates} : Create a new emplPositionTypeRate.
     *
     * @param emplPositionTypeRate the emplPositionTypeRate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplPositionTypeRate, or with status {@code 400 (Bad Request)} if the emplPositionTypeRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-position-type-rates")
    public ResponseEntity<EmplPositionTypeRate> createEmplPositionTypeRate(@RequestBody EmplPositionTypeRate emplPositionTypeRate) throws URISyntaxException {
        log.debug("REST request to save EmplPositionTypeRate : {}", emplPositionTypeRate);
        if (emplPositionTypeRate.getId() != null) {
            throw new BadRequestAlertException("A new emplPositionTypeRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplPositionTypeRate result = emplPositionTypeRateRepository.save(emplPositionTypeRate);
        return ResponseEntity.created(new URI("/api/empl-position-type-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-position-type-rates} : Updates an existing emplPositionTypeRate.
     *
     * @param emplPositionTypeRate the emplPositionTypeRate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplPositionTypeRate,
     * or with status {@code 400 (Bad Request)} if the emplPositionTypeRate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplPositionTypeRate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-position-type-rates")
    public ResponseEntity<EmplPositionTypeRate> updateEmplPositionTypeRate(@RequestBody EmplPositionTypeRate emplPositionTypeRate) throws URISyntaxException {
        log.debug("REST request to update EmplPositionTypeRate : {}", emplPositionTypeRate);
        if (emplPositionTypeRate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplPositionTypeRate result = emplPositionTypeRateRepository.save(emplPositionTypeRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplPositionTypeRate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-position-type-rates} : get all the emplPositionTypeRates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplPositionTypeRates in body.
     */
    @GetMapping("/empl-position-type-rates")
    public List<EmplPositionTypeRate> getAllEmplPositionTypeRates() {
        log.debug("REST request to get all EmplPositionTypeRates");
        return emplPositionTypeRateRepository.findAll();
    }

    /**
     * {@code GET  /empl-position-type-rates/:id} : get the "id" emplPositionTypeRate.
     *
     * @param id the id of the emplPositionTypeRate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplPositionTypeRate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-position-type-rates/{id}")
    public ResponseEntity<EmplPositionTypeRate> getEmplPositionTypeRate(@PathVariable Long id) {
        log.debug("REST request to get EmplPositionTypeRate : {}", id);
        Optional<EmplPositionTypeRate> emplPositionTypeRate = emplPositionTypeRateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplPositionTypeRate);
    }

    /**
     * {@code DELETE  /empl-position-type-rates/:id} : delete the "id" emplPositionTypeRate.
     *
     * @param id the id of the emplPositionTypeRate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-position-type-rates/{id}")
    public ResponseEntity<Void> deleteEmplPositionTypeRate(@PathVariable Long id) {
        log.debug("REST request to delete EmplPositionTypeRate : {}", id);
        emplPositionTypeRateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
