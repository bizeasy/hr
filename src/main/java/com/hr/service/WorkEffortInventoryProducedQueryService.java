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

import com.hr.domain.WorkEffortInventoryProduced;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.WorkEffortInventoryProducedRepository;
import com.hr.service.dto.WorkEffortInventoryProducedCriteria;

/**
 * Service for executing complex queries for {@link WorkEffortInventoryProduced} entities in the database.
 * The main input is a {@link WorkEffortInventoryProducedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkEffortInventoryProduced} or a {@link Page} of {@link WorkEffortInventoryProduced} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkEffortInventoryProducedQueryService extends QueryService<WorkEffortInventoryProduced> {

    private final Logger log = LoggerFactory.getLogger(WorkEffortInventoryProducedQueryService.class);

    private final WorkEffortInventoryProducedRepository workEffortInventoryProducedRepository;

    public WorkEffortInventoryProducedQueryService(WorkEffortInventoryProducedRepository workEffortInventoryProducedRepository) {
        this.workEffortInventoryProducedRepository = workEffortInventoryProducedRepository;
    }

    /**
     * Return a {@link List} of {@link WorkEffortInventoryProduced} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkEffortInventoryProduced> findByCriteria(WorkEffortInventoryProducedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkEffortInventoryProduced> specification = createSpecification(criteria);
        return workEffortInventoryProducedRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkEffortInventoryProduced} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortInventoryProduced> findByCriteria(WorkEffortInventoryProducedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkEffortInventoryProduced> specification = createSpecification(criteria);
        return workEffortInventoryProducedRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkEffortInventoryProducedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkEffortInventoryProduced> specification = createSpecification(criteria);
        return workEffortInventoryProducedRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkEffortInventoryProducedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkEffortInventoryProduced> createSpecification(WorkEffortInventoryProducedCriteria criteria) {
        Specification<WorkEffortInventoryProduced> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkEffortInventoryProduced_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), WorkEffortInventoryProduced_.quantity));
            }
            if (criteria.getWorkEffortId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkEffortId(),
                    root -> root.join(WorkEffortInventoryProduced_.workEffort, JoinType.LEFT).get(WorkEffort_.id)));
            }
            if (criteria.getInventoryItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemId(),
                    root -> root.join(WorkEffortInventoryProduced_.inventoryItem, JoinType.LEFT).get(InventoryItem_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(WorkEffortInventoryProduced_.status, JoinType.LEFT).get(Status_.id)));
            }
        }
        return specification;
    }
}
