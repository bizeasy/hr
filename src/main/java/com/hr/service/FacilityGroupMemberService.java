package com.hr.service;

import com.hr.domain.FacilityGroupMember;
import com.hr.repository.FacilityGroupMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FacilityGroupMember}.
 */
@Service
@Transactional
public class FacilityGroupMemberService {

    private final Logger log = LoggerFactory.getLogger(FacilityGroupMemberService.class);

    private final FacilityGroupMemberRepository facilityGroupMemberRepository;

    public FacilityGroupMemberService(FacilityGroupMemberRepository facilityGroupMemberRepository) {
        this.facilityGroupMemberRepository = facilityGroupMemberRepository;
    }

    /**
     * Save a facilityGroupMember.
     *
     * @param facilityGroupMember the entity to save.
     * @return the persisted entity.
     */
    public FacilityGroupMember save(FacilityGroupMember facilityGroupMember) {
        log.debug("Request to save FacilityGroupMember : {}", facilityGroupMember);
        return facilityGroupMemberRepository.save(facilityGroupMember);
    }

    /**
     * Get all the facilityGroupMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FacilityGroupMember> findAll(Pageable pageable) {
        log.debug("Request to get all FacilityGroupMembers");
        return facilityGroupMemberRepository.findAll(pageable);
    }


    /**
     * Get one facilityGroupMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FacilityGroupMember> findOne(Long id) {
        log.debug("Request to get FacilityGroupMember : {}", id);
        return facilityGroupMemberRepository.findById(id);
    }

    /**
     * Delete the facilityGroupMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FacilityGroupMember : {}", id);
        facilityGroupMemberRepository.deleteById(id);
    }
}
