package com.hr.service;

import com.hr.domain.WorkEffortProduct;
import com.hr.repository.WorkEffortProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkEffortProduct}.
 */
@Service
@Transactional
public class WorkEffortProductService {

    private final Logger log = LoggerFactory.getLogger(WorkEffortProductService.class);

    private final WorkEffortProductRepository workEffortProductRepository;

    public WorkEffortProductService(WorkEffortProductRepository workEffortProductRepository) {
        this.workEffortProductRepository = workEffortProductRepository;
    }

    /**
     * Save a workEffortProduct.
     *
     * @param workEffortProduct the entity to save.
     * @return the persisted entity.
     */
    public WorkEffortProduct save(WorkEffortProduct workEffortProduct) {
        log.debug("Request to save WorkEffortProduct : {}", workEffortProduct);
        return workEffortProductRepository.save(workEffortProduct);
    }

    /**
     * Get all the workEffortProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortProduct> findAll(Pageable pageable) {
        log.debug("Request to get all WorkEffortProducts");
        return workEffortProductRepository.findAll(pageable);
    }


    /**
     * Get one workEffortProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkEffortProduct> findOne(Long id) {
        log.debug("Request to get WorkEffortProduct : {}", id);
        return workEffortProductRepository.findById(id);
    }

    /**
     * Delete the workEffortProduct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkEffortProduct : {}", id);
        workEffortProductRepository.deleteById(id);
    }
}
