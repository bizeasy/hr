package com.hr.web.rest;

import com.hr.domain.TermType;
import com.hr.repository.TermTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.TermType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TermTypeResource {

    private final Logger log = LoggerFactory.getLogger(TermTypeResource.class);

    private static final String ENTITY_NAME = "termType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermTypeRepository termTypeRepository;

    public TermTypeResource(TermTypeRepository termTypeRepository) {
        this.termTypeRepository = termTypeRepository;
    }

    /**
     * {@code POST  /term-types} : Create a new termType.
     *
     * @param termType the termType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termType, or with status {@code 400 (Bad Request)} if the termType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/term-types")
    public ResponseEntity<TermType> createTermType(@Valid @RequestBody TermType termType) throws URISyntaxException {
        log.debug("REST request to save TermType : {}", termType);
        if (termType.getId() != null) {
            throw new BadRequestAlertException("A new termType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TermType result = termTypeRepository.save(termType);
        return ResponseEntity.created(new URI("/api/term-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /term-types} : Updates an existing termType.
     *
     * @param termType the termType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termType,
     * or with status {@code 400 (Bad Request)} if the termType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/term-types")
    public ResponseEntity<TermType> updateTermType(@Valid @RequestBody TermType termType) throws URISyntaxException {
        log.debug("REST request to update TermType : {}", termType);
        if (termType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TermType result = termTypeRepository.save(termType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /term-types} : get all the termTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termTypes in body.
     */
    @GetMapping("/term-types")
    public List<TermType> getAllTermTypes() {
        log.debug("REST request to get all TermTypes");
        return termTypeRepository.findAll();
    }

    /**
     * {@code GET  /term-types/:id} : get the "id" termType.
     *
     * @param id the id of the termType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/term-types/{id}")
    public ResponseEntity<TermType> getTermType(@PathVariable Long id) {
        log.debug("REST request to get TermType : {}", id);
        Optional<TermType> termType = termTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(termType);
    }

    /**
     * {@code DELETE  /term-types/:id} : delete the "id" termType.
     *
     * @param id the id of the termType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/term-types/{id}")
    public ResponseEntity<Void> deleteTermType(@PathVariable Long id) {
        log.debug("REST request to delete TermType : {}", id);
        termTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
