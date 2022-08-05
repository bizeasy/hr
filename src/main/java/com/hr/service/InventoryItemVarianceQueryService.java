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

import com.hr.domain.InventoryItemVariance;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.InventoryItemVarianceRepository;
import com.hr.service.dto.InventoryItemVarianceCriteria;

/**
 * Service for executing complex queries for {@link InventoryItemVariance} entities in the database.
 * The main input is a {@link InventoryItemVarianceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryItemVariance} or a {@link Page} of {@link InventoryItemVariance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryItemVarianceQueryService extends QueryService<InventoryItemVariance> {

    private final Logger log = LoggerFactory.getLogger(InventoryItemVarianceQueryService.class);

    private final InventoryItemVarianceRepository inventoryItemVarianceRepository;

    public InventoryItemVarianceQueryService(InventoryItemVarianceRepository inventoryItemVarianceRepository) {
        this.inventoryItemVarianceRepository = inventoryItemVarianceRepository;
    }

    /**
     * Return a {@link List} of {@link InventoryItemVariance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryItemVariance> findByCriteria(InventoryItemVarianceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventoryItemVariance> specification = createSpecification(criteria);
        return inventoryItemVarianceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InventoryItemVariance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryItemVariance> findByCriteria(InventoryItemVarianceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryItemVariance> specification = createSpecification(criteria);
        return inventoryItemVarianceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryItemVarianceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventoryItemVariance> specification = createSpecification(criteria);
        return inventoryItemVarianceRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryItemVarianceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryItemVariance> createSpecification(InventoryItemVarianceCriteria criteria) {
        Specification<InventoryItemVariance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InventoryItemVariance_.id));
            }
            if (criteria.getVarianceReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVarianceReason(), InventoryItemVariance_.varianceReason));
            }
            if (criteria.getAtpVar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAtpVar(), InventoryItemVariance_.atpVar));
            }
            if (criteria.getQohVar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQohVar(), InventoryItemVariance_.qohVar));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), InventoryItemVariance_.comments));
            }
            if (criteria.getInventoryItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemId(),
                    root -> root.join(InventoryItemVariance_.inventoryItem, JoinType.LEFT).get(InventoryItem_.id)));
            }
            if (criteria.getReasonId() != null) {
                specification = specification.and(buildSpecification(criteria.getReasonId(),
                    root -> root.join(InventoryItemVariance_.reason, JoinType.LEFT).get(Reason_.id)));
            }
        }
        return specification;
    }
}
