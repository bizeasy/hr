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

import com.hr.domain.PartyClassification;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.PartyClassificationRepository;
import com.hr.service.dto.PartyClassificationCriteria;

/**
 * Service for executing complex queries for {@link PartyClassification} entities in the database.
 * The main input is a {@link PartyClassificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartyClassification} or a {@link Page} of {@link PartyClassification} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartyClassificationQueryService extends QueryService<PartyClassification> {

    private final Logger log = LoggerFactory.getLogger(PartyClassificationQueryService.class);

    private final PartyClassificationRepository partyClassificationRepository;

    public PartyClassificationQueryService(PartyClassificationRepository partyClassificationRepository) {
        this.partyClassificationRepository = partyClassificationRepository;
    }

    /**
     * Return a {@link List} of {@link PartyClassification} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartyClassification> findByCriteria(PartyClassificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PartyClassification> specification = createSpecification(criteria);
        return partyClassificationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PartyClassification} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartyClassification> findByCriteria(PartyClassificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartyClassification> specification = createSpecification(criteria);
        return partyClassificationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartyClassificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PartyClassification> specification = createSpecification(criteria);
        return partyClassificationRepository.count(specification);
    }

    /**
     * Function to convert {@link PartyClassificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartyClassification> createSpecification(PartyClassificationCriteria criteria) {
        Specification<PartyClassification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PartyClassification_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), PartyClassification_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), PartyClassification_.thruDate));
            }
            if (criteria.getPartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyId(),
                    root -> root.join(PartyClassification_.party, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getClassificationGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getClassificationGroupId(),
                    root -> root.join(PartyClassification_.classificationGroup, JoinType.LEFT).get(PartyClassificationGroup_.id)));
            }
        }
        return specification;
    }
}
