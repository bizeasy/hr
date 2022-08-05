package com.hr.service;

import com.hr.domain.InventoryItem;
import com.hr.repository.InventoryItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InventoryItem}.
 */
@Service
@Transactional
public class InventoryItemService {

    private final Logger log = LoggerFactory.getLogger(InventoryItemService.class);

    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemService(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    /**
     * Save a inventoryItem.
     *
     * @param inventoryItem the entity to save.
     * @return the persisted entity.
     */
    public InventoryItem save(InventoryItem inventoryItem) {
        log.debug("Request to save InventoryItem : {}", inventoryItem);
        return inventoryItemRepository.save(inventoryItem);
    }

    /**
     * Get all the inventoryItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryItem> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryItems");
        return inventoryItemRepository.findAll(pageable);
    }


    /**
     * Get one inventoryItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoryItem> findOne(Long id) {
        log.debug("Request to get InventoryItem : {}", id);
        return inventoryItemRepository.findById(id);
    }

    /**
     * Delete the inventoryItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryItem : {}", id);
        inventoryItemRepository.deleteById(id);
    }
}
