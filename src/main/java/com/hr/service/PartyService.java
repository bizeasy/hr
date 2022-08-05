package com.hr.service;

import com.hr.domain.Party;
import com.hr.repository.PartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Party}.
 */
@Service
@Transactional
public class PartyService {

    private final Logger log = LoggerFactory.getLogger(PartyService.class);

    private final PartyRepository partyRepository;

    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    /**
     * Save a party.
     *
     * @param party the entity to save.
     * @return the persisted entity.
     */
    public Party save(Party party) {
        log.debug("Request to save Party : {}", party);
        return partyRepository.save(party);
    }

    /**
     * Get all the parties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Party> findAll(Pageable pageable) {
        log.debug("Request to get all Parties");
        return partyRepository.findAll(pageable);
    }


    /**
     * Get one party by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Party> findOne(Long id) {
        log.debug("Request to get Party : {}", id);
        return partyRepository.findById(id);
    }

    /**
     * Delete the party by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Party : {}", id);
        partyRepository.deleteById(id);
    }
}
