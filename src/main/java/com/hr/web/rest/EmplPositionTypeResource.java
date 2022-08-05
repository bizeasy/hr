package com.hr.web.rest;

import com.hr.domain.EmplPositionType;
import com.hr.repository.EmplPositionTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplPositionType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplPositionTypeResource {

    private final Logger log = LoggerFactory.getLogger(EmplPositionTypeResource.class);

    private static final String ENTITY_NAME = "emplPositionType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplPositionTypeRepository emplPositionTypeRepository;

    public EmplPositionTypeResource(EmplPositionTypeRepository emplPositionTypeRepository) {
        this.emplPositionTypeRepository = emplPositionTypeRepository;
    }

    /**
     * {@code POST  /empl-position-types} : Create a new emplPositionType.
     *
     * @param emplPositionType the emplPositionType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplPositionType, or with status {@code 400 (Bad Request)} if the emplPositionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-position-types")
    public ResponseEntity<EmplPositionType> createEmplPositionType(@Valid @RequestBody EmplPositionType emplPositionType) throws URISyntaxException {
        log.debug("REST request to save EmplPositionType : {}", emplPositionType);
        if (emplPositionType.getId() != null) {
            throw new BadRequestAlertException("A new emplPositionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplPositionType result = emplPositionTypeRepository.save(emplPositionType);
        return ResponseEntity.created(new URI("/api/empl-position-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-position-types} : Updates an existing emplPositionType.
     *
     * @param emplPositionType the emplPositionType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplPositionType,
     * or with status {@code 400 (Bad Request)} if the emplPositionType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplPositionType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-position-types")
    public ResponseEntity<EmplPositionType> updateEmplPositionType(@Valid @RequestBody EmplPositionType emplPositionType) throws URISyntaxException {
        log.debug("REST request to update EmplPositionType : {}", emplPositionType);
        if (emplPositionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplPositionType result = emplPositionTypeRepository.save(emplPositionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplPositionType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-position-types} : get all the emplPositionTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplPositionTypes in body.
     */
    @GetMapping("/empl-position-types")
    public List<EmplPositionType> getAllEmplPositionTypes() {
        log.debug("REST request to get all EmplPositionTypes");
        return emplPositionTypeRepository.findAll();
    }

    /**
     * {@code GET  /empl-position-types/:id} : get the "id" emplPositionType.
     *
     * @param id the id of the emplPositionType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplPositionType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-position-types/{id}")
    public ResponseEntity<EmplPositionType> getEmplPositionType(@PathVariable Long id) {
        log.debug("REST request to get EmplPositionType : {}", id);
        Optional<EmplPositionType> emplPositionType = emplPositionTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplPositionType);
    }

    /**
     * {@code DELETE  /empl-position-types/:id} : delete the "id" emplPositionType.
     *
     * @param id the id of the emplPositionType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-position-types/{id}")
    public ResponseEntity<Void> deleteEmplPositionType(@PathVariable Long id) {
        log.debug("REST request to delete EmplPositionType : {}", id);
        emplPositionTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
