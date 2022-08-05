package com.hr.service;

import com.hr.domain.WorkEffort;
import com.hr.repository.WorkEffortRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkEffort}.
 */
@Service
@Transactional
public class WorkEffortService {

    private final Logger log = LoggerFactory.getLogger(WorkEffortService.class);

    private final WorkEffortRepository workEffortRepository;

    public WorkEffortService(WorkEffortRepository workEffortRepository) {
        this.workEffortRepository = workEffortRepository;
    }

    /**
     * Save a workEffort.
     *
     * @param workEffort the entity to save.
     * @return the persisted entity.
     */
    public WorkEffort save(WorkEffort workEffort) {
        log.debug("Request to save WorkEffort : {}", workEffort);
        return workEffortRepository.save(workEffort);
    }

    /**
     * Get all the workEfforts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffort> findAll(Pageable pageable) {
        log.debug("Request to get all WorkEfforts");
        return workEffortRepository.findAll(pageable);
    }


    /**
     * Get one workEffort by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkEffort> findOne(Long id) {
        log.debug("Request to get WorkEffort : {}", id);
        return workEffortRepository.findById(id);
    }

    /**
     * Delete the workEffort by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkEffort : {}", id);
        workEffortRepository.deleteById(id);
    }
}
