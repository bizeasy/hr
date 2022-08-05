package com.hr.web.rest;

import com.hr.domain.Term;
import com.hr.repository.TermRepository;
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
 * REST controller for managing {@link com.hr.domain.Term}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TermResource {

    private final Logger log = LoggerFactory.getLogger(TermResource.class);

    private static final String ENTITY_NAME = "term";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermRepository termRepository;

    public TermResource(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    /**
     * {@code POST  /terms} : Create a new term.
     *
     * @param term the term to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new term, or with status {@code 400 (Bad Request)} if the term has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terms")
    public ResponseEntity<Term> createTerm(@Valid @RequestBody Term term) throws URISyntaxException {
        log.debug("REST request to save Term : {}", term);
        if (term.getId() != null) {
            throw new BadRequestAlertException("A new term cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Term result = termRepository.save(term);
        return ResponseEntity.created(new URI("/api/terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terms} : Updates an existing term.
     *
     * @param term the term to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated term,
     * or with status {@code 400 (Bad Request)} if the term is not valid,
     * or with status {@code 500 (Internal Server Error)} if the term couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terms")
    public ResponseEntity<Term> updateTerm(@Valid @RequestBody Term term) throws URISyntaxException {
        log.debug("REST request to update Term : {}", term);
        if (term.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Term result = termRepository.save(term);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, term.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /terms} : get all the terms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terms in body.
     */
    @GetMapping("/terms")
    public List<Term> getAllTerms() {
        log.debug("REST request to get all Terms");
        return termRepository.findAll();
    }

    /**
     * {@code GET  /terms/:id} : get the "id" term.
     *
     * @param id the id of the term to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the term, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terms/{id}")
    public ResponseEntity<Term> getTerm(@PathVariable Long id) {
        log.debug("REST request to get Term : {}", id);
        Optional<Term> term = termRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(term);
    }

    /**
     * {@code DELETE  /terms/:id} : delete the "id" term.
     *
     * @param id the id of the term to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terms/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Long id) {
        log.debug("REST request to delete Term : {}", id);
        termRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
