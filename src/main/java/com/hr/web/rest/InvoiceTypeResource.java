package com.hr.web.rest;

import com.hr.domain.InvoiceType;
import com.hr.repository.InvoiceTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.InvoiceType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InvoiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceTypeResource.class);

    private static final String ENTITY_NAME = "invoiceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceTypeRepository invoiceTypeRepository;

    public InvoiceTypeResource(InvoiceTypeRepository invoiceTypeRepository) {
        this.invoiceTypeRepository = invoiceTypeRepository;
    }

    /**
     * {@code POST  /invoice-types} : Create a new invoiceType.
     *
     * @param invoiceType the invoiceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceType, or with status {@code 400 (Bad Request)} if the invoiceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-types")
    public ResponseEntity<InvoiceType> createInvoiceType(@Valid @RequestBody InvoiceType invoiceType) throws URISyntaxException {
        log.debug("REST request to save InvoiceType : {}", invoiceType);
        if (invoiceType.getId() != null) {
            throw new BadRequestAlertException("A new invoiceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceType result = invoiceTypeRepository.save(invoiceType);
        return ResponseEntity.created(new URI("/api/invoice-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-types} : Updates an existing invoiceType.
     *
     * @param invoiceType the invoiceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceType,
     * or with status {@code 400 (Bad Request)} if the invoiceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-types")
    public ResponseEntity<InvoiceType> updateInvoiceType(@Valid @RequestBody InvoiceType invoiceType) throws URISyntaxException {
        log.debug("REST request to update InvoiceType : {}", invoiceType);
        if (invoiceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceType result = invoiceTypeRepository.save(invoiceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoice-types} : get all the invoiceTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceTypes in body.
     */
    @GetMapping("/invoice-types")
    public List<InvoiceType> getAllInvoiceTypes() {
        log.debug("REST request to get all InvoiceTypes");
        return invoiceTypeRepository.findAll();
    }

    /**
     * {@code GET  /invoice-types/:id} : get the "id" invoiceType.
     *
     * @param id the id of the invoiceType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-types/{id}")
    public ResponseEntity<InvoiceType> getInvoiceType(@PathVariable Long id) {
        log.debug("REST request to get InvoiceType : {}", id);
        Optional<InvoiceType> invoiceType = invoiceTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(invoiceType);
    }

    /**
     * {@code DELETE  /invoice-types/:id} : delete the "id" invoiceType.
     *
     * @param id the id of the invoiceType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-types/{id}")
    public ResponseEntity<Void> deleteInvoiceType(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceType : {}", id);
        invoiceTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
