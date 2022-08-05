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

import com.hr.domain.WorkEffortAssoc;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.WorkEffortAssocRepository;
import com.hr.service.dto.WorkEffortAssocCriteria;

/**
 * Service for executing complex queries for {@link WorkEffortAssoc} entities in the database.
 * The main input is a {@link WorkEffortAssocCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkEffortAssoc} or a {@link Page} of {@link WorkEffortAssoc} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkEffortAssocQueryService extends QueryService<WorkEffortAssoc> {

    private final Logger log = LoggerFactory.getLogger(WorkEffortAssocQueryService.class);

    private final WorkEffortAssocRepository workEffortAssocRepository;

    public WorkEffortAssocQueryService(WorkEffortAssocRepository workEffortAssocRepository) {
        this.workEffortAssocRepository = workEffortAssocRepository;
    }

    /**
     * Return a {@link List} of {@link WorkEffortAssoc} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkEffortAssoc> findByCriteria(WorkEffortAssocCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkEffortAssoc> specification = createSpecification(criteria);
        return workEffortAssocRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkEffortAssoc} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortAssoc> findByCriteria(WorkEffortAssocCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkEffortAssoc> specification = createSpecification(criteria);
        return workEffortAssocRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkEffortAssocCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkEffortAssoc> specification = createSpecification(criteria);
        return workEffortAssocRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkEffortAssocCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkEffortAssoc> createSpecification(WorkEffortAssocCriteria criteria) {
        Specification<WorkEffortAssoc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkEffortAssoc_.id));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), WorkEffortAssoc_.sequenceNo));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), WorkEffortAssoc_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), WorkEffortAssoc_.thruDate));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(WorkEffortAssoc_.type, JoinType.LEFT).get(WorkEffortAssocType_.id)));
            }
            if (criteria.getWeIdFromId() != null) {
                specification = specification.and(buildSpecification(criteria.getWeIdFromId(),
                    root -> root.join(WorkEffortAssoc_.weIdFrom, JoinType.LEFT).get(WorkEffort_.id)));
            }
            if (criteria.getWeIdToId() != null) {
                specification = specification.and(buildSpecification(criteria.getWeIdToId(),
                    root -> root.join(WorkEffortAssoc_.weIdTo, JoinType.LEFT).get(WorkEffort_.id)));
            }
        }
        return specification;
    }
}
