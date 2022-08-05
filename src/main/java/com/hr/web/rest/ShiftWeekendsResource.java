package com.hr.web.rest;

import com.hr.domain.ShiftWeekends;
import com.hr.repository.ShiftWeekendsRepository;
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
 * REST controller for managing {@link com.hr.domain.ShiftWeekends}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShiftWeekendsResource {

    private final Logger log = LoggerFactory.getLogger(ShiftWeekendsResource.class);

    private static final String ENTITY_NAME = "shiftWeekends";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShiftWeekendsRepository shiftWeekendsRepository;

    public ShiftWeekendsResource(ShiftWeekendsRepository shiftWeekendsRepository) {
        this.shiftWeekendsRepository = shiftWeekendsRepository;
    }

    /**
     * {@code POST  /shift-weekends} : Create a new shiftWeekends.
     *
     * @param shiftWeekends the shiftWeekends to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shiftWeekends, or with status {@code 400 (Bad Request)} if the shiftWeekends has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shift-weekends")
    public ResponseEntity<ShiftWeekends> createShiftWeekends(@RequestBody ShiftWeekends shiftWeekends) throws URISyntaxException {
        log.debug("REST request to save ShiftWeekends : {}", shiftWeekends);
        if (shiftWeekends.getId() != null) {
            throw new BadRequestAlertException("A new shiftWeekends cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShiftWeekends result = shiftWeekendsRepository.save(shiftWeekends);
        return ResponseEntity.created(new URI("/api/shift-weekends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shift-weekends} : Updates an existing shiftWeekends.
     *
     * @param shiftWeekends the shiftWeekends to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shiftWeekends,
     * or with status {@code 400 (Bad Request)} if the shiftWeekends is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shiftWeekends couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shift-weekends")
    public ResponseEntity<ShiftWeekends> updateShiftWeekends(@RequestBody ShiftWeekends shiftWeekends) throws URISyntaxException {
        log.debug("REST request to update ShiftWeekends : {}", shiftWeekends);
        if (shiftWeekends.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShiftWeekends result = shiftWeekendsRepository.save(shiftWeekends);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shiftWeekends.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shift-weekends} : get all the shiftWeekends.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shiftWeekends in body.
     */
    @GetMapping("/shift-weekends")
    public List<ShiftWeekends> getAllShiftWeekends() {
        log.debug("REST request to get all ShiftWeekends");
        return shiftWeekendsRepository.findAll();
    }

    /**
     * {@code GET  /shift-weekends/:id} : get the "id" shiftWeekends.
     *
     * @param id the id of the shiftWeekends to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shiftWeekends, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shift-weekends/{id}")
    public ResponseEntity<ShiftWeekends> getShiftWeekends(@PathVariable Long id) {
        log.debug("REST request to get ShiftWeekends : {}", id);
        Optional<ShiftWeekends> shiftWeekends = shiftWeekendsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shiftWeekends);
    }

    /**
     * {@code DELETE  /shift-weekends/:id} : delete the "id" shiftWeekends.
     *
     * @param id the id of the shiftWeekends to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shift-weekends/{id}")
    public ResponseEntity<Void> deleteShiftWeekends(@PathVariable Long id) {
        log.debug("REST request to delete ShiftWeekends : {}", id);
        shiftWeekendsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
