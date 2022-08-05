package com.hr.web.rest;

import com.hr.domain.ReasonType;
import com.hr.repository.ReasonTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.ReasonType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReasonTypeResource {

    private final Logger log = LoggerFactory.getLogger(ReasonTypeResource.class);

    private static final String ENTITY_NAME = "reasonType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReasonTypeRepository reasonTypeRepository;

    public ReasonTypeResource(ReasonTypeRepository reasonTypeRepository) {
        this.reasonTypeRepository = reasonTypeRepository;
    }

    /**
     * {@code POST  /reason-types} : Create a new reasonType.
     *
     * @param reasonType the reasonType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reasonType, or with status {@code 400 (Bad Request)} if the reasonType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reason-types")
    public ResponseEntity<ReasonType> createReasonType(@Valid @RequestBody ReasonType reasonType) throws URISyntaxException {
        log.debug("REST request to save ReasonType : {}", reasonType);
        if (reasonType.getId() != null) {
            throw new BadRequestAlertException("A new reasonType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReasonType result = reasonTypeRepository.save(reasonType);
        return ResponseEntity.created(new URI("/api/reason-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reason-types} : Updates an existing reasonType.
     *
     * @param reasonType the reasonType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reasonType,
     * or with status {@code 400 (Bad Request)} if the reasonType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reasonType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reason-types")
    public ResponseEntity<ReasonType> updateReasonType(@Valid @RequestBody ReasonType reasonType) throws URISyntaxException {
        log.debug("REST request to update ReasonType : {}", reasonType);
        if (reasonType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReasonType result = reasonTypeRepository.save(reasonType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reasonType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reason-types} : get all the reasonTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reasonTypes in body.
     */
    @GetMapping("/reason-types")
    public List<ReasonType> getAllReasonTypes() {
        log.debug("REST request to get all ReasonTypes");
        return reasonTypeRepository.findAll();
    }

    /**
     * {@code GET  /reason-types/:id} : get the "id" reasonType.
     *
     * @param id the id of the reasonType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reasonType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reason-types/{id}")
    public ResponseEntity<ReasonType> getReasonType(@PathVariable Long id) {
        log.debug("REST request to get ReasonType : {}", id);
        Optional<ReasonType> reasonType = reasonTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reasonType);
    }

    /**
     * {@code DELETE  /reason-types/:id} : delete the "id" reasonType.
     *
     * @param id the id of the reasonType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reason-types/{id}")
    public ResponseEntity<Void> deleteReasonType(@PathVariable Long id) {
        log.debug("REST request to delete ReasonType : {}", id);
        reasonTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
