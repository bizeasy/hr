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

import com.hr.domain.WorkEffortStatus;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.WorkEffortStatusRepository;
import com.hr.service.dto.WorkEffortStatusCriteria;

/**
 * Service for executing complex queries for {@link WorkEffortStatus} entities in the database.
 * The main input is a {@link WorkEffortStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkEffortStatus} or a {@link Page} of {@link WorkEffortStatus} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkEffortStatusQueryService extends QueryService<WorkEffortStatus> {

    private final Logger log = LoggerFactory.getLogger(WorkEffortStatusQueryService.class);

    private final WorkEffortStatusRepository workEffortStatusRepository;

    public WorkEffortStatusQueryService(WorkEffortStatusRepository workEffortStatusRepository) {
        this.workEffortStatusRepository = workEffortStatusRepository;
    }

    /**
     * Return a {@link List} of {@link WorkEffortStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkEffortStatus> findByCriteria(WorkEffortStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkEffortStatus> specification = createSpecification(criteria);
        return workEffortStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkEffortStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortStatus> findByCriteria(WorkEffortStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkEffortStatus> specification = createSpecification(criteria);
        return workEffortStatusRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkEffortStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkEffortStatus> specification = createSpecification(criteria);
        return workEffortStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkEffortStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkEffortStatus> createSpecification(WorkEffortStatusCriteria criteria) {
        Specification<WorkEffortStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkEffortStatus_.id));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), WorkEffortStatus_.reason));
            }
            if (criteria.getWorkEffortId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkEffortId(),
                    root -> root.join(WorkEffortStatus_.workEffort, JoinType.LEFT).get(WorkEffort_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(WorkEffortStatus_.status, JoinType.LEFT).get(Status_.id)));
            }
        }
        return specification;
    }
}
