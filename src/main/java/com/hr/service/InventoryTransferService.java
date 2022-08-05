package com.hr.service;

import com.hr.domain.InventoryTransfer;
import com.hr.repository.InventoryTransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InventoryTransfer}.
 */
@Service
@Transactional
public class InventoryTransferService {

    private final Logger log = LoggerFactory.getLogger(InventoryTransferService.class);

    private final InventoryTransferRepository inventoryTransferRepository;

    public InventoryTransferService(InventoryTransferRepository inventoryTransferRepository) {
        this.inventoryTransferRepository = inventoryTransferRepository;
    }

    /**
     * Save a inventoryTransfer.
     *
     * @param inventoryTransfer the entity to save.
     * @return the persisted entity.
     */
    public InventoryTransfer save(InventoryTransfer inventoryTransfer) {
        log.debug("Request to save InventoryTransfer : {}", inventoryTransfer);
        return inventoryTransferRepository.save(inventoryTransfer);
    }

    /**
     * Get all the inventoryTransfers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryTransfer> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryTransfers");
        return inventoryTransferRepository.findAll(pageable);
    }


    /**
     * Get one inventoryTransfer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoryTransfer> findOne(Long id) {
        log.debug("Request to get InventoryTransfer : {}", id);
        return inventoryTransferRepository.findById(id);
    }

    /**
     * Delete the inventoryTransfer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryTransfer : {}", id);
        inventoryTransferRepository.deleteById(id);
    }
}
