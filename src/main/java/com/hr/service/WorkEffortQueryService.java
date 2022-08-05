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

import com.hr.domain.WorkEffort;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.WorkEffortRepository;
import com.hr.service.dto.WorkEffortCriteria;

/**
 * Service for executing complex queries for {@link WorkEffort} entities in the database.
 * The main input is a {@link WorkEffortCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkEffort} or a {@link Page} of {@link WorkEffort} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkEffortQueryService extends QueryService<WorkEffort> {

    private final Logger log = LoggerFactory.getLogger(WorkEffortQueryService.class);

    private final WorkEffortRepository workEffortRepository;

    public WorkEffortQueryService(WorkEffortRepository workEffortRepository) {
        this.workEffortRepository = workEffortRepository;
    }

    /**
     * Return a {@link List} of {@link WorkEffort} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkEffort> findByCriteria(WorkEffortCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkEffort> specification = createSpecification(criteria);
        return workEffortRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkEffort} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkEffort> findByCriteria(WorkEffortCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkEffort> specification = createSpecification(criteria);
        return workEffortRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkEffortCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkEffort> specification = createSpecification(criteria);
        return workEffortRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkEffortCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkEffort> createSpecification(WorkEffortCriteria criteria) {
        Specification<WorkEffort> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkEffort_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), WorkEffort_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), WorkEffort_.description));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), WorkEffort_.code));
            }
            if (criteria.getBatchSize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBatchSize(), WorkEffort_.batchSize));
            }
            if (criteria.getMinYieldRange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinYieldRange(), WorkEffort_.minYieldRange));
            }
            if (criteria.getMaxYieldRange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxYieldRange(), WorkEffort_.maxYieldRange));
            }
            if (criteria.getPercentComplete() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentComplete(), WorkEffort_.percentComplete));
            }
            if (criteria.getValidationType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidationType(), WorkEffort_.validationType));
            }
            if (criteria.getShelfLife() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShelfLife(), WorkEffort_.shelfLife));
            }
            if (criteria.getOutputQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutputQty(), WorkEffort_.outputQty));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(WorkEffort_.product, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getKsmId() != null) {
                specification = specification.and(buildSpecification(criteria.getKsmId(),
                    root -> root.join(WorkEffort_.ksm, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(WorkEffort_.type, JoinType.LEFT).get(WorkEffortType_.id)));
            }
            if (criteria.getPurposeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurposeId(),
                    root -> root.join(WorkEffort_.purpose, JoinType.LEFT).get(WorkEffortPurpose_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(WorkEffort_.status, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(WorkEffort_.facility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getShelfListUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getShelfListUomId(),
                    root -> root.join(WorkEffort_.shelfListUom, JoinType.LEFT).get(Uom_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(WorkEffort_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductStoreId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductStoreId(),
                    root -> root.join(WorkEffort_.productStore, JoinType.LEFT).get(ProductStore_.id)));
            }
        }
        return specification;
    }
}
