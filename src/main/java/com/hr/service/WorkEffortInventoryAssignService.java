package com.hr.service;

import com.hr.domain.WorkEffortInventoryAssign;
import com.hr.repository.WorkEffortInventoryAssignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkEffortInventoryAssign}.
 */
@Service
@Transactional
public class WorkEffortInventoryAssignService {

    private final Logger log = LoggerFactory.getLogger(WorkEffortInventoryAssignService.class);

    private final WorkEffortInventoryAssignRepository workEffortInventoryAssignRepository;

    public WorkEffortInventoryAssignService(WorkEffortInventoryAssignRepository workEffortInventoryAssignRepository) {
        this.workEffortInventoryAssignRepository = workEffortInventoryAssignRepository;
    }

    /**
     * Save a workEffortInventoryAssign.
     *
     * @param workEffortInventoryAssign the entity to save.
     * @return the persisted entity.
     */
    public WorkEffortInventoryAssign save(WorkEffortInventoryAssign workEffortInventoryAssign) {
        log.debug("Request to save WorkEffortInventoryAssign : {}", workEffortInventoryAssign);
        return workEffortInventoryAssignRepository.save(workEffortInventoryAssign);
    }

    /**
     * Get all the workEffortInventoryAssigns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortInventoryAssign> findAll(Pageable pageable) {
        log.debug("Request to get all WorkEffortInventoryAssigns");
        return workEffortInventoryAssignRepository.findAll(pageable);
    }


    /**
     * Get one workEffortInventoryAssign by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkEffortInventoryAssign> findOne(Long id) {
        log.debug("Request to get WorkEffortInventoryAssign : {}", id);
        return workEffortInventoryAssignRepository.findById(id);
    }

    /**
     * Delete the workEffortInventoryAssign by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkEffortInventoryAssign : {}", id);
        workEffortInventoryAssignRepository.deleteById(id);
    }
}
