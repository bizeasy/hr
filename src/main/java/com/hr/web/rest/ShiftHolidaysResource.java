package com.hr.web.rest;

import com.hr.domain.ShiftHolidays;
import com.hr.repository.ShiftHolidaysRepository;
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
 * REST controller for managing {@link com.hr.domain.ShiftHolidays}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShiftHolidaysResource {

    private final Logger log = LoggerFactory.getLogger(ShiftHolidaysResource.class);

    private static final String ENTITY_NAME = "shiftHolidays";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShiftHolidaysRepository shiftHolidaysRepository;

    public ShiftHolidaysResource(ShiftHolidaysRepository shiftHolidaysRepository) {
        this.shiftHolidaysRepository = shiftHolidaysRepository;
    }

    /**
     * {@code POST  /shift-holidays} : Create a new shiftHolidays.
     *
     * @param shiftHolidays the shiftHolidays to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shiftHolidays, or with status {@code 400 (Bad Request)} if the shiftHolidays has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shift-holidays")
    public ResponseEntity<ShiftHolidays> createShiftHolidays(@RequestBody ShiftHolidays shiftHolidays) throws URISyntaxException {
        log.debug("REST request to save ShiftHolidays : {}", shiftHolidays);
        if (shiftHolidays.getId() != null) {
            throw new BadRequestAlertException("A new shiftHolidays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShiftHolidays result = shiftHolidaysRepository.save(shiftHolidays);
        return ResponseEntity.created(new URI("/api/shift-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shift-holidays} : Updates an existing shiftHolidays.
     *
     * @param shiftHolidays the shiftHolidays to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shiftHolidays,
     * or with status {@code 400 (Bad Request)} if the shiftHolidays is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shiftHolidays couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shift-holidays")
    public ResponseEntity<ShiftHolidays> updateShiftHolidays(@RequestBody ShiftHolidays shiftHolidays) throws URISyntaxException {
        log.debug("REST request to update ShiftHolidays : {}", shiftHolidays);
        if (shiftHolidays.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShiftHolidays result = shiftHolidaysRepository.save(shiftHolidays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shiftHolidays.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shift-holidays} : get all the shiftHolidays.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shiftHolidays in body.
     */
    @GetMapping("/shift-holidays")
    public List<ShiftHolidays> getAllShiftHolidays() {
        log.debug("REST request to get all ShiftHolidays");
        return shiftHolidaysRepository.findAll();
    }

    /**
     * {@code GET  /shift-holidays/:id} : get the "id" shiftHolidays.
     *
     * @param id the id of the shiftHolidays to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shiftHolidays, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shift-holidays/{id}")
    public ResponseEntity<ShiftHolidays> getShiftHolidays(@PathVariable Long id) {
        log.debug("REST request to get ShiftHolidays : {}", id);
        Optional<ShiftHolidays> shiftHolidays = shiftHolidaysRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shiftHolidays);
    }

    /**
     * {@code DELETE  /shift-holidays/:id} : delete the "id" shiftHolidays.
     *
     * @param id the id of the shiftHolidays to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shift-holidays/{id}")
    public ResponseEntity<Void> deleteShiftHolidays(@PathVariable Long id) {
        log.debug("REST request to delete ShiftHolidays : {}", id);
        shiftHolidaysRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
