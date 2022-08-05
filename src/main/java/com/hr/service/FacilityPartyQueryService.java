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

import com.hr.domain.FacilityParty;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.FacilityPartyRepository;
import com.hr.service.dto.FacilityPartyCriteria;

/**
 * Service for executing complex queries for {@link FacilityParty} entities in the database.
 * The main input is a {@link FacilityPartyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FacilityParty} or a {@link Page} of {@link FacilityParty} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FacilityPartyQueryService extends QueryService<FacilityParty> {

    private final Logger log = LoggerFactory.getLogger(FacilityPartyQueryService.class);

    private final FacilityPartyRepository facilityPartyRepository;

    public FacilityPartyQueryService(FacilityPartyRepository facilityPartyRepository) {
        this.facilityPartyRepository = facilityPartyRepository;
    }

    /**
     * Return a {@link List} of {@link FacilityParty} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FacilityParty> findByCriteria(FacilityPartyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FacilityParty> specification = createSpecification(criteria);
        return facilityPartyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FacilityParty} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FacilityParty> findByCriteria(FacilityPartyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FacilityParty> specification = createSpecification(criteria);
        return facilityPartyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FacilityPartyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FacilityParty> specification = createSpecification(criteria);
        return facilityPartyRepository.count(specification);
    }

    /**
     * Function to convert {@link FacilityPartyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FacilityParty> createSpecification(FacilityPartyCriteria criteria) {
        Specification<FacilityParty> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FacilityParty_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), FacilityParty_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), FacilityParty_.thruDate));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(FacilityParty_.facility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getPartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyId(),
                    root -> root.join(FacilityParty_.party, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getRoleTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleTypeId(),
                    root -> root.join(FacilityParty_.roleType, JoinType.LEFT).get(RoleType_.id)));
            }
        }
        return specification;
    }
}
