package com.hr.service;

import com.hr.domain.WorkEffortAssoc;
import com.hr.repository.WorkEffortAssocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkEffortAssoc}.
 */
@Service
@Transactional
public class WorkEffortAssocService {

    private final Logger log = LoggerFactory.getLogger(WorkEffortAssocService.class);

    private final WorkEffortAssocRepository workEffortAssocRepository;

    public WorkEffortAssocService(WorkEffortAssocRepository workEffortAssocRepository) {
        this.workEffortAssocRepository = workEffortAssocRepository;
    }

    /**
     * Save a workEffortAssoc.
     *
     * @param workEffortAssoc the entity to save.
     * @return the persisted entity.
     */
    public WorkEffortAssoc save(WorkEffortAssoc workEffortAssoc) {
        log.debug("Request to save WorkEffortAssoc : {}", workEffortAssoc);
        return workEffortAssocRepository.save(workEffortAssoc);
    }

    /**
     * Get all the workEffortAssocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortAssoc> findAll(Pageable pageable) {
        log.debug("Request to get all WorkEffortAssocs");
        return workEffortAssocRepository.findAll(pageable);
    }


    /**
     * Get one workEffortAssoc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkEffortAssoc> findOne(Long id) {
        log.debug("Request to get WorkEffortAssoc : {}", id);
        return workEffortAssocRepository.findById(id);
    }

    /**
     * Delete the workEffortAssoc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkEffortAssoc : {}", id);
        workEffortAssocRepository.deleteById(id);
    }
}
