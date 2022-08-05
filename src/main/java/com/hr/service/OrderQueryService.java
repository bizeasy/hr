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

import com.hr.domain.Order;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.OrderRepository;
import com.hr.service.dto.OrderCriteria;

/**
 * Service for executing complex queries for {@link Order} entities in the database.
 * The main input is a {@link OrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Order} or a {@link Page} of {@link Order} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderQueryService extends QueryService<Order> {

    private final Logger log = LoggerFactory.getLogger(OrderQueryService.class);

    private final OrderRepository orderRepository;

    public OrderQueryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Return a {@link List} of {@link Order} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Order> findByCriteria(OrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Order> specification = createSpecification(criteria);
        return orderRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Order} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Order> findByCriteria(OrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Order> specification = createSpecification(criteria);
        return orderRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Order> specification = createSpecification(criteria);
        return orderRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Order> createSpecification(OrderCriteria criteria) {
        Specification<Order> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Order_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Order_.name));
            }
            if (criteria.getExternalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExternalId(), Order_.externalId));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), Order_.orderDate));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), Order_.priority));
            }
            if (criteria.getEntryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntryDate(), Order_.entryDate));
            }
            if (criteria.getIsRushOrder() != null) {
                specification = specification.and(buildSpecification(criteria.getIsRushOrder(), Order_.isRushOrder));
            }
            if (criteria.getNeedsInventoryIssuance() != null) {
                specification = specification.and(buildSpecification(criteria.getNeedsInventoryIssuance(), Order_.needsInventoryIssuance));
            }
            if (criteria.getRemainingSubTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRemainingSubTotal(), Order_.remainingSubTotal));
            }
            if (criteria.getGrandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrandTotal(), Order_.grandTotal));
            }
            if (criteria.getHasRateContract() != null) {
                specification = specification.and(buildSpecification(criteria.getHasRateContract(), Order_.hasRateContract));
            }
            if (criteria.getGotQuoteFromVendors() != null) {
                specification = specification.and(buildSpecification(criteria.getGotQuoteFromVendors(), Order_.gotQuoteFromVendors));
            }
            if (criteria.getVendorReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorReason(), Order_.vendorReason));
            }
            if (criteria.getExpectedDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedDeliveryDate(), Order_.expectedDeliveryDate));
            }
            if (criteria.getInsuranceResp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInsuranceResp(), Order_.insuranceResp));
            }
            if (criteria.getTransportResp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransportResp(), Order_.transportResp));
            }
            if (criteria.getUnloadingResp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnloadingResp(), Order_.unloadingResp));
            }
            if (criteria.getOrderTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderTypeId(),
                    root -> root.join(Order_.orderType, JoinType.LEFT).get(OrderType_.id)));
            }
            if (criteria.getSalesChannelId() != null) {
                specification = specification.and(buildSpecification(criteria.getSalesChannelId(),
                    root -> root.join(Order_.salesChannel, JoinType.LEFT).get(SalesChannel_.id)));
            }
            if (criteria.getPartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartyId(),
                    root -> root.join(Order_.party, JoinType.LEFT).get(Party_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(Order_.status, JoinType.LEFT).get(Status_.id)));
            }
        }
        return specification;
    }
}
