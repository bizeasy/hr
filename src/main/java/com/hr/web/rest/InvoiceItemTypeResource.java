package com.hr.web.rest;

import com.hr.domain.InvoiceItemType;
import com.hr.repository.InvoiceItemTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.InvoiceItemType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InvoiceItemTypeResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemTypeResource.class);

    private static final String ENTITY_NAME = "invoiceItemType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceItemTypeRepository invoiceItemTypeRepository;

    public InvoiceItemTypeResource(InvoiceItemTypeRepository invoiceItemTypeRepository) {
        this.invoiceItemTypeRepository = invoiceItemTypeRepository;
    }

    /**
     * {@code POST  /invoice-item-types} : Create a new invoiceItemType.
     *
     * @param invoiceItemType the invoiceItemType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceItemType, or with status {@code 400 (Bad Request)} if the invoiceItemType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-item-types")
    public ResponseEntity<InvoiceItemType> createInvoiceItemType(@Valid @RequestBody InvoiceItemType invoiceItemType) throws URISyntaxException {
        log.debug("REST request to save InvoiceItemType : {}", invoiceItemType);
        if (invoiceItemType.getId() != null) {
            throw new BadRequestAlertException("A new invoiceItemType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceItemType result = invoiceItemTypeRepository.save(invoiceItemType);
        return ResponseEntity.created(new URI("/api/invoice-item-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-item-types} : Updates an existing invoiceItemType.
     *
     * @param invoiceItemType the invoiceItemType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceItemType,
     * or with status {@code 400 (Bad Request)} if the invoiceItemType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceItemType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-item-types")
    public ResponseEntity<InvoiceItemType> updateInvoiceItemType(@Valid @RequestBody InvoiceItemType invoiceItemType) throws URISyntaxException {
        log.debug("REST request to update InvoiceItemType : {}", invoiceItemType);
        if (invoiceItemType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceItemType result = invoiceItemTypeRepository.save(invoiceItemType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceItemType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoice-item-types} : get all the invoiceItemTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceItemTypes in body.
     */
    @GetMapping("/invoice-item-types")
    public List<InvoiceItemType> getAllInvoiceItemTypes() {
        log.debug("REST request to get all InvoiceItemTypes");
        return invoiceItemTypeRepository.findAll();
    }

    /**
     * {@code GET  /invoice-item-types/:id} : get the "id" invoiceItemType.
     *
     * @param id the id of the invoiceItemType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceItemType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-item-types/{id}")
    public ResponseEntity<InvoiceItemType> getInvoiceItemType(@PathVariable Long id) {
        log.debug("REST request to get InvoiceItemType : {}", id);
        Optional<InvoiceItemType> invoiceItemType = invoiceItemTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(invoiceItemType);
    }

    /**
     * {@code DELETE  /invoice-item-types/:id} : delete the "id" invoiceItemType.
     *
     * @param id the id of the invoiceItemType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-item-types/{id}")
    public ResponseEntity<Void> deleteInvoiceItemType(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceItemType : {}", id);
        invoiceItemTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
