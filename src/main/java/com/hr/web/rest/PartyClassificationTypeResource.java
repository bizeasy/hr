package com.hr.web.rest;

import com.hr.domain.PartyClassificationType;
import com.hr.repository.PartyClassificationTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.PartyClassificationType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyClassificationTypeResource {

    private final Logger log = LoggerFactory.getLogger(PartyClassificationTypeResource.class);

    private static final String ENTITY_NAME = "partyClassificationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyClassificationTypeRepository partyClassificationTypeRepository;

    public PartyClassificationTypeResource(PartyClassificationTypeRepository partyClassificationTypeRepository) {
        this.partyClassificationTypeRepository = partyClassificationTypeRepository;
    }

    /**
     * {@code POST  /party-classification-types} : Create a new partyClassificationType.
     *
     * @param partyClassificationType the partyClassificationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyClassificationType, or with status {@code 400 (Bad Request)} if the partyClassificationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-classification-types")
    public ResponseEntity<PartyClassificationType> createPartyClassificationType(@Valid @RequestBody PartyClassificationType partyClassificationType) throws URISyntaxException {
        log.debug("REST request to save PartyClassificationType : {}", partyClassificationType);
        if (partyClassificationType.getId() != null) {
            throw new BadRequestAlertException("A new partyClassificationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyClassificationType result = partyClassificationTypeRepository.save(partyClassificationType);
        return ResponseEntity.created(new URI("/api/party-classification-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-classification-types} : Updates an existing partyClassificationType.
     *
     * @param partyClassificationType the partyClassificationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyClassificationType,
     * or with status {@code 400 (Bad Request)} if the partyClassificationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyClassificationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-classification-types")
    public ResponseEntity<PartyClassificationType> updatePartyClassificationType(@Valid @RequestBody PartyClassificationType partyClassificationType) throws URISyntaxException {
        log.debug("REST request to update PartyClassificationType : {}", partyClassificationType);
        if (partyClassificationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyClassificationType result = partyClassificationTypeRepository.save(partyClassificationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyClassificationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-classification-types} : get all the partyClassificationTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyClassificationTypes in body.
     */
    @GetMapping("/party-classification-types")
    public List<PartyClassificationType> getAllPartyClassificationTypes() {
        log.debug("REST request to get all PartyClassificationTypes");
        return partyClassificationTypeRepository.findAll();
    }

    /**
     * {@code GET  /party-classification-types/:id} : get the "id" partyClassificationType.
     *
     * @param id the id of the partyClassificationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyClassificationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-classification-types/{id}")
    public ResponseEntity<PartyClassificationType> getPartyClassificationType(@PathVariable Long id) {
        log.debug("REST request to get PartyClassificationType : {}", id);
        Optional<PartyClassificationType> partyClassificationType = partyClassificationTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyClassificationType);
    }

    /**
     * {@code DELETE  /party-classification-types/:id} : delete the "id" partyClassificationType.
     *
     * @param id the id of the partyClassificationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-classification-types/{id}")
    public ResponseEntity<Void> deletePartyClassificationType(@PathVariable Long id) {
        log.debug("REST request to delete PartyClassificationType : {}", id);
        partyClassificationTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
