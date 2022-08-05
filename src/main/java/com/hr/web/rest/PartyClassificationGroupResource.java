package com.hr.web.rest;

import com.hr.domain.PartyClassificationGroup;
import com.hr.repository.PartyClassificationGroupRepository;
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
 * REST controller for managing {@link com.hr.domain.PartyClassificationGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyClassificationGroupResource {

    private final Logger log = LoggerFactory.getLogger(PartyClassificationGroupResource.class);

    private static final String ENTITY_NAME = "partyClassificationGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyClassificationGroupRepository partyClassificationGroupRepository;

    public PartyClassificationGroupResource(PartyClassificationGroupRepository partyClassificationGroupRepository) {
        this.partyClassificationGroupRepository = partyClassificationGroupRepository;
    }

    /**
     * {@code POST  /party-classification-groups} : Create a new partyClassificationGroup.
     *
     * @param partyClassificationGroup the partyClassificationGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyClassificationGroup, or with status {@code 400 (Bad Request)} if the partyClassificationGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-classification-groups")
    public ResponseEntity<PartyClassificationGroup> createPartyClassificationGroup(@Valid @RequestBody PartyClassificationGroup partyClassificationGroup) throws URISyntaxException {
        log.debug("REST request to save PartyClassificationGroup : {}", partyClassificationGroup);
        if (partyClassificationGroup.getId() != null) {
            throw new BadRequestAlertException("A new partyClassificationGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyClassificationGroup result = partyClassificationGroupRepository.save(partyClassificationGroup);
        return ResponseEntity.created(new URI("/api/party-classification-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-classification-groups} : Updates an existing partyClassificationGroup.
     *
     * @param partyClassificationGroup the partyClassificationGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyClassificationGroup,
     * or with status {@code 400 (Bad Request)} if the partyClassificationGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyClassificationGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-classification-groups")
    public ResponseEntity<PartyClassificationGroup> updatePartyClassificationGroup(@Valid @RequestBody PartyClassificationGroup partyClassificationGroup) throws URISyntaxException {
        log.debug("REST request to update PartyClassificationGroup : {}", partyClassificationGroup);
        if (partyClassificationGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyClassificationGroup result = partyClassificationGroupRepository.save(partyClassificationGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyClassificationGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-classification-groups} : get all the partyClassificationGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyClassificationGroups in body.
     */
    @GetMapping("/party-classification-groups")
    public List<PartyClassificationGroup> getAllPartyClassificationGroups() {
        log.debug("REST request to get all PartyClassificationGroups");
        return partyClassificationGroupRepository.findAll();
    }

    /**
     * {@code GET  /party-classification-groups/:id} : get the "id" partyClassificationGroup.
     *
     * @param id the id of the partyClassificationGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyClassificationGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-classification-groups/{id}")
    public ResponseEntity<PartyClassificationGroup> getPartyClassificationGroup(@PathVariable Long id) {
        log.debug("REST request to get PartyClassificationGroup : {}", id);
        Optional<PartyClassificationGroup> partyClassificationGroup = partyClassificationGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyClassificationGroup);
    }

    /**
     * {@code DELETE  /party-classification-groups/:id} : delete the "id" partyClassificationGroup.
     *
     * @param id the id of the partyClassificationGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-classification-groups/{id}")
    public ResponseEntity<Void> deletePartyClassificationGroup(@PathVariable Long id) {
        log.debug("REST request to delete PartyClassificationGroup : {}", id);
        partyClassificationGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
