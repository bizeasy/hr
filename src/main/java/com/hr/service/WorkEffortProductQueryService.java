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

import com.hr.domain.WorkEffortProduct;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.WorkEffortProductRepository;
import com.hr.service.dto.WorkEffortProductCriteria;

/**
 * Service for executing complex queries for {@link WorkEffortProduct} entities in the database.
 * The main input is a {@link WorkEffortProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkEffortProduct} or a {@link Page} of {@link WorkEffortProduct} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkEffortProductQueryService extends QueryService<WorkEffortProduct> {

    private final Logger log = LoggerFactory.getLogger(WorkEffortProductQueryService.class);

    private final WorkEffortProductRepository workEffortProductRepository;

    public WorkEffortProductQueryService(WorkEffortProductRepository workEffortProductRepository) {
        this.workEffortProductRepository = workEffortProductRepository;
    }

    /**
     * Return a {@link List} of {@link WorkEffortProduct} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkEffortProduct> findByCriteria(WorkEffortProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkEffortProduct> specification = createSpecification(criteria);
        return workEffortProductRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkEffortProduct} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffortProduct> findByCriteria(WorkEffortProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkEffortProduct> specification = createSpecification(criteria);
        return workEffortProductRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkEffortProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkEffortProduct> specification = createSpecification(criteria);
        return workEffortProductRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkEffortProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkEffortProduct> createSpecification(WorkEffortProductCriteria criteria) {
        Specification<WorkEffortProduct> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkEffortProduct_.id));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), WorkEffortProduct_.sequenceNo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), WorkEffortProduct_.quantity));
            }
            if (criteria.getWorkEffortId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkEffortId(),
                    root -> root.join(WorkEffortProduct_.workEffort, JoinType.LEFT).get(WorkEffort_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(WorkEffortProduct_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
