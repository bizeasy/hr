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

import com.hr.domain.OrderItem;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.OrderItemRepository;
import com.hr.service.dto.OrderItemCriteria;

/**
 * Service for executing complex queries for {@link OrderItem} entities in the database.
 * The main input is a {@link OrderItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrderItem} or a {@link Page} of {@link OrderItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderItemQueryService extends QueryService<OrderItem> {

    private final Logger log = LoggerFactory.getLogger(OrderItemQueryService.class);

    private final OrderItemRepository orderItemRepository;

    public OrderItemQueryService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Return a {@link List} of {@link OrderItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrderItem> findByCriteria(OrderItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrderItem> specification = createSpecification(criteria);
        return orderItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OrderItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderItem> findByCriteria(OrderItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrderItem> specification = createSpecification(criteria);
        return orderItemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrderItem> specification = createSpecification(criteria);
        return orderItemRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrderItem> createSpecification(OrderItemCriteria criteria) {
        Specification<OrderItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderItem_.id));
            }
            if (criteria.getSequenceNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequenceNo(), OrderItem_.sequenceNo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), OrderItem_.quantity));
            }
            if (criteria.getCancelQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCancelQuantity(), OrderItem_.cancelQuantity));
            }
            if (criteria.getSelectedAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSelectedAmount(), OrderItem_.selectedAmount));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), OrderItem_.unitPrice));
            }
            if (criteria.getUnitListPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitListPrice(), OrderItem_.unitListPrice));
            }
            if (criteria.getCgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCgst(), OrderItem_.cgst));
            }
            if (criteria.getIgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIgst(), OrderItem_.igst));
            }
            if (criteria.getSgst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSgst(), OrderItem_.sgst));
            }
            if (criteria.getCgstPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCgstPercentage(), OrderItem_.cgstPercentage));
            }
            if (criteria.getIgstPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIgstPercentage(), OrderItem_.igstPercentage));
            }
            if (criteria.getSgstPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSgstPercentage(), OrderItem_.sgstPercentage));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), OrderItem_.totalPrice));
            }
            if (criteria.getIsModifiedPrice() != null) {
                specification = specification.and(buildSpecification(criteria.getIsModifiedPrice(), OrderItem_.isModifiedPrice));
            }
            if (criteria.getEstimatedShipDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedShipDate(), OrderItem_.estimatedShipDate));
            }
            if (criteria.getEstimatedDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedDeliveryDate(), OrderItem_.estimatedDeliveryDate));
            }
            if (criteria.getShipBeforeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShipBeforeDate(), OrderItem_.shipBeforeDate));
            }
            if (criteria.getShipAfterDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShipAfterDate(), OrderItem_.shipAfterDate));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(OrderItem_.order, JoinType.LEFT).get(Order_.id)));
            }
            if (criteria.getOrderItemTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderItemTypeId(),
                    root -> root.join(OrderItem_.orderItemType, JoinType.LEFT).get(OrderItemType_.id)));
            }
            if (criteria.getFromInventoryItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getFromInventoryItemId(),
                    root -> root.join(OrderItem_.fromInventoryItem, JoinType.LEFT).get(InventoryItem_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(OrderItem_.product, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getSupplierProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierProductId(),
                    root -> root.join(OrderItem_.supplierProduct, JoinType.LEFT).get(SupplierProduct_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(OrderItem_.status, JoinType.LEFT).get(Status_.id)));
            }
            if (criteria.getTaxAuthRateId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxAuthRateId(),
                    root -> root.join(OrderItem_.taxAuthRate, JoinType.LEFT).get(TaxAuthorityRateType_.id)));
            }
        }
        return specification;
    }
}
