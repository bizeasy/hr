package com.hr.web.rest;

import com.hr.domain.HolidayType;
import com.hr.repository.HolidayTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.HolidayType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HolidayTypeResource {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeResource.class);

    private static final String ENTITY_NAME = "holidayType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HolidayTypeRepository holidayTypeRepository;

    public HolidayTypeResource(HolidayTypeRepository holidayTypeRepository) {
        this.holidayTypeRepository = holidayTypeRepository;
    }

    /**
     * {@code POST  /holiday-types} : Create a new holidayType.
     *
     * @param holidayType the holidayType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holidayType, or with status {@code 400 (Bad Request)} if the holidayType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holiday-types")
    public ResponseEntity<HolidayType> createHolidayType(@Valid @RequestBody HolidayType holidayType) throws URISyntaxException {
        log.debug("REST request to save HolidayType : {}", holidayType);
        if (holidayType.getId() != null) {
            throw new BadRequestAlertException("A new holidayType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidayType result = holidayTypeRepository.save(holidayType);
        return ResponseEntity.created(new URI("/api/holiday-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holiday-types} : Updates an existing holidayType.
     *
     * @param holidayType the holidayType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holidayType,
     * or with status {@code 400 (Bad Request)} if the holidayType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holidayType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holiday-types")
    public ResponseEntity<HolidayType> updateHolidayType(@Valid @RequestBody HolidayType holidayType) throws URISyntaxException {
        log.debug("REST request to update HolidayType : {}", holidayType);
        if (holidayType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HolidayType result = holidayTypeRepository.save(holidayType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holidayType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /holiday-types} : get all the holidayTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holidayTypes in body.
     */
    @GetMapping("/holiday-types")
    public List<HolidayType> getAllHolidayTypes() {
        log.debug("REST request to get all HolidayTypes");
        return holidayTypeRepository.findAll();
    }

    /**
     * {@code GET  /holiday-types/:id} : get the "id" holidayType.
     *
     * @param id the id of the holidayType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holidayType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holiday-types/{id}")
    public ResponseEntity<HolidayType> getHolidayType(@PathVariable Long id) {
        log.debug("REST request to get HolidayType : {}", id);
        Optional<HolidayType> holidayType = holidayTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(holidayType);
    }

    /**
     * {@code DELETE  /holiday-types/:id} : delete the "id" holidayType.
     *
     * @param id the id of the holidayType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holiday-types/{id}")
    public ResponseEntity<Void> deleteHolidayType(@PathVariable Long id) {
        log.debug("REST request to delete HolidayType : {}", id);
        holidayTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
