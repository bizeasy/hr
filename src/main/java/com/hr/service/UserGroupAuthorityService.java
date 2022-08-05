package com.hr.service;

import com.hr.domain.UserGroupAuthority;
import com.hr.repository.UserGroupAuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserGroupAuthority}.
 */
@Service
@Transactional
public class UserGroupAuthorityService {

    private final Logger log = LoggerFactory.getLogger(UserGroupAuthorityService.class);

    private final UserGroupAuthorityRepository userGroupAuthorityRepository;

    public UserGroupAuthorityService(UserGroupAuthorityRepository userGroupAuthorityRepository) {
        this.userGroupAuthorityRepository = userGroupAuthorityRepository;
    }

    /**
     * Save a userGroupAuthority.
     *
     * @param userGroupAuthority the entity to save.
     * @return the persisted entity.
     */
    public UserGroupAuthority save(UserGroupAuthority userGroupAuthority) {
        log.debug("Request to save UserGroupAuthority : {}", userGroupAuthority);
        return userGroupAuthorityRepository.save(userGroupAuthority);
    }

    /**
     * Get all the userGroupAuthorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGroupAuthority> findAll(Pageable pageable) {
        log.debug("Request to get all UserGroupAuthorities");
        return userGroupAuthorityRepository.findAll(pageable);
    }


    /**
     * Get one userGroupAuthority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserGroupAuthority> findOne(Long id) {
        log.debug("Request to get UserGroupAuthority : {}", id);
        return userGroupAuthorityRepository.findById(id);
    }

    /**
     * Delete the userGroupAuthority by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserGroupAuthority : {}", id);
        userGroupAuthorityRepository.deleteById(id);
    }
}
