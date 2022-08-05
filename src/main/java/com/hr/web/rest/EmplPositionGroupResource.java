package com.hr.web.rest;

import com.hr.domain.EmplPositionGroup;
import com.hr.repository.EmplPositionGroupRepository;
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
 * REST controller for managing {@link com.hr.domain.EmplPositionGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmplPositionGroupResource {

    private final Logger log = LoggerFactory.getLogger(EmplPositionGroupResource.class);

    private static final String ENTITY_NAME = "emplPositionGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmplPositionGroupRepository emplPositionGroupRepository;

    public EmplPositionGroupResource(EmplPositionGroupRepository emplPositionGroupRepository) {
        this.emplPositionGroupRepository = emplPositionGroupRepository;
    }

    /**
     * {@code POST  /empl-position-groups} : Create a new emplPositionGroup.
     *
     * @param emplPositionGroup the emplPositionGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emplPositionGroup, or with status {@code 400 (Bad Request)} if the emplPositionGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empl-position-groups")
    public ResponseEntity<EmplPositionGroup> createEmplPositionGroup(@Valid @RequestBody EmplPositionGroup emplPositionGroup) throws URISyntaxException {
        log.debug("REST request to save EmplPositionGroup : {}", emplPositionGroup);
        if (emplPositionGroup.getId() != null) {
            throw new BadRequestAlertException("A new emplPositionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmplPositionGroup result = emplPositionGroupRepository.save(emplPositionGroup);
        return ResponseEntity.created(new URI("/api/empl-position-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empl-position-groups} : Updates an existing emplPositionGroup.
     *
     * @param emplPositionGroup the emplPositionGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emplPositionGroup,
     * or with status {@code 400 (Bad Request)} if the emplPositionGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emplPositionGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empl-position-groups")
    public ResponseEntity<EmplPositionGroup> updateEmplPositionGroup(@Valid @RequestBody EmplPositionGroup emplPositionGroup) throws URISyntaxException {
        log.debug("REST request to update EmplPositionGroup : {}", emplPositionGroup);
        if (emplPositionGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmplPositionGroup result = emplPositionGroupRepository.save(emplPositionGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emplPositionGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empl-position-groups} : get all the emplPositionGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplPositionGroups in body.
     */
    @GetMapping("/empl-position-groups")
    public List<EmplPositionGroup> getAllEmplPositionGroups() {
        log.debug("REST request to get all EmplPositionGroups");
        return emplPositionGroupRepository.findAll();
    }

    /**
     * {@code GET  /empl-position-groups/:id} : get the "id" emplPositionGroup.
     *
     * @param id the id of the emplPositionGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emplPositionGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empl-position-groups/{id}")
    public ResponseEntity<EmplPositionGroup> getEmplPositionGroup(@PathVariable Long id) {
        log.debug("REST request to get EmplPositionGroup : {}", id);
        Optional<EmplPositionGroup> emplPositionGroup = emplPositionGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emplPositionGroup);
    }

    /**
     * {@code DELETE  /empl-position-groups/:id} : delete the "id" emplPositionGroup.
     *
     * @param id the id of the emplPositionGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empl-position-groups/{id}")
    public ResponseEntity<Void> deleteEmplPositionGroup(@PathVariable Long id) {
        log.debug("REST request to delete EmplPositionGroup : {}", id);
        emplPositionGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
