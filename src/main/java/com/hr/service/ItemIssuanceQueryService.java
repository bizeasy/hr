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

import com.hr.domain.ItemIssuance;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.ItemIssuanceRepository;
import com.hr.service.dto.ItemIssuanceCriteria;

/**
 * Service for executing complex queries for {@link ItemIssuance} entities in the database.
 * The main input is a {@link ItemIssuanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemIssuance} or a {@link Page} of {@link ItemIssuance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemIssuanceQueryService extends QueryService<ItemIssuance> {

    private final Logger log = LoggerFactory.getLogger(ItemIssuanceQueryService.class);

    private final ItemIssuanceRepository itemIssuanceRepository;

    public ItemIssuanceQueryService(ItemIssuanceRepository itemIssuanceRepository) {
        this.itemIssuanceRepository = itemIssuanceRepository;
    }

    /**
     * Return a {@link List} of {@link ItemIssuance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemIssuance> findByCriteria(ItemIssuanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemIssuance> specification = createSpecification(criteria);
        return itemIssuanceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemIssuance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemIssuance> findByCriteria(ItemIssuanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemIssuance> specification = createSpecification(criteria);
        return itemIssuanceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemIssuanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemIssuance> specification = createSpecification(criteria);
        return itemIssuanceRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemIssuanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemIssuance> createSpecification(ItemIssuanceCriteria criteria) {
        Specification<ItemIssuance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemIssuance_.id));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), ItemIssuance_.message));
            }
            if (criteria.getIssuedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedDate(), ItemIssuance_.issuedDate));
            }
            if (criteria.getIssuedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIssuedBy(), ItemIssuance_.issuedBy));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), ItemIssuance_.quantity));
            }
            if (criteria.getCancelQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCancelQuantity(), ItemIssuance_.cancelQuantity));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), ItemIssuance_.fromDate));
            }
            if (criteria.getThruDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThruDate(), ItemIssuance_.thruDate));
            }
            if (criteria.getEquipmentId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEquipmentId(), ItemIssuance_.equipmentId));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(ItemIssuance_.order, JoinType.LEFT).get(Order_.id)));
            }
            if (criteria.getOrderItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderItemId(),
                    root -> root.join(ItemIssuance_.orderItem, JoinType.LEFT).get(OrderItem_.id)));
            }
            if (criteria.getInventoryItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemId(),
                    root -> root.join(ItemIssuance_.inventoryItem, JoinType.LEFT).get(InventoryItem_.id)));
            }
            if (criteria.getIssuedByUserLoginId() != null) {
                specification = specification.and(buildSpecification(criteria.getIssuedByUserLoginId(),
                    root -> root.join(ItemIssuance_.issuedByUserLogin, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getVarianceReasonId() != null) {
                specification = specification.and(buildSpecification(criteria.getVarianceReasonId(),
                    root -> root.join(ItemIssuance_.varianceReason, JoinType.LEFT).get(Reason_.id)));
            }
            if (criteria.getFacilityId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacilityId(),
                    root -> root.join(ItemIssuance_.facility, JoinType.LEFT).get(Facility_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(ItemIssuance_.status, JoinType.LEFT).get(Status_.id)));
            }
        }
        return specification;
    }
}
