package com.hr.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hr.domain.FacilityGroupMember;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.FacilityGroupMemberRepository;
import com.hr.service.dto.FacilityGroupMemberCriteria;

/**
 * Service for executing complex queries for {@link FacilityGroupMember} entities in the database.
 * The main input is a {@link FacilityGroupMemberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FacilityGroupMember} or a {@link Page} of {@link FacilityGroupMember} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FacilityGroupMemberQueryService extends QueryService<FacilityGroupMember> {

    private final Logger log = LoggerFactory.getLogger(FacilityGroupMemberQueryService.class);

    private final FacilityGroupMemberRepository facilityGroupMemberRepository;

    public FacilityGroupMemberQueryService(FacilityGroupMemberRepository facilityGroupMemberRepository) {
        this.facilityGroupMemberRepository = facilityGroupMemberRepository;
    }

    /**
     * Return a {@link List} of {@link FacilityGroupMember} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FacilityGroupMember> findByCriteria(FacilityGroupMemberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FacilityGroupMember> specification = createSpecification(criteria);
        return facilityGroupMemberRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FacilityGroupMember} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FacilityGroupMember> findByCriteria(FacilityGroupMemberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FacilityGroupMember> specification = createSpecification(criteria);
        return facilityGroupMemberRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FacilityGroupMemberCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FacilityGroupMember> specification = createSpecification(criteria);
        return facilityGroupMemberRepository.count(specification);
    }

    /**
     * Function to convert {@link FacilityGroupMemberCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FacilityGroupMember> createSpecification(FacilityGroupMemberCriteria criteria) {
        Specification<FacilityGroupMember> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FacilityGroupMember_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), FacilityGroupMember_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), FacilityGroupMember_.thruDate));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), FacilityGroupMember_.sequenceNo));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(FacilityGroupMember_.facility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getFacilityGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityGroupId(),
                    root -> root.join(FacilityGroupMember_.facilityGroup, JoinType.LEFT).get(FacilityGroup_.id)));
            }
        }
        return specification;
    }
}
