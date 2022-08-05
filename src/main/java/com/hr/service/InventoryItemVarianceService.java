package com.hr.service;

import com.hr.domain.InventoryItemVariance;
import com.hr.repository.InventoryItemVarianceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InventoryItemVariance}.
 */
@Service
@Transactional
public class InventoryItemVarianceService {

    private final Logger log = LoggerFactory.getLogger(InventoryItemVarianceService.class);

    private final InventoryItemVarianceRepository inventoryItemVarianceRepository;

    public InventoryItemVarianceService(InventoryItemVarianceRepository inventoryItemVarianceRepository) {
        this.inventoryItemVarianceRepository = inventoryItemVarianceRepository;
    }

    /**
     * Save a inventoryItemVariance.
     *
     * @param inventoryItemVariance the entity to save.
     * @return the persisted entity.
     */
    public InventoryItemVariance save(InventoryItemVariance inventoryItemVariance) {
        log.debug("Request to save InventoryItemVariance : {}", inventoryItemVariance);
        return inventoryItemVarianceRepository.save(inventoryItemVariance);
    }

    /**
     * Get all the inventoryItemVariances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryItemVariance> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryItemVariances");
        return inventoryItemVarianceRepository.findAll(pageable);
    }


    /**
     * Get one inventoryItemVariance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoryItemVariance> findOne(Long id) {
        log.debug("Request to get InventoryItemVariance : {}", id);
        return inventoryItemVarianceRepository.findById(id);
    }

    /**
     * Delete the inventoryItemVariance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryItemVariance : {}", id);
        inventoryItemVarianceRepository.deleteById(id);
    }
}
