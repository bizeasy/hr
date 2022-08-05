package com.hr.service;

import com.hr.domain.UserGroupMember;
import com.hr.repository.UserGroupMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserGroupMember}.
 */
@Service
@Transactional
public class UserGroupMemberService {

    private final Logger log = LoggerFactory.getLogger(UserGroupMemberService.class);

    private final UserGroupMemberRepository userGroupMemberRepository;

    public UserGroupMemberService(UserGroupMemberRepository userGroupMemberRepository) {
        this.userGroupMemberRepository = userGroupMemberRepository;
    }

    /**
     * Save a userGroupMember.
     *
     * @param userGroupMember the entity to save.
     * @return the persisted entity.
     */
    public UserGroupMember save(UserGroupMember userGroupMember) {
        log.debug("Request to save UserGroupMember : {}", userGroupMember);
        return userGroupMemberRepository.save(userGroupMember);
    }

    /**
     * Get all the userGroupMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGroupMember> findAll(Pageable pageable) {
        log.debug("Request to get all UserGroupMembers");
        return userGroupMemberRepository.findAll(pageable);
    }


    /**
     * Get one userGroupMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserGroupMember> findOne(Long id) {
        log.debug("Request to get UserGroupMember : {}", id);
        return userGroupMemberRepository.findById(id);
    }

    /**
     * Delete the userGroupMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserGroupMember : {}", id);
        userGroupMemberRepository.deleteById(id);
    }
}
