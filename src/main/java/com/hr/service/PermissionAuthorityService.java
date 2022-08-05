package com.hr.service;

import com.hr.domain.PermissionAuthority;
import com.hr.repository.PermissionAuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PermissionAuthority}.
 */
@Service
@Transactional
public class PermissionAuthorityService {

    private final Logger log = LoggerFactory.getLogger(PermissionAuthorityService.class);

    private final PermissionAuthorityRepository permissionAuthorityRepository;

    public PermissionAuthorityService(PermissionAuthorityRepository permissionAuthorityRepository) {
        this.permissionAuthorityRepository = permissionAuthorityRepository;
    }

    /**
     * Save a permissionAuthority.
     *
     * @param permissionAuthority the entity to save.
     * @return the persisted entity.
     */
    public PermissionAuthority save(PermissionAuthority permissionAuthority) {
        log.debug("Request to save PermissionAuthority : {}", permissionAuthority);
        return permissionAuthorityRepository.save(permissionAuthority);
    }

    /**
     * Get all the permissionAuthorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PermissionAuthority> findAll(Pageable pageable) {
        log.debug("Request to get all PermissionAuthorities");
        return permissionAuthorityRepository.findAll(pageable);
    }


    /**
     * Get one permissionAuthority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PermissionAuthority> findOne(Long id) {
        log.debug("Request to get PermissionAuthority : {}", id);
        return permissionAuthorityRepository.findById(id);
    }

    /**
     * Delete the permissionAuthority by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PermissionAuthority : {}", id);
        permissionAuthorityRepository.deleteById(id);
    }
}
