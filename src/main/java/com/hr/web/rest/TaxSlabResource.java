package com.hr.web.rest;

import com.hr.domain.TaxSlab;
import com.hr.repository.TaxSlabRepository;
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
 * REST controller for managing {@link com.hr.domain.TaxSlab}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaxSlabResource {

    private final Logger log = LoggerFactory.getLogger(TaxSlabResource.class);

    private static final String ENTITY_NAME = "taxSlab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxSlabRepository taxSlabRepository;

    public TaxSlabResource(TaxSlabRepository taxSlabRepository) {
        this.taxSlabRepository = taxSlabRepository;
    }

    /**
     * {@code POST  /tax-slabs} : Create a new taxSlab.
     *
     * @param taxSlab the taxSlab to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxSlab, or with status {@code 400 (Bad Request)} if the taxSlab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-slabs")
    public ResponseEntity<TaxSlab> createTaxSlab(@Valid @RequestBody TaxSlab taxSlab) throws URISyntaxException {
        log.debug("REST request to save TaxSlab : {}", taxSlab);
        if (taxSlab.getId() != null) {
            throw new BadRequestAlertException("A new taxSlab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxSlab result = taxSlabRepository.save(taxSlab);
        return ResponseEntity.created(new URI("/api/tax-slabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-slabs} : Updates an existing taxSlab.
     *
     * @param taxSlab the taxSlab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxSlab,
     * or with status {@code 400 (Bad Request)} if the taxSlab is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxSlab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-slabs")
    public ResponseEntity<TaxSlab> updateTaxSlab(@Valid @RequestBody TaxSlab taxSlab) throws URISyntaxException {
        log.debug("REST request to update TaxSlab : {}", taxSlab);
        if (taxSlab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxSlab result = taxSlabRepository.save(taxSlab);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxSlab.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tax-slabs} : get all the taxSlabs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxSlabs in body.
     */
    @GetMapping("/tax-slabs")
    public List<TaxSlab> getAllTaxSlabs() {
        log.debug("REST request to get all TaxSlabs");
        return taxSlabRepository.findAll();
    }

    /**
     * {@code GET  /tax-slabs/:id} : get the "id" taxSlab.
     *
     * @param id the id of the taxSlab to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxSlab, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-slabs/{id}")
    public ResponseEntity<TaxSlab> getTaxSlab(@PathVariable Long id) {
        log.debug("REST request to get TaxSlab : {}", id);
        Optional<TaxSlab> taxSlab = taxSlabRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxSlab);
    }

    /**
     * {@code DELETE  /tax-slabs/:id} : delete the "id" taxSlab.
     *
     * @param id the id of the taxSlab to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-slabs/{id}")
    public ResponseEntity<Void> deleteTaxSlab(@PathVariable Long id) {
        log.debug("REST request to delete TaxSlab : {}", id);
        taxSlabRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
