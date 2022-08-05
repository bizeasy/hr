package com.hr.web.rest;

import com.hr.domain.InventoryItemVariance;
import com.hr.service.InventoryItemVarianceService;
import com.hr.web.rest.errors.BadRequestAlertException;
import com.hr.service.dto.InventoryItemVarianceCriteria;
import com.hr.service.InventoryItemVarianceQueryService;

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
 * REST controller for managing {@link com.hr.domain.InventoryItemVariance}.
 */
@RestController
@RequestMapping("/api")
public class InventoryItemVarianceResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemVarianceResource.class);

    private static final String ENTITY_NAME = "inventoryItemVariance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryItemVarianceService inventoryItemVarianceService;

    private final InventoryItemVarianceQueryService inventoryItemVarianceQueryService;

    public InventoryItemVarianceResource(InventoryItemVarianceService inventoryItemVarianceService, InventoryItemVarianceQueryService inventoryItemVarianceQueryService) {
        this.inventoryItemVarianceService = inventoryItemVarianceService;
        this.inventoryItemVarianceQueryService = inventoryItemVarianceQueryService;
    }

    /**
     * {@code POST  /inventory-item-variances} : Create a new inventoryItemVariance.
     *
     * @param inventoryItemVariance the inventoryItemVariance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryItemVariance, or with status {@code 400 (Bad Request)} if the inventoryItemVariance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-item-variances")
    public ResponseEntity<InventoryItemVariance> createInventoryItemVariance(@Valid @RequestBody InventoryItemVariance inventoryItemVariance) throws URISyntaxException {
        log.debug("REST request to save InventoryItemVariance : {}", inventoryItemVariance);
        if (inventoryItemVariance.getId() != null) {
            throw new BadRequestAlertException("A new inventoryItemVariance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryItemVariance result = inventoryItemVarianceService.save(inventoryItemVariance);
        return ResponseEntity.created(new URI("/api/inventory-item-variances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-item-variances} : Updates an existing inventoryItemVariance.
     *
     * @param inventoryItemVariance the inventoryItemVariance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryItemVariance,
     * or with status {@code 400 (Bad Request)} if the inventoryItemVariance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryItemVariance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-item-variances")
    public ResponseEntity<InventoryItemVariance> updateInventoryItemVariance(@Valid @RequestBody InventoryItemVariance inventoryItemVariance) throws URISyntaxException {
        log.debug("REST request to update InventoryItemVariance : {}", inventoryItemVariance);
        if (inventoryItemVariance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryItemVariance result = inventoryItemVarianceService.save(inventoryItemVariance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryItemVariance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-item-variances} : get all the inventoryItemVariances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryItemVariances in body.
     */
    @GetMapping("/inventory-item-variances")
    public ResponseEntity<List<InventoryItemVariance>> getAllInventoryItemVariances(InventoryItemVarianceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InventoryItemVariances by criteria: {}", criteria);
        Page<InventoryItemVariance> page = inventoryItemVarianceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-item-variances/count} : count all the inventoryItemVariances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inventory-item-variances/count")
    public ResponseEntity<Long> countInventoryItemVariances(InventoryItemVarianceCriteria criteria) {
        log.debug("REST request to count InventoryItemVariances by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryItemVarianceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventory-item-variances/:id} : get the "id" inventoryItemVariance.
     *
     * @param id the id of the inventoryItemVariance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryItemVariance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-item-variances/{id}")
    public ResponseEntity<InventoryItemVariance> getInventoryItemVariance(@PathVariable Long id) {
        log.debug("REST request to get InventoryItemVariance : {}", id);
        Optional<InventoryItemVariance> inventoryItemVariance = inventoryItemVarianceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryItemVariance);
    }

    /**
     * {@code DELETE  /inventory-item-variances/:id} : delete the "id" inventoryItemVariance.
     *
     * @param id the id of the inventoryItemVariance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-item-variances/{id}")
    public ResponseEntity<Void> deleteInventoryItemVariance(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItemVariance : {}", id);
        inventoryItemVarianceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
