package com.hr.web.rest;

import com.hr.domain.TaxAuthority;
import com.hr.repository.TaxAuthorityRepository;
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
 * REST controller for managing {@link com.hr.domain.TaxAuthority}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaxAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(TaxAuthorityResource.class);

    private static final String ENTITY_NAME = "taxAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxAuthorityRepository taxAuthorityRepository;

    public TaxAuthorityResource(TaxAuthorityRepository taxAuthorityRepository) {
        this.taxAuthorityRepository = taxAuthorityRepository;
    }

    /**
     * {@code POST  /tax-authorities} : Create a new taxAuthority.
     *
     * @param taxAuthority the taxAuthority to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxAuthority, or with status {@code 400 (Bad Request)} if the taxAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-authorities")
    public ResponseEntity<TaxAuthority> createTaxAuthority(@Valid @RequestBody TaxAuthority taxAuthority) throws URISyntaxException {
        log.debug("REST request to save TaxAuthority : {}", taxAuthority);
        if (taxAuthority.getId() != null) {
            throw new BadRequestAlertException("A new taxAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxAuthority result = taxAuthorityRepository.save(taxAuthority);
        return ResponseEntity.created(new URI("/api/tax-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-authorities} : Updates an existing taxAuthority.
     *
     * @param taxAuthority the taxAuthority to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxAuthority,
     * or with status {@code 400 (Bad Request)} if the taxAuthority is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxAuthority couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-authorities")
    public ResponseEntity<TaxAuthority> updateTaxAuthority(@Valid @RequestBody TaxAuthority taxAuthority) throws URISyntaxException {
        log.debug("REST request to update TaxAuthority : {}", taxAuthority);
        if (taxAuthority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxAuthority result = taxAuthorityRepository.save(taxAuthority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxAuthority.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tax-authorities} : get all the taxAuthorities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxAuthorities in body.
     */
    @GetMapping("/tax-authorities")
    public List<TaxAuthority> getAllTaxAuthorities() {
        log.debug("REST request to get all TaxAuthorities");
        return taxAuthorityRepository.findAll();
    }

    /**
     * {@code GET  /tax-authorities/:id} : get the "id" taxAuthority.
     *
     * @param id the id of the taxAuthority to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxAuthority, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-authorities/{id}")
    public ResponseEntity<TaxAuthority> getTaxAuthority(@PathVariable Long id) {
        log.debug("REST request to get TaxAuthority : {}", id);
        Optional<TaxAuthority> taxAuthority = taxAuthorityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxAuthority);
    }

    /**
     * {@code DELETE  /tax-authorities/:id} : delete the "id" taxAuthority.
     *
     * @param id the id of the taxAuthority to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-authorities/{id}")
    public ResponseEntity<Void> deleteTaxAuthority(@PathVariable Long id) {
        log.debug("REST request to delete TaxAuthority : {}", id);
        taxAuthorityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
