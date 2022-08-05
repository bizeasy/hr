package com.hr.web.rest;

import com.hr.domain.InventoryItemDelegate;
import com.hr.service.InventoryItemDelegateService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.InventoryItemDelegateCriteria;
import com.hr.service.InventoryItemDelegateQueryService;

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
 * REST controller for managing {@link com.hr.domain.InventoryItemDelegate}.
 */
@RestController
@RequestMapping("/api")
public class InventoryItemDelegateResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemDelegateResource.class);

    private static final String ENTITY_NAME = "inventoryItemDelegate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryItemDelegateService inventoryItemDelegateService;

    private final InventoryItemDelegateQueryService inventoryItemDelegateQueryService;

    public InventoryItemDelegateResource(InventoryItemDelegateService inventoryItemDelegateService, InventoryItemDelegateQueryService inventoryItemDelegateQueryService) {
        this.inventoryItemDelegateService = inventoryItemDelegateService;
        this.inventoryItemDelegateQueryService = inventoryItemDelegateQueryService;
    }

    /**
     * {@code POST  /inventory-item-delegates} : Create a new inventoryItemDelegate.
     *
     * @param inventoryItemDelegate the inventoryItemDelegate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryItemDelegate, or with status {@code 400 (Bad Request)} if the inventoryItemDelegate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-item-delegates")
    public ResponseEntity<InventoryItemDelegate> createInventoryItemDelegate(@Valid @RequestBody InventoryItemDelegate inventoryItemDelegate) throws URISyntaxException {
        log.debug("REST request to save InventoryItemDelegate : {}", inventoryItemDelegate);
        if (inventoryItemDelegate.getId() != null) {
            throw new BadRequestAlertException("A new inventoryItemDelegate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryItemDelegate result = inventoryItemDelegateService.save(inventoryItemDelegate);
        return ResponseEntity.created(new URI("/api/inventory-item-delegates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-item-delegates} : Updates an existing inventoryItemDelegate.
     *
     * @param inventoryItemDelegate the inventoryItemDelegate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryItemDelegate,
     * or with status {@code 400 (Bad Request)} if the inventoryItemDelegate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryItemDelegate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-item-delegates")
    public ResponseEntity<InventoryItemDelegate> updateInventoryItemDelegate(@Valid @RequestBody InventoryItemDelegate inventoryItemDelegate) throws URISyntaxException {
        log.debug("REST request to update InventoryItemDelegate : {}", inventoryItemDelegate);
        if (inventoryItemDelegate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryItemDelegate result = inventoryItemDelegateService.save(inventoryItemDelegate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryItemDelegate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-item-delegates} : get all the inventoryItemDelegates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryItemDelegates in body.
     */
    @GetMapping("/inventory-item-delegates")
    public ResponseEntity<List<InventoryItemDelegate>> getAllInventoryItemDelegates(InventoryItemDelegateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InventoryItemDelegates by criteria: {}", criteria);
        Page<InventoryItemDelegate> page = inventoryItemDelegateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-item-delegates/count} : count all the inventoryItemDelegates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inventory-item-delegates/count")
    public ResponseEntity<Long> countInventoryItemDelegates(InventoryItemDelegateCriteria criteria) {
        log.debug("REST request to count InventoryItemDelegates by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryItemDelegateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventory-item-delegates/:id} : get the "id" inventoryItemDelegate.
     *
     * @param id the id of the inventoryItemDelegate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryItemDelegate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-item-delegates/{id}")
    public ResponseEntity<InventoryItemDelegate> getInventoryItemDelegate(@PathVariable Long id) {
        log.debug("REST request to get InventoryItemDelegate : {}", id);
        Optional<InventoryItemDelegate> inventoryItemDelegate = inventoryItemDelegateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryItemDelegate);
    }

    /**
     * {@code DELETE  /inventory-item-delegates/:id} : delete the "id" inventoryItemDelegate.
     *
     * @param id the id of the inventoryItemDelegate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-item-delegates/{id}")
    public ResponseEntity<Void> deleteInventoryItemDelegate(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItemDelegate : {}", id);
        inventoryItemDelegateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
