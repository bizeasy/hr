package com.hr.service;

import com.hr.domain.PartyContactMech;
import com.hr.repository.PartyContactMechRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PartyContactMech}.
 */
@Service
@Transactional
public class PartyContactMechService {

    private final Logger log = LoggerFactory.getLogger(PartyContactMechService.class);

    private final PartyContactMechRepository partyContactMechRepository;

    public PartyContactMechService(PartyContactMechRepository partyContactMechRepository) {
        this.partyContactMechRepository = partyContactMechRepository;
    }

    /**
     * Save a partyContactMech.
     *
     * @param partyContactMech the entity to save.
     * @return the persisted entity.
     */
    public PartyContactMech save(PartyContactMech partyContactMech) {
        log.debug("Request to save PartyContactMech : {}", partyContactMech);
        return partyContactMechRepository.save(partyContactMech);
    }

    /**
     * Get all the partyContactMeches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartyContactMech> findAll(Pageable pageable) {
        log.debug("Request to get all PartyContactMeches");
        return partyContactMechRepository.findAll(pageable);
    }


    /**
     * Get one partyContactMech by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartyContactMech> findOne(Long id) {
        log.debug("Request to get PartyContactMech : {}", id);
        return partyContactMechRepository.findById(id);
    }

    /**
     * Delete the partyContactMech by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PartyContactMech : {}", id);
        partyContactMechRepository.deleteById(id);
    }
}
