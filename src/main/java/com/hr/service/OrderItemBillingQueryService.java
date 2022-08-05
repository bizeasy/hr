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

import com.hr.domain.OrderItemBilling;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.OrderItemBillingRepository;
import com.hr.service.dto.OrderItemBillingCriteria;

/**
 * Service for executing complex queries for {@link OrderItemBilling} entities in the database.
 * The main input is a {@link OrderItemBillingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrderItemBilling} or a {@link Page} of {@link OrderItemBilling} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderItemBillingQueryService extends QueryService<OrderItemBilling> {

    private final Logger log = LoggerFactory.getLogger(OrderItemBillingQueryService.class);

    private final OrderItemBillingRepository orderItemBillingRepository;

    public OrderItemBillingQueryService(OrderItemBillingRepository orderItemBillingRepository) {
        this.orderItemBillingRepository = orderItemBillingRepository;
    }

    /**
     * Return a {@link List} of {@link OrderItemBilling} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrderItemBilling> findByCriteria(OrderItemBillingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrderItemBilling> specification = createSpecification(criteria);
        return orderItemBillingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OrderItemBilling} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderItemBilling> findByCriteria(OrderItemBillingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrderItemBilling> specification = createSpecification(criteria);
        return orderItemBillingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderItemBillingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrderItemBilling> specification = createSpecification(criteria);
        return orderItemBillingRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderItemBillingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrderItemBilling> createSpecification(OrderItemBillingCriteria criteria) {
        Specification<OrderItemBilling> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderItemBilling_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), OrderItemBilling_.quantity));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), OrderItemBilling_.amount));
            }
            if (criteria.getOrderItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderItemId(),
                    root -> root.join(OrderItemBilling_.orderItem, JoinType.LEFT).get(OrderItem_.id)));
            }
            if (criteria.getInvoiceItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceItemId(),
                    root -> root.join(OrderItemBilling_.invoiceItem, JoinType.LEFT).get(InvoiceItem_.id)));
            }
        }
        return specification;
    }
}
