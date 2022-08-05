package com.hr.service;

import com.hr.domain.PartyClassification;
import com.hr.repository.PartyClassificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PartyClassification}.
 */
@Service
@Transactional
public class PartyClassificationService {

    private final Logger log = LoggerFactory.getLogger(PartyClassificationService.class);

    private final PartyClassificationRepository partyClassificationRepository;

    public PartyClassificationService(PartyClassificationRepository partyClassificationRepository) {
        this.partyClassificationRepository = partyClassificationRepository;
    }

    /**
     * Save a partyClassification.
     *
     * @param partyClassification the entity to save.
     * @return the persisted entity.
     */
    public PartyClassification save(PartyClassification partyClassification) {
        log.debug("Request to save PartyClassification : {}", partyClassification);
        return partyClassificationRepository.save(partyClassification);
    }

    /**
     * Get all the partyClassifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartyClassification> findAll(Pageable pageable) {
        log.debug("Request to get all PartyClassifications");
        return partyClassificationRepository.findAll(pageable);
    }


    /**
     * Get one partyClassification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartyClassification> findOne(Long id) {
        log.debug("Request to get PartyClassification : {}", id);
        return partyClassificationRepository.findById(id);
    }

    /**
     * Delete the partyClassification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PartyClassification : {}", id);
        partyClassificationRepository.deleteById(id);
    }
}
