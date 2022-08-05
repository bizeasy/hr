package com.hr.service;

import com.hr.domain.PartyRole;
import com.hr.repository.PartyRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PartyRole}.
 */
@Service
@Transactional
public class PartyRoleService {

    private final Logger log = LoggerFactory.getLogger(PartyRoleService.class);

    private final PartyRoleRepository partyRoleRepository;

    public PartyRoleService(PartyRoleRepository partyRoleRepository) {
        this.partyRoleRepository = partyRoleRepository;
    }

    /**
     * Save a partyRole.
     *
     * @param partyRole the entity to save.
     * @return the persisted entity.
     */
    public PartyRole save(PartyRole partyRole) {
        log.debug("Request to save PartyRole : {}", partyRole);
        return partyRoleRepository.save(partyRole);
    }

    /**
     * Get all the partyRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartyRole> findAll(Pageable pageable) {
        log.debug("Request to get all PartyRoles");
        return partyRoleRepository.findAll(pageable);
    }


    /**
     * Get one partyRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartyRole> findOne(Long id) {
        log.debug("Request to get PartyRole : {}", id);
        return partyRoleRepository.findById(id);
    }

    /**
     * Delete the partyRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PartyRole : {}", id);
        partyRoleRepository.deleteById(id);
    }
}
