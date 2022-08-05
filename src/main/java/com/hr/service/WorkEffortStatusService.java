package com.hr.service;

import com.hr.domain.WorkEffortStatus;
import com.hr.repository.WorkEffortStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkEffortStatus}.
 */
@Service
@Transactional
public class WorkEffortStatusService {

    private final Logger log = LoggerFactory.getLogger(WorkEffortStatusService.class);

    private final WorkEffortStatusRepository workEffortStatusRepository;

    public WorkEffortStatusService(WorkEffortStatusRepository workEffortStatusRepository) {
        this.workEffortStatusRepository = workEffortStatusRepository;
    }

    /**
     * Save a workEffortStatus.
     *
     * @param workEffortStatus the entity to save.
     * @return the persisted entity.
     */
    public WorkEffortStatus save(WorkEffortStatus workEffortStatus) {
        log.debug("Request to save WorkEffortStatus : {}", workEffortStatus);
        return workEffortStatusRepository.save(workEffortStatus);
    }

    /**
     * Get all the workEffortStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortStatus> findAll(Pageable pageable) {
        log.debug("Request to get all WorkEffortStatuses");
        return workEffortStatusRepository.findAll(pageable);
    }


    /**
     * Get one workEffortStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkEffortStatus> findOne(Long id) {
        log.debug("Request to get WorkEffortStatus : {}", id);
        return workEffortStatusRepository.findById(id);
    }

    /**
     * Delete the workEffortStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkEffortStatus : {}", id);
        workEffortStatusRepository.deleteById(id);
    }
}
