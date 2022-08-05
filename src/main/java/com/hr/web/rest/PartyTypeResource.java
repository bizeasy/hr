package com.hr.web.rest;

import com.hr.domain.PartyType;
import com.hr.repository.PartyTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.PartyType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyTypeResource {

    private final Logger log = LoggerFactory.getLogger(PartyTypeResource.class);

    private static final String ENTITY_NAME = "partyType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyTypeRepository partyTypeRepository;

    public PartyTypeResource(PartyTypeRepository partyTypeRepository) {
        this.partyTypeRepository = partyTypeRepository;
    }

    /**
     * {@code POST  /party-types} : Create a new partyType.
     *
     * @param partyType the partyType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyType, or with status {@code 400 (Bad Request)} if the partyType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-types")
    public ResponseEntity<PartyType> createPartyType(@Valid @RequestBody PartyType partyType) throws URISyntaxException {
        log.debug("REST request to save PartyType : {}", partyType);
        if (partyType.getId() != null) {
            throw new BadRequestAlertException("A new partyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyType result = partyTypeRepository.save(partyType);
        return ResponseEntity.created(new URI("/api/party-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-types} : Updates an existing partyType.
     *
     * @param partyType the partyType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyType,
     * or with status {@code 400 (Bad Request)} if the partyType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-types")
    public ResponseEntity<PartyType> updatePartyType(@Valid @RequestBody PartyType partyType) throws URISyntaxException {
        log.debug("REST request to update PartyType : {}", partyType);
        if (partyType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyType result = partyTypeRepository.save(partyType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-types} : get all the partyTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyTypes in body.
     */
    @GetMapping("/party-types")
    public List<PartyType> getAllPartyTypes() {
        log.debug("REST request to get all PartyTypes");
        return partyTypeRepository.findAll();
    }

    /**
     * {@code GET  /party-types/:id} : get the "id" partyType.
     *
     * @param id the id of the partyType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-types/{id}")
    public ResponseEntity<PartyType> getPartyType(@PathVariable Long id) {
        log.debug("REST request to get PartyType : {}", id);
        Optional<PartyType> partyType = partyTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyType);
    }

    /**
     * {@code DELETE  /party-types/:id} : delete the "id" partyType.
     *
     * @param id the id of the partyType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-types/{id}")
    public ResponseEntity<Void> deletePartyType(@PathVariable Long id) {
        log.debug("REST request to delete PartyType : {}", id);
        partyTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
