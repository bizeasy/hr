package com.hr.web.rest;

import com.hr.domain.DeductionType;
import com.hr.repository.DeductionTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.DeductionType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeductionTypeResource {

    private final Logger log = LoggerFactory.getLogger(DeductionTypeResource.class);

    private static final String ENTITY_NAME = "deductionType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeductionTypeRepository deductionTypeRepository;

    public DeductionTypeResource(DeductionTypeRepository deductionTypeRepository) {
        this.deductionTypeRepository = deductionTypeRepository;
    }

    /**
     * {@code POST  /deduction-types} : Create a new deductionType.
     *
     * @param deductionType the deductionType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deductionType, or with status {@code 400 (Bad Request)} if the deductionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deduction-types")
    public ResponseEntity<DeductionType> createDeductionType(@Valid @RequestBody DeductionType deductionType) throws URISyntaxException {
        log.debug("REST request to save DeductionType : {}", deductionType);
        if (deductionType.getId() != null) {
            throw new BadRequestAlertException("A new deductionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeductionType result = deductionTypeRepository.save(deductionType);
        return ResponseEntity.created(new URI("/api/deduction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deduction-types} : Updates an existing deductionType.
     *
     * @param deductionType the deductionType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deductionType,
     * or with status {@code 400 (Bad Request)} if the deductionType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deductionType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deduction-types")
    public ResponseEntity<DeductionType> updateDeductionType(@Valid @RequestBody DeductionType deductionType) throws URISyntaxException {
        log.debug("REST request to update DeductionType : {}", deductionType);
        if (deductionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeductionType result = deductionTypeRepository.save(deductionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deductionType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deduction-types} : get all the deductionTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deductionTypes in body.
     */
    @GetMapping("/deduction-types")
    public List<DeductionType> getAllDeductionTypes() {
        log.debug("REST request to get all DeductionTypes");
        return deductionTypeRepository.findAll();
    }

    /**
     * {@code GET  /deduction-types/:id} : get the "id" deductionType.
     *
     * @param id the id of the deductionType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deductionType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deduction-types/{id}")
    public ResponseEntity<DeductionType> getDeductionType(@PathVariable Long id) {
        log.debug("REST request to get DeductionType : {}", id);
        Optional<DeductionType> deductionType = deductionTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deductionType);
    }

    /**
     * {@code DELETE  /deduction-types/:id} : delete the "id" deductionType.
     *
     * @param id the id of the deductionType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deduction-types/{id}")
    public ResponseEntity<Void> deleteDeductionType(@PathVariable Long id) {
        log.debug("REST request to delete DeductionType : {}", id);
        deductionTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
