package com.hr.service;

import com.hr.domain.WorkEffortInventoryProduced;
import com.hr.repository.WorkEffortInventoryProducedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkEffortInventoryProduced}.
 */
@Service
@Transactional
public class WorkEffortInventoryProducedService {

    private final Logger log = LoggerFactory.getLogger(WorkEffortInventoryProducedService.class);

    private final WorkEffortInventoryProducedRepository workEffortInventoryProducedRepository;

    public WorkEffortInventoryProducedService(WorkEffortInventoryProducedRepository workEffortInventoryProducedRepository) {
        this.workEffortInventoryProducedRepository = workEffortInventoryProducedRepository;
    }

    /**
     * Save a workEffortInventoryProduced.
     *
     * @param workEffortInventoryProduced the entity to save.
     * @return the persisted entity.
     */
    public WorkEffortInventoryProduced save(WorkEffortInventoryProduced workEffortInventoryProduced) {
        log.debug("Request to save WorkEffortInventoryProduced : {}", workEffortInventoryProduced);
        return workEffortInventoryProducedRepository.save(workEffortInventoryProduced);
    }

    /**
     * Get all the workEffortInventoryProduceds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortInventoryProduced> findAll(Pageable pageable) {
        log.debug("Request to get all WorkEffortInventoryProduceds");
        return workEffortInventoryProducedRepository.findAll(pageable);
    }


    /**
     * Get one workEffortInventoryProduced by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkEffortInventoryProduced> findOne(Long id) {
        log.debug("Request to get WorkEffortInventoryProduced : {}", id);
        return workEffortInventoryProducedRepository.findById(id);
    }

    /**
     * Delete the workEffortInventoryProduced by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkEffortInventoryProduced : {}", id);
        workEffortInventoryProducedRepository.deleteById(id);
    }
}
