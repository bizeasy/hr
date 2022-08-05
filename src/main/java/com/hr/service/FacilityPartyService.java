package com.hr.service;

import com.hr.domain.FacilityParty;
import com.hr.repository.FacilityPartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FacilityParty}.
 */
@Service
@Transactional
public class FacilityPartyService {

    private final Logger log = LoggerFactory.getLogger(FacilityPartyService.class);

    private final FacilityPartyRepository facilityPartyRepository;

    public FacilityPartyService(FacilityPartyRepository facilityPartyRepository) {
        this.facilityPartyRepository = facilityPartyRepository;
    }

    /**
     * Save a facilityParty.
     *
     * @param facilityParty the entity to save.
     * @return the persisted entity.
     */
    public FacilityParty save(FacilityParty facilityParty) {
        log.debug("Request to save FacilityParty : {}", facilityParty);
        return facilityPartyRepository.save(facilityParty);
    }

    /**
     * Get all the facilityParties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FacilityParty> findAll(Pageable pageable) {
        log.debug("Request to get all FacilityParties");
        return facilityPartyRepository.findAll(pageable);
    }


    /**
     * Get one facilityParty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FacilityParty> findOne(Long id) {
        log.debug("Request to get FacilityParty : {}", id);
        return facilityPartyRepository.findById(id);
    }

    /**
     * Delete the facilityParty by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FacilityParty : {}", id);
        facilityPartyRepository.deleteById(id);
    }
}
