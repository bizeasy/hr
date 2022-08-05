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

import com.hr.domain.WorkEffortInventoryAssign;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.WorkEffortInventoryAssignRepository;
import com.hr.service.dto.WorkEffortInventoryAssignCriteria;

/**
 * Service for executing complex queries for {@link WorkEffortInventoryAssign} entities in the database.
 * The main input is a {@link WorkEffortInventoryAssignCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkEffortInventoryAssign} or a {@link Page} of {@link WorkEffortInventoryAssign} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkEffortInventoryAssignQueryService extends QueryService<WorkEffortInventoryAssign> {

    private final Logger log = LoggerFactory.getLogger(WorkEffortInventoryAssignQueryService.class);

    private final WorkEffortInventoryAssignRepository workEffortInventoryAssignRepository;

    public WorkEffortInventoryAssignQueryService(WorkEffortInventoryAssignRepository workEffortInventoryAssignRepository) {
        this.workEffortInventoryAssignRepository = workEffortInventoryAssignRepository;
    }

    /**
     * Return a {@link List} of {@link WorkEffortInventoryAssign} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkEffortInventoryAssign> findByCriteria(WorkEffortInventoryAssignCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkEffortInventoryAssign> specification = createSpecification(criteria);
        return workEffortInventoryAssignRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkEffortInventoryAssign} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortInventoryAssign> findByCriteria(WorkEffortInventoryAssignCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkEffortInventoryAssign> specification = createSpecification(criteria);
        return workEffortInventoryAssignRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkEffortInventoryAssignCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkEffortInventoryAssign> specification = createSpecification(criteria);
        return workEffortInventoryAssignRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkEffortInventoryAssignCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkEffortInventoryAssign> createSpecification(WorkEffortInventoryAssignCriteria criteria) {
        Specification<WorkEffortInventoryAssign> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkEffortInventoryAssign_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), WorkEffortInventoryAssign_.quantity));
            }
            if (criteria.getWorkEffortId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkEffortId(),
                    root -> root.join(WorkEffortInventoryAssign_.workEffort, JoinType.LEFT).get(WorkEffort_.id)));
            }
            if (criteria.getInventoryItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemId(),
                    root -> root.join(WorkEffortInventoryAssign_.inventoryItem, JoinType.LEFT).get(InventoryItem_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(WorkEffortInventoryAssign_.status, JoinType.LEFT).get(Status_.id)));
            }
        }
        return specification;
    }
}
