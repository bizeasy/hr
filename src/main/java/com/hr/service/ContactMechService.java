package com.hr.service;

import com.hr.domain.ContactMech;
import com.hr.repository.ContactMechRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContactMech}.
 */
@Service
@Transactional
public class ContactMechService {

    private final Logger log = LoggerFactory.getLogger(ContactMechService.class);

    private final ContactMechRepository contactMechRepository;

    public ContactMechService(ContactMechRepository contactMechRepository) {
        this.contactMechRepository = contactMechRepository;
    }

    /**
     * Save a contactMech.
     *
     * @param contactMech the entity to save.
     * @return the persisted entity.
     */
    public ContactMech save(ContactMech contactMech) {
        log.debug("Request to save ContactMech : {}", contactMech);
        return contactMechRepository.save(contactMech);
    }

    /**
     * Get all the contactMeches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactMech> findAll(Pageable pageable) {
        log.debug("Request to get all ContactMeches");
        return contactMechRepository.findAll(pageable);
    }


    /**
     * Get one contactMech by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactMech> findOne(Long id) {
        log.debug("Request to get ContactMech : {}", id);
        return contactMechRepository.findById(id);
    }

    /**
     * Delete the contactMech by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactMech : {}", id);
        contactMechRepository.deleteById(id);
    }
}
