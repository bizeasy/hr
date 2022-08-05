package com.hr.service;

import com.hr.domain.InventoryItemDelegate;
import com.hr.repository.InventoryItemDelegateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InventoryItemDelegate}.
 */
@Service
@Transactional
public class InventoryItemDelegateService {

    private final Logger log = LoggerFactory.getLogger(InventoryItemDelegateService.class);

    private final InventoryItemDelegateRepository inventoryItemDelegateRepository;

    public InventoryItemDelegateService(InventoryItemDelegateRepository inventoryItemDelegateRepository) {
        this.inventoryItemDelegateRepository = inventoryItemDelegateRepository;
    }

    /**
     * Save a inventoryItemDelegate.
     *
     * @param inventoryItemDelegate the entity to save.
     * @return the persisted entity.
     */
    public InventoryItemDelegate save(InventoryItemDelegate inventoryItemDelegate) {
        log.debug("Request to save InventoryItemDelegate : {}", inventoryItemDelegate);
        return inventoryItemDelegateRepository.save(inventoryItemDelegate);
    }

    /**
     * Get all the inventoryItemDelegates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryItemDelegate> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryItemDelegates");
        return inventoryItemDelegateRepository.findAll(pageable);
    }


    /**
     * Get one inventoryItemDelegate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoryItemDelegate> findOne(Long id) {
        log.debug("Request to get InventoryItemDelegate : {}", id);
        return inventoryItemDelegateRepository.findById(id);
    }

    /**
     * Delete the inventoryItemDelegate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryItemDelegate : {}", id);
        inventoryItemDelegateRepository.deleteById(id);
    }
}
