package com.hr.service;

import com.hr.domain.StatusCategory;
import com.hr.repository.StatusCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StatusCategory}.
 */
@Service
@Transactional
public class StatusCategoryService {

    private final Logger log = LoggerFactory.getLogger(StatusCategoryService.class);

    private final StatusCategoryRepository statusCategoryRepository;

    public StatusCategoryService(StatusCategoryRepository statusCategoryRepository) {
        this.statusCategoryRepository = statusCategoryRepository;
    }

    /**
     * Save a statusCategory.
     *
     * @param statusCategory the entity to save.
     * @return the persisted entity.
     */
    public StatusCategory save(StatusCategory statusCategory) {
        log.debug("Request to save StatusCategory : {}", statusCategory);
        return statusCategoryRepository.save(statusCategory);
    }

    /**
     * Get all the statusCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusCategory> findAll(Pageable pageable) {
        log.debug("Request to get all StatusCategories");
        return statusCategoryRepository.findAll(pageable);
    }


    /**
     * Get one statusCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatusCategory> findOne(Long id) {
        log.debug("Request to get StatusCategory : {}", id);
        return statusCategoryRepository.findById(id);
    }

    /**
     * Delete the statusCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StatusCategory : {}", id);
        statusCategoryRepository.deleteById(id);
    }
}
