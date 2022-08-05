package com.hr.service;

import com.hr.domain.Reason;
import com.hr.repository.ReasonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Reason}.
 */
@Service
@Transactional
public class ReasonService {

    private final Logger log = LoggerFactory.getLogger(ReasonService.class);

    private final ReasonRepository reasonRepository;

    public ReasonService(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    /**
     * Save a reason.
     *
     * @param reason the entity to save.
     * @return the persisted entity.
     */
    public Reason save(Reason reason) {
        log.debug("Request to save Reason : {}", reason);
        return reasonRepository.save(reason);
    }

    /**
     * Get all the reasons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Reason> findAll(Pageable pageable) {
        log.debug("Request to get all Reasons");
        return reasonRepository.findAll(pageable);
    }


    /**
     * Get one reason by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Reason> findOne(Long id) {
        log.debug("Request to get Reason : {}", id);
        return reasonRepository.findById(id);
    }

    /**
     * Delete the reason by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reason : {}", id);
        reasonRepository.deleteById(id);
    }
}
