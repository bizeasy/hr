package com.hr.web.rest;

import com.hr.domain.InventoryTransfer;
import com.hr.service.InventoryTransferService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.InventoryTransferCriteria;
import com.hr.service.InventoryTransferQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hr.domain.InventoryTransfer}.
 */
@RestController
@RequestMapping("/api")
public class InventoryTransferResource {

    private final Logger log = LoggerFactory.getLogger(InventoryTransferResource.class);

    private static final String ENTITY_NAME = "inventoryTransfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryTransferService inventoryTransferService;

    private final InventoryTransferQueryService inventoryTransferQueryService;

    public InventoryTransferResource(InventoryTransferService inventoryTransferService, InventoryTransferQueryService inventoryTransferQueryService) {
        this.inventoryTransferService = inventoryTransferService;
        this.inventoryTransferQueryService = inventoryTransferQueryService;
    }

    /**
     * {@code POST  /inventory-transfers} : Create a new inventoryTransfer.
     *
     * @param inventoryTransfer the inventoryTransfer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryTransfer, or with status {@code 400 (Bad Request)} if the inventoryTransfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-transfers")
    public ResponseEntity<InventoryTransfer> createInventoryTransfer(@Valid @RequestBody InventoryTransfer inventoryTransfer) throws URISyntaxException {
        log.debug("REST request to save InventoryTransfer : {}", inventoryTransfer);
        if (inventoryTransfer.getId() != null) {
            throw new BadRequestAlertException("A new inventoryTransfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryTransfer result = inventoryTransferService.save(inventoryTransfer);
        return ResponseEntity.created(new URI("/api/inventory-transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-transfers} : Updates an existing inventoryTransfer.
     *
     * @param inventoryTransfer the inventoryTransfer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryTransfer,
     * or with status {@code 400 (Bad Request)} if the inventoryTransfer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryTransfer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-transfers")
    public ResponseEntity<InventoryTransfer> updateInventoryTransfer(@Valid @RequestBody InventoryTransfer inventoryTransfer) throws URISyntaxException {
        log.debug("REST request to update InventoryTransfer : {}", inventoryTransfer);
        if (inventoryTransfer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryTransfer result = inventoryTransferService.save(inventoryTransfer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryTransfer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-transfers} : get all the inventoryTransfers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryTransfers in body.
     */
    @GetMapping("/inventory-transfers")
    public ResponseEntity<List<InventoryTransfer>> getAllInventoryTransfers(InventoryTransferCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InventoryTransfers by criteria: {}", criteria);
        Page<InventoryTransfer> page = inventoryTransferQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-transfers/count} : count all the inventoryTransfers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inventory-transfers/count")
    public ResponseEntity<Long> countInventoryTransfers(InventoryTransferCriteria criteria) {
        log.debug("REST request to count InventoryTransfers by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryTransferQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventory-transfers/:id} : get the "id" inventoryTransfer.
     *
     * @param id the id of the inventoryTransfer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryTransfer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-transfers/{id}")
    public ResponseEntity<InventoryTransfer> getInventoryTransfer(@PathVariable Long id) {
        log.debug("REST request to get InventoryTransfer : {}", id);
        Optional<InventoryTransfer> inventoryTransfer = inventoryTransferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryTransfer);
    }

    /**
     * {@code DELETE  /inventory-transfers/:id} : delete the "id" inventoryTransfer.
     *
     * @param id the id of the inventoryTransfer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-transfers/{id}")
    public ResponseEntity<Void> deleteInventoryTransfer(@PathVariable Long id) {
        log.debug("REST request to delete InventoryTransfer : {}", id);
        inventoryTransferService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
