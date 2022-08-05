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

import com.hr.domain.PaymentApplication;
import com.hr.domain.*; // for static metamodels
import com.hr.repository.PaymentApplicationRepository;
import com.hr.service.dto.PaymentApplicationCriteria;

/**
 * Service for executing complex queries for {@link PaymentApplication} entities in the database.
 * The main input is a {@link PaymentApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentApplication} or a {@link Page} of {@link PaymentApplication} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentApplicationQueryService extends QueryService<PaymentApplication> {

    private final Logger log = LoggerFactory.getLogger(PaymentApplicationQueryService.class);

    private final PaymentApplicationRepository paymentApplicationRepository;

    public PaymentApplicationQueryService(PaymentApplicationRepository paymentApplicationRepository) {
        this.paymentApplicationRepository = paymentApplicationRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentApplication} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentApplication> findByCriteria(PaymentApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentApplication> specification = createSpecification(criteria);
        return paymentApplicationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PaymentApplication} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentApplication> findByCriteria(PaymentApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentApplication> specification = createSpecification(criteria);
        return paymentApplicationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentApplication> specification = createSpecification(criteria);
        return paymentApplicationRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentApplicationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentApplication> createSpecification(PaymentApplicationCriteria criteria) {
        Specification<PaymentApplication> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentApplication_.id));
            }
            if (criteria.getAmountApplied() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountApplied(), PaymentApplication_.amountApplied));
            }
            if (criteria.getPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentId(),
                    root -> root.join(PaymentApplication_.payment, JoinType.LEFT).get(Payment_.id)));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(PaymentApplication_.invoice, JoinType.LEFT).get(Invoice_.id)));
            }
            if (criteria.getInvoiceItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceItemId(),
                    root -> root.join(PaymentApplication_.invoiceItem, JoinType.LEFT).get(InvoiceItem_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(PaymentApplication_.order, JoinType.LEFT).get(Order_.id)));
            }
            if (criteria.getOrderItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderItemId(),
                    root -> root.join(PaymentApplication_.orderItem, JoinType.LEFT).get(OrderItem_.id)));
            }
            if (criteria.getToPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getToPaymentId(),
                    root -> root.join(PaymentApplication_.toPayment, JoinType.LEFT).get(Payment_.id)));
            }
        }
        return specification;
    }
}
