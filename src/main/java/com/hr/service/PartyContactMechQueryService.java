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

import com.hr.domain.PartyContactMech;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.PartyContactMechRepository;
import com.hr.service.dto.PartyContactMechCriteria;

/**
 * Service for executing complex queries for {@link PartyContactMech} entities in the database.
 * The main input is a {@link PartyContactMechCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartyContactMech} or a {@link Page} of {@link PartyContactMech} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartyContactMechQueryService extends QueryService<PartyContactMech> {

    private final Logger log = LoggerFactory.getLogger(PartyContactMechQueryService.class);

    private final PartyContactMechRepository partyContactMechRepository;

    public PartyContactMechQueryService(PartyContactMechRepository partyContactMechRepository) {
        this.partyContactMechRepository = partyContactMechRepository;
    }

    /**
     * Return a {@link List} of {@link PartyContactMech} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartyContactMech> findByCriteria(PartyContactMechCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PartyContactMech> specification = createSpecification(criteria);
        return partyContactMechRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PartyContactMech} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartyContactMech> findByCriteria(PartyContactMechCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartyContactMech> specification = createSpecification(criteria);
        return partyContactMechRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartyContactMechCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PartyContactMech> specification = createSpecification(criteria);
        return partyContactMechRepository.count(specification);
    }

    /**
     * Function to convert {@link PartyContactMechCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartyContactMech> createSpecification(PartyContactMechCriteria criteria) {
        Specification<PartyContactMech> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PartyContactMech_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), PartyContactMech_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), PartyContactMech_.thruDate));
            }
            if (criteria.getPartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyId(),
                    root -> root.join(PartyContactMech_.party, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getContactMechId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechId(),
                    root -> root.join(PartyContactMech_.contactMech, JoinType.LEFT).get(ContactMech_.id)));
            }
            if (criteria.getContactMechPurposeId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactMechPurposeId(),
                    root -> root.join(PartyContactMech_.contactMechPurpose, JoinType.LEFT).get(ContactMechPurpose_.id)));
            }
        }
        return specification;
    }
}
