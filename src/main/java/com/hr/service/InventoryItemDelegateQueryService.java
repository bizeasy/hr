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

import com.hr.domain.InventoryItemDelegate;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.InventoryItemDelegateRepository;
import com.hr.service.dto.InventoryItemDelegateCriteria;

/**
 * Service for executing complex queries for {@link InventoryItemDelegate} entities in the database.
 * The main input is a {@link InventoryItemDelegateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryItemDelegate} or a {@link Page} of {@link InventoryItemDelegate} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryItemDelegateQueryService extends QueryService<InventoryItemDelegate> {

    private final Logger log = LoggerFactory.getLogger(InventoryItemDelegateQueryService.class);

    private final InventoryItemDelegateRepository inventoryItemDelegateRepository;

    public InventoryItemDelegateQueryService(InventoryItemDelegateRepository inventoryItemDelegateRepository) {
        this.inventoryItemDelegateRepository = inventoryItemDelegateRepository;
    }

    /**
     * Return a {@link List} of {@link InventoryItemDelegate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryItemDelegate> findByCriteria(InventoryItemDelegateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventoryItemDelegate> specification = createSpecification(criteria);
        return inventoryItemDelegateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InventoryItemDelegate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryItemDelegate> findByCriteria(InventoryItemDelegateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryItemDelegate> specification = createSpecification(criteria);
        return inventoryItemDelegateRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryItemDelegateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventoryItemDelegate> specification = createSpecification(criteria);
        return inventoryItemDelegateRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryItemDelegateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryItemDelegate> createSpecification(InventoryItemDelegateCriteria criteria) {
        Specification<InventoryItemDelegate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InventoryItemDelegate_.id));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), InventoryItemDelegate_.sequenceNo));
            }
            if (criteria.getEffectiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffectiveDate(), InventoryItemDelegate_.effectiveDate));
            }
            if (criteria.getQuantityOnHandDiff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityOnHandDiff(), InventoryItemDelegate_.quantityOnHandDiff));
            }
            if (criteria.getAvailableToPromiseDiff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailableToPromiseDiff(), InventoryItemDelegate_.availableToPromiseDiff));
            }
            if (criteria.getAccountingQuantityDiff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountingQuantityDiff(), InventoryItemDelegate_.accountingQuantityDiff));
            }
            if (criteria.getUnitCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitCost(), InventoryItemDelegate_.unitCost));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), InventoryItemDelegate_.remarks));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(InventoryItemDelegate_.invoice, JoinType.LEFT).get(Invoice_.id)));
            }
            if (criteria.getInvoiceItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceItemId(),
                    root -> root.join(InventoryItemDelegate_.invoiceItem, JoinType.LEFT).get(InvoiceItem_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(InventoryItemDelegate_.order, JoinType.LEFT).get(Order_.id)));
            }
            if (criteria.getOrderItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderItemId(),
                    root -> root.join(InventoryItemDelegate_.orderItem, JoinType.LEFT).get(OrderItem_.id)));
            }
            if (criteria.getItemIssuanceId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemIssuanceId(),
                    root -> root.join(InventoryItemDelegate_.itemIssuance, JoinType.LEFT).get(ItemIssuance_.id)));
            }
            if (criteria.getInventoryTransferId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryTransferId(),
                    root -> root.join(InventoryItemDelegate_.inventoryTransfer, JoinType.LEFT).get(InventoryTransfer_.id)));
            }
            if (criteria.getInventoryItemVarianceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemVarianceId(),
                    root -> root.join(InventoryItemDelegate_.inventoryItemVariance, JoinType.LEFT).get(InventoryItemVariance_.id)));
            }
            if (criteria.getInventoryItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryItemId(),
                    root -> root.join(InventoryItemDelegate_.inventoryItem, JoinType.LEFT).get(InventoryItem_.id)));
            }
        }
        return specification;
    }
}
