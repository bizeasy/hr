package com.hr.web.rest;

import com.hr.domain.PublicHolidays;
import com.hr.repository.PublicHolidaysRepository;
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
 * REST controller for managing {@link com.hr.domain.PublicHolidays}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PublicHolidaysResource {

    private final Logger log = LoggerFactory.getLogger(PublicHolidaysResource.class);

    private static final String ENTITY_NAME = "publicHolidays";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicHolidaysRepository publicHolidaysRepository;

    public PublicHolidaysResource(PublicHolidaysRepository publicHolidaysRepository) {
        this.publicHolidaysRepository = publicHolidaysRepository;
    }

    /**
     * {@code POST  /public-holidays} : Create a new publicHolidays.
     *
     * @param publicHolidays the publicHolidays to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicHolidays, or with status {@code 400 (Bad Request)} if the publicHolidays has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-holidays")
    public ResponseEntity<PublicHolidays> createPublicHolidays(@RequestBody PublicHolidays publicHolidays) throws URISyntaxException {
        log.debug("REST request to save PublicHolidays : {}", publicHolidays);
        if (publicHolidays.getId() != null) {
            throw new BadRequestAlertException("A new publicHolidays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicHolidays result = publicHolidaysRepository.save(publicHolidays);
        return ResponseEntity.created(new URI("/api/public-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-holidays} : Updates an existing publicHolidays.
     *
     * @param publicHolidays the publicHolidays to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicHolidays,
     * or with status {@code 400 (Bad Request)} if the publicHolidays is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicHolidays couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-holidays")
    public ResponseEntity<PublicHolidays> updatePublicHolidays(@RequestBody PublicHolidays publicHolidays) throws URISyntaxException {
        log.debug("REST request to update PublicHolidays : {}", publicHolidays);
        if (publicHolidays.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PublicHolidays result = publicHolidaysRepository.save(publicHolidays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicHolidays.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /public-holidays} : get all the publicHolidays.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicHolidays in body.
     */
    @GetMapping("/public-holidays")
    public List<PublicHolidays> getAllPublicHolidays() {
        log.debug("REST request to get all PublicHolidays");
        return publicHolidaysRepository.findAll();
    }

    /**
     * {@code GET  /public-holidays/:id} : get the "id" publicHolidays.
     *
     * @param id the id of the publicHolidays to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicHolidays, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-holidays/{id}")
    public ResponseEntity<PublicHolidays> getPublicHolidays(@PathVariable Long id) {
        log.debug("REST request to get PublicHolidays : {}", id);
        Optional<PublicHolidays> publicHolidays = publicHolidaysRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(publicHolidays);
    }

    /**
     * {@code DELETE  /public-holidays/:id} : delete the "id" publicHolidays.
     *
     * @param id the id of the publicHolidays to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-holidays/{id}")
    public ResponseEntity<Void> deletePublicHolidays(@PathVariable Long id) {
        log.debug("REST request to delete PublicHolidays : {}", id);
        publicHolidaysRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
