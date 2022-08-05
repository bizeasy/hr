package com.hr.service;

import com.hr.domain.StatusValidChange;
import com.hr.repository.StatusValidChangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StatusValidChange}.
 */
@Service
@Transactional
public class StatusValidChangeService {

    private final Logger log = LoggerFactory.getLogger(StatusValidChangeService.class);

    private final StatusValidChangeRepository statusValidChangeRepository;

    public StatusValidChangeService(StatusValidChangeRepository statusValidChangeRepository) {
        this.statusValidChangeRepository = statusValidChangeRepository;
    }

    /**
     * Save a statusValidChange.
     *
     * @param statusValidChange the entity to save.
     * @return the persisted entity.
     */
    public StatusValidChange save(StatusValidChange statusValidChange) {
        log.debug("Request to save StatusValidChange : {}", statusValidChange);
        return statusValidChangeRepository.save(statusValidChange);
    }

    /**
     * Get all the statusValidChanges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusValidChange> findAll(Pageable pageable) {
        log.debug("Request to get all StatusValidChanges");
        return statusValidChangeRepository.findAll(pageable);
    }


    /**
     * Get one statusValidChange by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatusValidChange> findOne(Long id) {
        log.debug("Request to get StatusValidChange : {}", id);
        return statusValidChangeRepository.findById(id);
    }

    /**
     * Delete the statusValidChange by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StatusValidChange : {}", id);
        statusValidChangeRepository.deleteById(id);
    }
}
