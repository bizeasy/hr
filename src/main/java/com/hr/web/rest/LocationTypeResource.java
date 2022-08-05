package com.hr.web.rest;

import com.hr.domain.LocationType;
import com.hr.repository.LocationTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.LocationType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LocationTypeResource {

    private final Logger log = LoggerFactory.getLogger(LocationTypeResource.class);

    private static final String ENTITY_NAME = "locationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationTypeRepository locationTypeRepository;

    public LocationTypeResource(LocationTypeRepository locationTypeRepository) {
        this.locationTypeRepository = locationTypeRepository;
    }

    /**
     * {@code POST  /location-types} : Create a new locationType.
     *
     * @param locationType the locationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationType, or with status {@code 400 (Bad Request)} if the locationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-types")
    public ResponseEntity<LocationType> createLocationType(@Valid @RequestBody LocationType locationType) throws URISyntaxException {
        log.debug("REST request to save LocationType : {}", locationType);
        if (locationType.getId() != null) {
            throw new BadRequestAlertException("A new locationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationType result = locationTypeRepository.save(locationType);
        return ResponseEntity.created(new URI("/api/location-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-types} : Updates an existing locationType.
     *
     * @param locationType the locationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationType,
     * or with status {@code 400 (Bad Request)} if the locationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-types")
    public ResponseEntity<LocationType> updateLocationType(@Valid @RequestBody LocationType locationType) throws URISyntaxException {
        log.debug("REST request to update LocationType : {}", locationType);
        if (locationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocationType result = locationTypeRepository.save(locationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /location-types} : get all the locationTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationTypes in body.
     */
    @GetMapping("/location-types")
    public List<LocationType> getAllLocationTypes() {
        log.debug("REST request to get all LocationTypes");
        return locationTypeRepository.findAll();
    }

    /**
     * {@code GET  /location-types/:id} : get the "id" locationType.
     *
     * @param id the id of the locationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-types/{id}")
    public ResponseEntity<LocationType> getLocationType(@PathVariable Long id) {
        log.debug("REST request to get LocationType : {}", id);
        Optional<LocationType> locationType = locationTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locationType);
    }

    /**
     * {@code DELETE  /location-types/:id} : delete the "id" locationType.
     *
     * @param id the id of the locationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-types/{id}")
    public ResponseEntity<Void> deleteLocationType(@PathVariable Long id) {
        log.debug("REST request to delete LocationType : {}", id);
        locationTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
