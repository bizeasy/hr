package com.hr.web.rest;

import com.hr.domain.InventoryItemType;
import com.hr.repository.InventoryItemTypeRepository;
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
 * REST controller for managing {@link com.hr.domain.InventoryItemType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InventoryItemTypeResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemTypeResource.class);

    private static final String ENTITY_NAME = "inventoryItemType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryItemTypeRepository inventoryItemTypeRepository;

    public InventoryItemTypeResource(InventoryItemTypeRepository inventoryItemTypeRepository) {
        this.inventoryItemTypeRepository = inventoryItemTypeRepository;
    }

    /**
     * {@code POST  /inventory-item-types} : Create a new inventoryItemType.
     *
     * @param inventoryItemType the inventoryItemType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryItemType, or with status {@code 400 (Bad Request)} if the inventoryItemType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-item-types")
    public ResponseEntity<InventoryItemType> createInventoryItemType(@Valid @RequestBody InventoryItemType inventoryItemType) throws URISyntaxException {
        log.debug("REST request to save InventoryItemType : {}", inventoryItemType);
        if (inventoryItemType.getId() != null) {
            throw new BadRequestAlertException("A new inventoryItemType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryItemType result = inventoryItemTypeRepository.save(inventoryItemType);
        return ResponseEntity.created(new URI("/api/inventory-item-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-item-types} : Updates an existing inventoryItemType.
     *
     * @param inventoryItemType the inventoryItemType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryItemType,
     * or with status {@code 400 (Bad Request)} if the inventoryItemType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryItemType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-item-types")
    public ResponseEntity<InventoryItemType> updateInventoryItemType(@Valid @RequestBody InventoryItemType inventoryItemType) throws URISyntaxException {
        log.debug("REST request to update InventoryItemType : {}", inventoryItemType);
        if (inventoryItemType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryItemType result = inventoryItemTypeRepository.save(inventoryItemType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryItemType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-item-types} : get all the inventoryItemTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryItemTypes in body.
     */
    @GetMapping("/inventory-item-types")
    public List<InventoryItemType> getAllInventoryItemTypes() {
        log.debug("REST request to get all InventoryItemTypes");
        return inventoryItemTypeRepository.findAll();
    }

    /**
     * {@code GET  /inventory-item-types/:id} : get the "id" inventoryItemType.
     *
     * @param id the id of the inventoryItemType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryItemType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-item-types/{id}")
    public ResponseEntity<InventoryItemType> getInventoryItemType(@PathVariable Long id) {
        log.debug("REST request to get InventoryItemType : {}", id);
        Optional<InventoryItemType> inventoryItemType = inventoryItemTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inventoryItemType);
    }

    /**
     * {@code DELETE  /inventory-item-types/:id} : delete the "id" inventoryItemType.
     *
     * @param id the id of the inventoryItemType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-item-types/{id}")
    public ResponseEntity<Void> deleteInventoryItemType(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItemType : {}", id);
        inventoryItemTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
