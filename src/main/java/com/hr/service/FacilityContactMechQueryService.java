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

import com.hr.domain.FacilityContactMech;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.FacilityContactMechRepository;
import com.hr.service.dto.FacilityContactMechCriteria;

/**
 * Service for executing complex queries for {@link FacilityContactMech} entities in the database.
 * The main input is a {@link FacilityContactMechCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FacilityContactMech} or a {@link Page} of {@link FacilityContactMech} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FacilityContactMechQueryService extends QueryService<FacilityContactMech> {

    private final Logger log = LoggerFactory.getLogger(FacilityContactMechQueryService.class);

    private final FacilityContactMechRepository facilityContactMechRepository;

    public FacilityContactMechQueryService(FacilityContactMechRepository facilityContactMechRepository) {
        this.facilityContactMechRepository = facilityContactMechRepository;
    }

    /**
     * Return a {@link List} of {@link FacilityContactMech} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FacilityContactMech> findByCriteria(FacilityContactMechCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FacilityContactMech> specification = createSpecification(criteria);
        return facilityContactMechRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FacilityContactMech} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FacilityContactMech> findByCriteria(FacilityContactMechCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FacilityContactMech> specification = createSpecification(criteria);
        return facilityContactMechRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FacilityContactMechCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FacilityContactMech> specification = createSpecification(criteria);
        return facilityContactMechRepository.count(specification);
    }

    /**
     * Function to convert {@link FacilityContactMechCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FacilityContactMech> createSpecification(FacilityContactMechCriteria criteria) {
        Specification<FacilityContactMech> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FacilityContactMech_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), FacilityContactMech_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), FacilityContactMech_.thruDate));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(FacilityContactMech_.facility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getContactMechId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechId(),
                    root -> root.join(FacilityContactMech_.contactMech, JoinType.LEFT).get(ContactMech_.id)));
            }
            if (criteria.getContactMechPurposeId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechPurposeId(),
                    root -> root.join(FacilityContactMech_.contactMechPurpose, JoinType.LEFT).get(ContactMechPurpose_.id)));
            }
        }
        return specification;
    }
}
