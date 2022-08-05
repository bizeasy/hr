package com.hr.service;

import com.hr.domain.FacilityContactMech;
import com.hr.repository.FacilityContactMechRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FacilityContactMech}.
 */
@Service
@Transactional
public class FacilityContactMechService {

    private final Logger log = LoggerFactory.getLogger(FacilityContactMechService.class);

    private final FacilityContactMechRepository facilityContactMechRepository;

    public FacilityContactMechService(FacilityContactMechRepository facilityContactMechRepository) {
        this.facilityContactMechRepository = facilityContactMechRepository;
    }

    /**
     * Save a facilityContactMech.
     *
     * @param facilityContactMech the entity to save.
     * @return the persisted entity.
     */
    public FacilityContactMech save(FacilityContactMech facilityContactMech) {
        log.debug("Request to save FacilityContactMech : {}", facilityContactMech);
        return facilityContactMechRepository.save(facilityContactMech);
    }

    /**
     * Get all the facilityContactMeches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FacilityContactMech> findAll(Pageable pageable) {
        log.debug("Request to get all FacilityContactMeches");
        return facilityContactMechRepository.findAll(pageable);
    }


    /**
     * Get one facilityContactMech by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FacilityContactMech> findOne(Long id) {
        log.debug("Request to get FacilityContactMech : {}", id);
        return facilityContactMechRepository.findById(id);
    }

    /**
     * Delete the facilityContactMech by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FacilityContactMech : {}", id);
        facilityContactMechRepository.deleteById(id);
    }
}
